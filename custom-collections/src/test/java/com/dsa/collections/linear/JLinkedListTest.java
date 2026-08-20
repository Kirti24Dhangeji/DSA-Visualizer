package com.dsa.collections.linear;

import com.dsa.collections.exceptions.ListEmptyException;

public class JLinkedListTest {

    // ─────────────────────────────────────────────
    //  Test infrastructure
    // ─────────────────────────────────────────────

    static int passed = 0;
    static int failed = 0;

    static void check(String testName, boolean condition) {
        if (condition) {
            System.out.println("  [PASS] " + testName);
            passed++;
        } else {
            System.out.println("  [FAIL] " + testName);
            failed++;
        }
    }

    /** Ensures a block throws the expected exception type. */
    static void checkThrows(String testName, Class<? extends Exception> expected, ThrowingRunnable block) {
        try {
            block.run();
            System.out.println("  [FAIL] " + testName + "  (no exception thrown)");
            failed++;
        } catch (Exception e) {
            if (expected.isInstance(e)) {
                System.out.println("  [PASS] " + testName);
                passed++;
            } else {
                System.out.println("  [FAIL] " + testName
                        + "  (expected " + expected.getSimpleName()
                        + ", got " + e.getClass().getSimpleName() + ")");
                failed++;
            }
        }
    }

    @FunctionalInterface
    interface ThrowingRunnable {
        void run() throws Exception;
    }

    static void printSummary() {
        System.out.println("\n========================================");
        System.out.println("  Results : " + passed + " passed, " + failed + " failed");
        System.out.println("========================================\n");
    }

    // ─────────────────────────────────────────────
    //  Helper – build a list of integers quickly
    // ─────────────────────────────────────────────

    static JLinkedList<Integer> listOf(int... values) {
        JLinkedList<Integer> list = new JLinkedList<>();
        for (int v : values) list.add(v);
        return list;
    }

    // ─────────────────────────────────────────────
    //  Test groups
    // ─────────────────────────────────────────────

    static void testConstructorAndBasics() {
        System.out.println("\n--- Constructor & Basics ---");

        JLinkedList<Integer> list = new JLinkedList<>();
        check("Default constructor: isEmpty()", list.isEmpty());
        check("Default constructor: size() == 0", list.size() == 0);
    }

    // ── add(E e) ──────────────────────────────────

    static void testAdd() throws Exception {
        System.out.println("\n--- add(E e) ---");

        JLinkedList<Integer> list = new JLinkedList<>();
        list.add(10);
        check("add: size becomes 1", list.size() == 1);
        check("add: get(0) == 10", list.get(0).equals(10));

        list.add(20);
        list.add(30);
        check("add: size becomes 3", list.size() == 3);
        check("add: last element get(2) == 30", list.get(2).equals(30));
        check("add: middle element get(1) == 20", list.get(1).equals(20));
        check("add: isEmpty() false after adds", !list.isEmpty());
    }

    // ── addFirst / addLast ────────────────────────

    static void testAddFirstAddLast() throws Exception {
        System.out.println("\n--- addFirst(E) / addLast(E) ---");

        JLinkedList<Integer> list = listOf(20, 30);

        list.addFirst(10);
        check("addFirst: size increases", list.size() == 3);
        check("addFirst: new head is 10", list.get(0).equals(10));
        check("addFirst: second element still 20", list.get(1).equals(20));

        list.addLast(40);
        check("addLast: size increases", list.size() == 4);
        check("addLast: new tail is 40", list.getLast().equals(40));
        check("addLast: prev-to-last still 30", list.get(2).equals(30));
    }

    // ── add(int index, E element) ─────────────────

    static void testAddAtIndex() throws Exception {
        System.out.println("\n--- add(int index, E element) ---");

        JLinkedList<Integer> list = listOf(10, 30, 40);

        list.add(1, 20);           // insert between 10 and 30
        check("add(index): size is 4", list.size() == 4);
        check("add(index): element at 1 is 20", list.get(1).equals(20));
        check("add(index): element at 2 is 30", list.get(2).equals(30));

        list.add(0, 5);            // insert at head
        check("add(0): head is 5", list.get(0).equals(5));
        check("add(0): second is 10", list.get(1).equals(10));

        list.add(list.size(), 99); // insert at tail
        check("add(size): tail is 99", list.get(list.size() - 1).equals(99));

        checkThrows("add(index): negative index -> IllegalArgumentException",
                IllegalArgumentException.class, () -> list.add(-1, 0));
        checkThrows("add(index): index > size -> IllegalArgumentException",
                IllegalArgumentException.class, () -> list.add(list.size() + 1, 0));
    }

    // ── get / set ─────────────────────────────────

    static void testGetSet() throws Exception {
        System.out.println("\n--- get(int) / set(int, E) ---");

        JLinkedList<Integer> list = listOf(10, 20, 30);

        check("get: index 0", list.get(0).equals(10));
        check("get: index 1", list.get(1).equals(20));
        check("get: index 2", list.get(2).equals(30));

        list.set(1, 99);
        check("set: value updated at index 1", list.get(1).equals(99));
        check("set: neighbours unchanged (0)", list.get(0).equals(10));
        check("set: neighbours unchanged (2)", list.get(2).equals(30));

        checkThrows("set: negative index -> IllegalArgumentException",
                IllegalArgumentException.class, () -> list.set(-1, 0));

        checkThrows("get: empty list -> ListEmptyException",
                ListEmptyException.class,
                () -> new JLinkedList<Integer>().get(0));
    }

    // ── getFirst / getLast ─────────────────────────

    static void testGetFirstLast() throws Exception {
        System.out.println("\n--- getFirst() / getLast() ---");

        JLinkedList<Integer> list = listOf(1, 2, 3);

        check("getFirst: returns 1", list.getFirst().equals(1));
        check("getLast:  returns 3", list.getLast().equals(3));

        checkThrows("getFirst: empty list -> ListEmptyException",
                ListEmptyException.class,
                () -> new JLinkedList<Integer>().getFirst());
        checkThrows("getLast:  empty list -> ListEmptyException",
                ListEmptyException.class,
                () -> new JLinkedList<Integer>().getLast());
    }

    // ── remove(Object o) ──────────────────────────

    static void testRemoveByObject() throws Exception {
        System.out.println("\n--- remove(Object o) ---");

        JLinkedList<Integer> list = listOf(10, 20, 30, 40);

        // remove middle
        boolean r1 = list.remove((Object) 20);
        check("remove(obj): returns true when found", r1);
        check("remove(obj): size decreases", list.size() == 3);
        check("remove(obj): element gone (indexOf -1)", list.indexOf(20) == -1);
        check("remove(obj): neighbours linked (10→30)", list.get(1).equals(30));

        // remove head
        list.remove((Object) 10);
        check("remove(obj): head removal -> new head is 30", list.get(0).equals(30));

        // remove tail
        list.remove((Object) 40);
        check("remove(obj): tail removal -> new tail is 30", list.getLast().equals(30));

        // remove absent element
        boolean r2 = list.remove((Object) 999);
        check("remove(obj): returns false when absent", !r2);

        // empty list
        boolean r3 = new JLinkedList<Integer>().remove((Object) 1);
        check("remove(obj): empty list returns false", !r3);
    }

    // ── remove(int index) ─────────────────────────

    static void testRemoveByIndex() throws Exception {
        System.out.println("\n--- remove(int index) ---");

        JLinkedList<Integer> list = listOf(10, 20, 30, 40);

        Integer r1 = list.remove(1);
        check("remove(index): returns removed value 20", r1.equals(20));
        check("remove(index): size decreases", list.size() == 3);
        check("remove(index): element at 1 is now 30", list.get(1).equals(30));

        list.remove(0);          // remove head
        check("remove(0): new head is 30", list.get(0).equals(30));

        list.remove(list.size() - 1); // remove tail
        check("remove(last): new tail is 30", list.getLast().equals(30));

        checkThrows("remove(index): negative -> IllegalArgumentException",
                IllegalArgumentException.class, () -> list.remove(-1));
        checkThrows("remove(index): >= size -> IllegalArgumentException",
                IllegalArgumentException.class, () -> list.remove(list.size()));
        checkThrows("remove(index): empty list -> ListEmptyException",
                ListEmptyException.class,
                () -> new JLinkedList<Integer>().remove(0));
    }

    // ── removeFirst / removeLast ──────────────────

    static void testRemoveFirstLast() throws Exception {
        System.out.println("\n--- removeFirst() / removeLast() ---");

        JLinkedList<Integer> list = listOf(1, 2, 3);

        Integer rf = list.removeFirst();
        check("removeFirst: returns 1", rf.equals(1));
        check("removeFirst: new head is 2", list.getFirst().equals(2));
        check("removeFirst: size is 2", list.size() == 2);

        Integer rl = list.removeLast();
        check("removeLast: returns 3", rl.equals(3));
        check("removeLast: new tail is 2", list.getLast().equals(2));
        check("removeLast: size is 1", list.size() == 1);

        checkThrows("removeFirst: empty -> ListEmptyException",
                ListEmptyException.class,
                () -> new JLinkedList<Integer>().removeFirst());
        checkThrows("removeLast:  empty -> ListEmptyException",
                ListEmptyException.class,
                () -> new JLinkedList<Integer>().removeLast());
    }

    // ── contains ──────────────────────────────────

    static void testContains() throws Exception {
        System.out.println("\n--- contains(Object o) ---");

        JLinkedList<Integer> list = listOf(10, 20, 30);

        check("contains: present element (20)", list.contains(20));
        check("contains: head element (10)", list.contains(10));
        check("contains: tail element (30)", list.contains(30));
        check("contains: absent element (99)", !list.contains(99));

        checkThrows("contains: empty list -> ListEmptyException",
                ListEmptyException.class,
                () -> new JLinkedList<Integer>().contains(1));
    }

    // ── indexOf ───────────────────────────────────

    static void testIndexOf() throws Exception {
        System.out.println("\n--- indexOf(Object o) ---");

        JLinkedList<Integer> list = listOf(10, 20, 30, 20);

        check("indexOf: first occurrence of 20 is 1", list.indexOf(20) == 1);
        check("indexOf: head element index is 0",    list.indexOf(10) == 0);
        check("indexOf: tail element index is 3",    list.indexOf(20) == 1); // first occurrence
        check("indexOf: absent element returns -1",  list.indexOf(99) == -1);

        checkThrows("indexOf: empty list -> ListEmptyException",
                ListEmptyException.class,
                () -> new JLinkedList<Integer>().indexOf(1));
    }

    // ── clear ─────────────────────────────────────

    static void testClear() {
        System.out.println("\n--- clear() ---");

        JLinkedList<Integer> list = listOf(1, 2, 3);
        list.clear();

        check("clear: size is 0",    list.size() == 0);
        check("clear: isEmpty true", list.isEmpty());
    }

    // ── addAll ────────────────────────────────────

    static void testAddAll() throws Exception {
        System.out.println("\n--- addAll(JCollection) ---");

        JLinkedList<Integer> base  = listOf(1, 2, 3);
        JLinkedList<Integer> extra = listOf(4, 5, 6);

        boolean result = base.addAll(extra);
        check("addAll: returns true", result);
        check("addAll: size is 6", base.size() == 6);
        check("addAll: first appended element at index 3 is 4", base.get(3).equals(4));
        check("addAll: last element is 6", base.getLast().equals(6));

        boolean emptyResult = base.addAll(new JLinkedList<>());
        check("addAll: empty collection returns false", !emptyResult);
    }

    // ── sort ──────────────────────────────────────

    static void testSort() throws Exception {
        System.out.println("\n--- sort() ---");

        JLinkedList<Integer> list = listOf(30, 10, 50, 20, 40);
        list.sort();

        check("sort: size unchanged", list.size() == 5);
        check("sort: index 0 is 10", list.get(0).equals(10));
        check("sort: index 1 is 20", list.get(1).equals(20));
        check("sort: index 2 is 30", list.get(2).equals(30));
        check("sort: index 3 is 40", list.get(3).equals(40));
        check("sort: index 4 is 50", list.get(4).equals(50));

        checkThrows("sort: empty list -> ListEmptyException",
                ListEmptyException.class,
                () -> new JLinkedList<Integer>().sort());
    }

    // ── reversed ──────────────────────────────────

    static void testReversed() throws Exception {
        System.out.println("\n--- reversed() ---");

        JLinkedList<Integer> list = listOf(1, 2, 3, 4, 5);
        var rev = (JLinkedList<Integer>) list.reversed();

        check("reversed: size unchanged", rev.size() == 5);
        check("reversed: first element is 5", rev.get(0).equals(5));
        check("reversed: last element is 1",  rev.get(4).equals(1));
        check("reversed: middle element is 3", rev.get(2).equals(3));
        check("reversed: original list unchanged (head still 1)", list.get(0).equals(1));

        check("reversed: empty list returns null", new JLinkedList<Integer>().reversed() == null);
    }

    // ── iterator ──────────────────────────────────

    static void testIterator() {
        System.out.println("\n--- iterator() ---");

        JLinkedList<Integer> list = listOf(10, 20, 30);

        var itr = list.iterator();
        check("iterator: hasNext true at start", itr.hasNext());

        check("iterator: next() = 10", itr.next().equals(10));
        check("iterator: next() = 20", itr.next().equals(20));
        check("iterator: next() = 30", itr.next().equals(30));
        check("iterator: hasNext false at end", !itr.hasNext());
    }

    // ── toArray ───────────────────────────────────

    static void testToArray() {
        System.out.println("\n--- toArray() ---");

        JLinkedList<Integer> list = listOf(10, 20, 30);
        Object[] arr = list.toArray();

        check("toArray: length == size", arr.length == list.size());
        check("toArray: arr[0] == 10", arr[0].equals(10));
        check("toArray: arr[1] == 20", arr[1].equals(20));
        check("toArray: arr[2] == 30", arr[2].equals(30));
    }

    // ── containsAll ───────────────────────────────

    static void testContainsAll() throws Exception {
        System.out.println("\n--- containsAll(JCollection) ---");

        JLinkedList<Integer> list = listOf(10, 20, 30, 40, 50);
        JLinkedList<Integer> sub  = listOf(20, 30, 40);   // contiguous subsequence

        check("containsAll: contiguous sub-sequence found", list.containsAll(sub));

        JLinkedList<Integer> absent = listOf(20, 99);
        check("containsAll: absent element returns false", !list.containsAll(absent));

        checkThrows("containsAll: empty list -> ListEmptyException",
                ListEmptyException.class,
                () -> new JLinkedList<Integer>().containsAll(sub));
        checkThrows("containsAll: empty collection arg -> IllegalArgumentException",
                IllegalArgumentException.class,
                () -> list.containsAll(new JLinkedList<>()));
    }

    // ── copyOf ────────────────────────────────────

    static void testCopyOf() throws Exception {
        System.out.println("\n--- copyOf(JCollection) ---");

        JLinkedList<Integer> src  = listOf(1, 2, 3);
        JLinkedList<Integer> copy = new JLinkedList<>();
        var result = copy.copyOf(src);

        check("copyOf: size matches", result.size() == 3);
        check("copyOf: element 0", result.get(0).equals(1));
        check("copyOf: element 2", result.get(2).equals(3));

        // mutation independence
        src.add(99);
        check("copyOf: copy unaffected by src mutation", result.size() == 3);

        checkThrows("copyOf: null arg -> IllegalArgumentException",
                IllegalArgumentException.class, () -> copy.copyOf(null));
        checkThrows("copyOf: empty arg -> IllegalArgumentException",
                IllegalArgumentException.class, () -> copy.copyOf(new JLinkedList<>()));
    }

    // ─────────────────────────────────────────────
    //  main
    // ─────────────────────────────────────────────

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("       JLinkedList Test Suite           ");
        System.out.println("========================================");

        try {
            testConstructorAndBasics();
            testAdd();
            testAddFirstAddLast();
            testAddAtIndex();
            testGetSet();
            testGetFirstLast();
            testRemoveByObject();
            testRemoveByIndex();
            testRemoveFirstLast();
            testContains();
            testIndexOf();
            testClear();
            testAddAll();
            testSort();
            testReversed();
            testIterator();
            testToArray();
            testContainsAll();
            testCopyOf();
        } catch (Exception e) {
            System.out.println("\n[FATAL] Unexpected exception escaped a test group:");
            e.printStackTrace();
        }

        printSummary();
    }
}