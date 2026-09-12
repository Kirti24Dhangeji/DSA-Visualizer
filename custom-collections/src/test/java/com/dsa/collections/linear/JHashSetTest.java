package com.dsa.collections.linear;

import com.dsa.collections.iterator.JIterator;

import java.util.NoSuchElementException;
import java.util.Arrays;

/**
 * Custom test suite for JHashSet (no JUnit — matches the project's existing
 * check()/checkThrows()/printSummary() testing convention).
 *
 * Compile (from custom-collections module):
 *   mvn clean test-compile
 * Run directly:
 *   java -cp target/classes;target/test-classes com.dsa.collections.linear.JHashSetTest
 *   (use ':' instead of ';' on non-Windows)
 *
 * ---------------------------------------------------------------------------
 * This suite targets the current implementation, where:
 *   - hash(Object) correctly hashes the element itself (element.hashCode()).
 *   - add(null) throws IllegalArgumentException (nulls are disallowed).
 *   - Constructing with capacity <= 0 throws IllegalArgumentException.
 *
 * BUG FOUND WHILE WRITING THESE TESTS (see testResizeAfterRemovals_TombstonesDontLeak):
 *
 * add()'s resize/load-factor check only looks at `size` (live elements), never
 * at tombstones (DELETED slots). That lets the table become fully occupied
 * (real values + tombstones, zero true `null` slots) while `size` is still
 * low enough that no resize fires. Once that happens, a new element's probe
 * can wrap the entire table — passing a reusable tombstone along the way —
 * without ever reaching a `null`, and the loop falls through to `return
 * false;` even though inserting into that tombstone would have been valid
 * and correct. Fix: after the scan completes with no match and no null slot,
 * if firstDeletedIndex was set, insert there (the full scan already proves
 * the element isn't a duplicate):
 *
 *     if (firstDeletedIndex != -1) {
 *         elements[firstDeletedIndex] = element;
 *         size++;
 *         return true;
 *     }
 *     return false;
 *
 * contains()/remove() don't need this fix — falling through to "not found"
 * after a full scan is already the correct answer for a lookup.
 * ---------------------------------------------------------------------------
 */
public class JHashSetTest {

    private static int totalTests = 0;
    private static int passedTests = 0;

    @FunctionalInterface
    interface ThrowingRunnable {
        void run() throws Exception;
    }

    private static void check(String description, boolean condition) {
        totalTests++;
        if (condition) {
            passedTests++;
            System.out.println("[PASS] " + description);
        } else {
            System.out.println("[FAIL] " + description);
        }
    }

    private static void checkThrows(String description, Class<? extends Throwable> expected, ThrowingRunnable runnable) {
        totalTests++;
        try {
            runnable.run();
            System.out.println("[FAIL] " + description + " (no exception thrown)");
        } catch (Throwable t) {
            if (expected.isInstance(t)) {
                passedTests++;
                System.out.println("[PASS] " + description);
            } else {
                System.out.println("[FAIL] " + description + " (expected " + expected.getSimpleName()
                        + " but got " + t.getClass().getSimpleName() + ")");
            }
        }
    }

    private static void printSummary() {
        System.out.println();
        System.out.println("=====================================");
        System.out.println("Total:  " + totalTests);
        System.out.println("Passed: " + passedTests);
        System.out.println("Failed: " + (totalTests - passedTests));
        System.out.println("=====================================");
    }

    /* HELPER FACTORY METHODS */

    private static JHashSet<Integer> setOf(Integer... values) {
        JHashSet<Integer> set = new JHashSet<>();
        for (Integer v : values) {
            set.add(v);
        }
        return set;
    }

    /** Multiset-equality check that ignores bucket/iteration order. */
    private static boolean sameContents(Object[] actual, Object... expected) {
        Object[] a = Arrays.copyOf(actual, actual.length);
        Object[] e = Arrays.copyOf(expected, expected.length);
        Arrays.sort(a, JHashSetTest::compareNullable);
        Arrays.sort(e, JHashSetTest::compareNullable);
        return Arrays.equals(a, e);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static int compareNullable(Object a, Object b) {
        if (a == null && b == null) return 0;
        if (a == null) return -1;
        if (b == null) return 1;
        return ((Comparable) a).compareTo(b);
    }

    /** Drains the iterator into an array, independent of toArray(). */
    private static Object[] drainIterator(JHashSet<Integer> set) {
        JIterator<Integer> it = set.iterator();
        Object[] result = new Object[set.size()];
        int i = 0;
        while (it.hasNext()) {
            result[i++] = it.next();
        }
        return result;
    }

    public static void main(String[] args) {
        testConstructors();
        testAddBasic();
        testAddDuplicate();
        testContains();
        testRemove();
        testRemoveThenReAdd_TombstoneReuse();
        testIsEmpty();
        testSizeTracking();
        testToArray();
        testClear();
        testIterator_HappyPath();
        testIterator_NoSuchElementException();
        testResizeGrowth_AllElementsSurvive();
        testResizeAfterRemovals_TombstonesDontLeak();
        testAddNullThrows();
        testInvalidCapacityThrowsInConstructor();
        testStringElements();

        printSummary();
    }

    private static void testConstructors() {
        System.out.println("\n--- Constructors ---");

        JHashSet<Integer> defaultSet = new JHashSet<>();
        check("default constructor starts empty", defaultSet.isEmpty());
        check("default constructor size is 0", defaultSet.size() == 0);

        JHashSet<Integer> sizedSet = new JHashSet<>(20);
        check("sized constructor starts empty", sizedSet.isEmpty());
        check("sized constructor accepts and stores an element", sizedSet.add(42) && sizedSet.contains(42));
    }

    private static void testAddBasic() {
        System.out.println("\n--- add() basic ---");

        JHashSet<Integer> set = new JHashSet<>();
        check("add() on new element returns true", set.add(1));
        check("size is 1 after one add", set.size() == 1);

        check("add() second distinct element returns true", set.add(2));
        check("size is 2 after two distinct adds", set.size() == 2);
    }

    private static void testAddDuplicate() {
        System.out.println("\n--- add() duplicate handling ---");

        JHashSet<Integer> set = setOf(5, 10, 15);
        check("adding an existing element returns false", !set.add(10));
        check("size unchanged after duplicate add attempt", set.size() == 3);
    }

    private static void testContains() {
        System.out.println("\n--- contains() ---");

        JHashSet<Integer> set = setOf(1, 2, 3);
        check("contains() true for present element", set.contains(2));
        check("contains() false for absent element", !set.contains(99));
        check("contains() false on empty set", new JHashSet<Integer>().contains(1) == false);
    }

    private static void testRemove() {
        System.out.println("\n--- remove() ---");

        JHashSet<Integer> set = setOf(1, 2, 3);
        check("remove() existing element returns true", set.remove(2));
        check("size decrements after remove", set.size() == 2);
        check("removed element no longer found by contains()", !set.contains(2));

        check("remove() non-existent element returns false", !set.remove(999));
        check("size unchanged after failed remove", set.size() == 2);

        check("remove() on empty set returns false, no exception", !new JHashSet<Integer>().remove(1));
    }

    private static void testRemoveThenReAdd_TombstoneReuse() {
        System.out.println("\n--- remove() + add() tombstone (DELETED slot) reuse ---");

        JHashSet<Integer> set = setOf(1, 2, 3);
        set.remove(1);
        set.remove(2);
        set.remove(3);
        check("set is empty after removing all elements", set.isEmpty());
        check("size is 0 after removing all elements", set.size() == 0);

        check("re-adding a previously removed element works", set.add(1));
        check("re-added element is found by contains()", set.contains(1));
        check("size is 1 after re-adding", set.size() == 1);

        check("adding two more distinct elements into reused tombstone slots works",
                set.add(2) && set.add(3));
        check("size is 3 after reusing all tombstone slots", set.size() == 3);
        check("all three elements are retrievable after tombstone reuse",
                set.contains(1) && set.contains(2) && set.contains(3));
    }

    private static void testIsEmpty() {
        System.out.println("\n--- isEmpty() ---");

        JHashSet<Integer> set = new JHashSet<>();
        check("new set isEmpty() is true", set.isEmpty());

        set.add(1);
        check("isEmpty() is false after add", !set.isEmpty());

        set.remove(1);
        check("isEmpty() is true again after removing the only element", set.isEmpty());
    }

    private static void testSizeTracking() {
        System.out.println("\n--- size() tracking across mixed operations ---");

        JHashSet<Integer> set = new JHashSet<>();
        for (int i = 0; i < 10; i++) {
            set.add(i);
        }
        check("size is 10 after adding 10 distinct elements", set.size() == 10);

        for (int i = 0; i < 5; i++) {
            set.remove(i);
        }
        check("size is 5 after removing 5 elements", set.size() == 5);

        // Re-add duplicates of remaining elements — should not change size
        for (int i = 5; i < 10; i++) {
            set.add(i);
        }
        check("size still 5 after re-adding already-present elements", set.size() == 5);
    }

    private static void testToArray() {
        System.out.println("\n--- toArray() ---");

        JHashSet<Integer> set = setOf(10, 20, 30);
        Object[] arr = set.toArray();
        check("toArray() length matches size()", arr.length == set.size());
        check("toArray() contains exactly the inserted elements (order-independent)",
                sameContents(arr, 10, 20, 30));

        Object[] emptyArr = new JHashSet<Integer>().toArray();
        check("toArray() on empty set returns zero-length array", emptyArr.length == 0);
    }

    private static void testClear() {
        System.out.println("\n--- clear() ---");

        JHashSet<Integer> set = setOf(1, 2, 3);
        set.clear();
        check("size is 0 after clear()", set.size() == 0);
        check("isEmpty() is true after clear()", set.isEmpty());
        check("previously present element not found after clear()", !set.contains(1));

        check("set is usable again after clear()", set.add(100) && set.contains(100));
    }

    private static void testIterator_HappyPath() {
        System.out.println("\n--- iterator() happy path ---");

        JHashSet<Integer> set = setOf(1, 2, 3, 4, 5);
        Object[] viaIterator = drainIterator(set);
        check("iterator yields exactly size() elements", viaIterator.length == set.size());
        check("iterator content matches toArray() content (order-independent)",
                sameContents(viaIterator, (Object[]) set.toArray()));

        JIterator<Integer> emptyIt = new JHashSet<Integer>().iterator();
        check("hasNext() is false immediately on empty set", !emptyIt.hasNext());
    }

    private static void testIterator_NoSuchElementException() {
        System.out.println("\n--- iterator() exhaustion ---");

        JHashSet<Integer> set = setOf(1);
        JIterator<Integer> it = set.iterator();
        it.next(); // consume the only element

        checkThrows("next() after exhausting iterator throws NoSuchElementException",
                NoSuchElementException.class, it::next);
    }

    private static void testResizeGrowth_AllElementsSurvive() {
        System.out.println("\n--- resize() correctness under growth ---");

        // Default capacity is 10 with a 0.75 load factor, so this forces
        // at least one internal resize().
        JHashSet<Integer> set = new JHashSet<>();
        int n = 50;
        for (int i = 0; i < n; i++) {
            set.add(i);
        }

        check("size is correct after growth-triggering inserts (" + n + ")", set.size() == n);

        boolean allFound = true;
        for (int i = 0; i < n; i++) {
            if (!set.contains(i)) {
                allFound = false;
                break;
            }
        }
        check("every inserted element is still found after resize()", allFound);
        check("toArray() length matches size after resize()", set.toArray().length == n);

        // no duplicates should have been introduced by rehashing
        check("re-adding all elements after resize returns false for every one (no dupes created)",
                allNoLongerAddable(set, n));
    }

    private static boolean allNoLongerAddable(JHashSet<Integer> set, int n) {
        for (int i = 0; i < n; i++) {
            if (set.add(i)) {
                return false; // it was "new" again — duplicate/corruption
            }
        }
        return true;
    }

    private static void testResizeAfterRemovals_TombstonesDontLeak() {
        System.out.println("\n--- resize() drops tombstones correctly ---");

        JHashSet<Integer> set = new JHashSet<>(4);
        set.add(1);
        set.add(2);
        set.add(3);
        set.remove(1);
        set.remove(2);
        // Now: 1 live element (3), 2 tombstones — force further growth.
        set.add(4);
        set.add(5);
        set.add(6);
        set.add(7); // should trigger at least one more resize along the way

        check("size reflects only live elements after remove+resize", set.size() == 5);
        check("removed elements are not resurrected by resize()",
                !set.contains(1) && !set.contains(2));
        check("all elements added after removal are present",
                set.contains(3) && set.contains(4) && set.contains(5)
                        && set.contains(6) && set.contains(7));
        check("toArray() length matches size after remove+resize combo", set.toArray().length == 5);
    }

    private static void testAddNullThrows() {
        System.out.println("\n--- add(null) is rejected ---");

        JHashSet<Integer> set = new JHashSet<>();
        checkThrows("add(null) throws IllegalArgumentException", IllegalArgumentException.class,
                () -> set.add(null));
        check("size stays 0 after a rejected null add", set.size() == 0);
        check("set is still usable after a rejected add", set.add(1) && set.contains(1));
    }

    private static void testInvalidCapacityThrowsInConstructor() {
        System.out.println("\n--- constructor rejects non-positive capacity ---");

        checkThrows("capacity 0 throws IllegalArgumentException", IllegalArgumentException.class,
                () -> new JHashSet<Integer>(0));
        checkThrows("negative capacity throws IllegalArgumentException", IllegalArgumentException.class,
                () -> new JHashSet<Integer>(-5));
    }

    private static void testStringElements() {
        System.out.println("\n--- generic type check with String elements ---");

        JHashSet<String> set = new JHashSet<>();
        check("add() String element", set.add("alpha"));
        check("add() second distinct String element", set.add("beta"));
        check("duplicate String add returns false", !set.add("alpha"));
        check("contains() finds String element", set.contains("beta"));
        check("remove() String element works", set.remove("alpha"));
        check("removed String element no longer contained", !set.contains("alpha"));
        check("size is 1 after String add/remove sequence", set.size() == 1);
    }
}