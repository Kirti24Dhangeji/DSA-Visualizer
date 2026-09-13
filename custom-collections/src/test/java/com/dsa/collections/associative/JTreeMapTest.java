package com.dsa.collections.associative;

import java.util.Arrays;

public class JTreeMapTest {

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

    /** Runs an action that is expected to complete without throwing anything.
     *  Deliberately used instead of calling risky methods inline, so a
     *  currently-known bug that throws doesn't abort the whole test run. */
    private static void checkNoThrow(String description, ThrowingRunnable runnable) {
        totalTests++;
        try {
            runnable.run();
            passedTests++;
            System.out.println("[PASS] " + description);
        } catch (Throwable t) {
            System.out.println("[FAIL] " + description + " (threw " + t.getClass().getSimpleName()
                    + (t.getMessage() != null ? ": " + t.getMessage() : "") + ")");
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

    public static void main(String[] args) {
        testConstructor();
        testPutNewKey_ReturnsValue();
        testPutDuplicateKeyUpdatesValue();
        testPutNullKeyOrValueThrows();
        testGetExistingKey();
        testGetMissingKeyThrows();
        testContainsKey();
        testContainsValue();
        testSetValue();
        testSetValueMissingKeyThrows();
        testRemove_ReturnsRemovedValue();
        testRemove_LastElement_DoesNotThrow();
        testRemove_MissingKeyThrows();
        testRemove_NullKeyThrows();
        testSizeTracking();
        testKeySet();
        testValues_ContainsAllAndSortedByKey();
        testClear();
        testLargeDataset_AllKeysSurviveInsertion();
        testStringKeys();

        printSummary();
    }

    private static void testConstructor() {
        System.out.println("\n--- Constructor ---");

        JTreeMap<Integer, String> map = new JTreeMap<>();
        check("new map isEmpty() is true", map.isEmpty());
        check("new map size() is 0", map.size() == 0);
    }

    private static void testPutNewKey_ReturnsValue() {
        System.out.println("\n--- put() new key ---");

        JTreeMap<Integer, String> map = new JTreeMap<>();
        check("put() returns the inserted value", map.put(1, "one").equals("one"));
        check("get() retrieves the just-inserted value", map.get(1).equals("one"));
        check("containsKey() true right after put()", map.containsKey(1));
    }

    private static void testPutDuplicateKeyUpdatesValue() {
        System.out.println("\n--- put() on an existing key updates the value ---");

        JTreeMap<Integer, String> map = new JTreeMap<>();
        map.put(5, "five");
        map.put(5, "FIVE-updated");
        check("get() reflects the updated value after re-putting an existing key",
                "FIVE-updated".equals(map.get(5)));
    }

    private static void testPutNullKeyOrValueThrows() {
        System.out.println("\n--- put() rejects null key/value ---");

        JTreeMap<Integer, String> map = new JTreeMap<>();
        checkThrows("put(null, \"x\") throws IllegalArgumentException", IllegalArgumentException.class,
                () -> map.put(null, "x"));
        checkThrows("put(1, null) throws IllegalArgumentException", IllegalArgumentException.class,
                () -> map.put(1, null));
    }

    private static void testGetExistingKey() {
        System.out.println("\n--- get() ---");

        JTreeMap<Integer, String> map = new JTreeMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        check("get() returns correct value for an existing key", "ten".equals(map.get(10)));
        check("get() returns correct value for another existing key", "twenty".equals(map.get(20)));
    }

    private static void testGetMissingKeyThrows() {
        System.out.println("\n--- get() on missing key ---");

        JTreeMap<Integer, String> map = new JTreeMap<>();
        map.put(1, "one");
        checkThrows("get() on a missing key throws IllegalArgumentException", IllegalArgumentException.class,
                () -> map.get(999));
    }

    private static void testContainsKey() {
        System.out.println("\n--- containsKey() ---");

        JTreeMap<Integer, String> map = new JTreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        check("containsKey() true for present key", map.containsKey(1));
        check("containsKey() false for absent key", !map.containsKey(99));
        check("containsKey() false on empty map", !new JTreeMap<Integer, String>().containsKey(1));
    }

    private static void testContainsValue() {
        System.out.println("\n--- containsValue() ---");

        JTreeMap<Integer, String> map = new JTreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        check("containsValue() true for present value", map.containsValue("two"));
        check("containsValue() false for absent value", !map.containsValue("nope"));
    }

    private static void testSetValue() {
        System.out.println("\n--- setValue() ---");

        JTreeMap<Integer, String> map = new JTreeMap<>();
        map.put(1, "one");
        map.setValue(1, "ONE-changed");
        check("setValue() updates the value for an existing key", "ONE-changed".equals(map.get(1)));
    }

    private static void testSetValueMissingKeyThrows() {
        System.out.println("\n--- setValue() on missing key ---");

        JTreeMap<Integer, String> map = new JTreeMap<>();
        checkThrows("setValue() on a missing key throws IllegalArgumentException", IllegalArgumentException.class,
                () -> map.setValue(1, "x"));
    }

    private static void testRemove_ReturnsRemovedValue() {
        System.out.println("\n--- KNOWN BUG: remove() must return the removed key's value ---");

        JTreeMap<Integer, String> map = new JTreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        String removed = map.remove(2);
        check("KNOWN ISSUE — remove() should return the removed key's value, not the new root's value",
                "two".equals(removed));
        check("removed key is no longer present", !map.containsKey(2));
        check("other keys remain present after removal", map.containsKey(1) && map.containsKey(3));
    }

    private static void testRemove_LastElement_DoesNotThrow() {
        System.out.println("\n--- KNOWN BUG: remove() of the only remaining key must not throw ---");

        JTreeMap<Integer, String> map = new JTreeMap<>();
        map.put(1, "solo");

        // Wrapped defensively: the current implementation throws
        // NullPointerException here (root becomes null, then
        // `root.item.value` is dereferenced). checkNoThrow reports this as
        // a clean failure instead of crashing the whole test run.
        final String[] removedHolder = new String[1];
        checkNoThrow("removing the map's only key does not throw", () -> removedHolder[0] = map.remove(1));

        if (removedHolder[0] != null) {
            check("KNOWN ISSUE — removed value equals the value that was stored",
                    "solo".equals(removedHolder[0]));
        }
        check("map is empty after removing its only key", map.isEmpty());
    }

    private static void testRemove_MissingKeyThrows() {
        System.out.println("\n--- remove() on missing key ---");

        JTreeMap<Integer, String> map = new JTreeMap<>();
        map.put(1, "one");
        checkThrows("remove() on a missing key throws IllegalArgumentException", IllegalArgumentException.class,
                () -> map.remove(999));
    }

    private static void testRemove_NullKeyThrows() {
        System.out.println("\n--- remove(null) ---");

        JTreeMap<Integer, String> map = new JTreeMap<>();
        checkThrows("remove(null) throws IllegalArgumentException", IllegalArgumentException.class,
                () -> map.remove(null));
    }

    private static void testSizeTracking() {
        System.out.println("\n--- KNOWN BUG: size() must track puts/removes/clear ---");

        JTreeMap<Integer, String> map = new JTreeMap<>();
        check("KNOWN ISSUE — size is 0 on a new map", map.size() == 0);

        map.put(1, "one");
        check("KNOWN ISSUE — size is 1 after one put()", map.size() == 1);

        map.put(2, "two");
        map.put(3, "three");
        check("KNOWN ISSUE — size is 3 after three distinct put()s", map.size() == 3);

        map.put(2, "TWO-updated"); // update, not a new key
        check("KNOWN ISSUE — size stays 3 after updating an existing key", map.size() == 3);

        try {
            map.remove(1);
        } catch (Throwable ignored) {
            // remove() correctness is tested elsewhere; here we only care about size bookkeeping
        }
        check("KNOWN ISSUE — size is 2 after removing one key", map.size() == 2);

        map.clear();
        check("size is 0 after clear()", map.size() == 0);
    }

    private static void testKeySet() {
        System.out.println("\n--- keySet() ---");

        JTreeMap<Integer, String> map = new JTreeMap<>();
        map.put(5, "five");
        map.put(3, "three");
        map.put(8, "eight");

        var keys = map.keySet();
        check("keySet() size matches number of inserted keys", keys.size() == 3);
        check("keySet() contains every inserted key",
                keys.contains(5) && keys.contains(3) && keys.contains(8));
        check("keySet() does not contain a key that was never inserted", !keys.contains(99));
    }

    private static void testValues_ContainsAllAndSortedByKey() {
        System.out.println("\n--- values() (in ascending key order via BST in-order traversal) ---");

        JTreeMap<Integer, String> map = new JTreeMap<>();
        map.put(5, "five");
        map.put(3, "three");
        map.put(8, "eight");
        map.put(1, "one");
        map.put(4, "four");

        var vals = map.values();
        check("values() size matches number of inserted entries", vals.size() == 5);
        check("values() contains every inserted value",
                vals.contains("one") && vals.contains("three") && vals.contains("four")
                        && vals.contains("five") && vals.contains("eight"));

        // Keys in ascending order are 1, 3, 4, 5, 8 -> values should come out in that order.
        String[] expectedOrder = {"one", "three", "four", "five", "eight"};
        String[] actualOrder = new String[5];
        var it = vals.iterator();
        int i = 0;
        while (it.hasNext()) {
            actualOrder[i++] = (String) it.next();
        }
        check("values() are produced in ascending key order", Arrays.equals(expectedOrder, actualOrder));
    }

    private static void testClear() {
        System.out.println("\n--- clear() ---");

        JTreeMap<Integer, String> map = new JTreeMap<>();
        map.put(1, "one");
        map.put(2, "two");

        map.clear();
        check("isEmpty() is true after clear()", map.isEmpty());
        check("containsKey() is false for a previously present key after clear()", !map.containsKey(1));
        checkThrows("get() throws for a previously present key after clear()", IllegalArgumentException.class,
                () -> map.get(1));

        check("map is usable again after clear()", map.put(100, "hundred").equals("hundred")
                && map.containsKey(100));
    }

    private static void testLargeDataset_AllKeysSurviveInsertion() {
        System.out.println("\n--- regression guard: many sequential inserts (stresses insert()'s root handling) ---");

        JTreeMap<Integer, String> map = new JTreeMap<>();
        int n = 40;
        for (int i = 0; i < n; i++) {
            map.put(i, "val" + i);
        }

        boolean allFound = true;
        for (int i = 0; i < n; i++) {
            if (!map.containsKey(i) || !("val" + i).equals(map.get(i))) {
                allFound = false;
                break;
            }
        }
        check("every one of " + n + " sequentially inserted keys is retrievable", allFound);
        check("KNOWN ISSUE — size() equals " + n + " after " + n + " distinct inserts", map.size() == n);
        check("keySet() size equals " + n + " after " + n + " distinct inserts", map.keySet().size() == n);
    }

    private static void testStringKeys() {
        System.out.println("\n--- generic type check with String keys ---");

        JTreeMap<String, Integer> map = new JTreeMap<>();
        map.put("banana", 2);
        map.put("apple", 1);
        map.put("cherry", 3);

        check("get() works with String keys", map.get("apple") == 1);
        check("containsKey() works with String keys", map.containsKey("cherry"));

        // keySet() is backed by JHashSet, so its iteration order is not
        // guaranteed — only check contents here. values() (backed by
        // JArrayList via in-order traversal) is where order is meaningful;
        // see testValues_ContainsAllAndSortedByKey().
        var keys = map.keySet();
        check("keySet() contains all inserted String keys",
                keys.contains("apple") && keys.contains("banana") && keys.contains("cherry"));
    }
}
