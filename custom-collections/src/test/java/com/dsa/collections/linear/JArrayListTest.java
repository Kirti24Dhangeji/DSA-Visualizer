package com.dsa.collections.linear;

import com.dsa.collections.exceptions.ListEmptyException;
// import com.dsa.collections.linear.JArrayList;

public class JArrayListTest {

    static int passed = 0, failed = 0;

    // Simple assertion helper
    static void check(String label, boolean condition) {
        if (condition) {
            System.out.println("  PASS  " + label);
            passed++;
        } else {
            System.out.println("  FAIL  " + label);
            failed++;
        }
    }

    public static void main(String[] args) throws ListEmptyException {

        // ── 1. Constructor ───────────────────────────────────────────────────
        System.out.println("\n--- Constructor ---");
        JArrayList<Integer> list = new JArrayList<>();
        check("default constructor: empty",    list.isEmpty());
        check("default constructor: size = 0", list.size() == 0);

        try {
            new JArrayList<>(-1);
            check("negative capacity throws", false);
        } catch (IllegalArgumentException e) {
            check("negative capacity throws", true);
        }

        // ── 2. add() and size() ──────────────────────────────────────────────
        System.out.println("\n--- add() and size() ---");
        list.add(10); list.add(20); list.add(30);
        check("size after 3 adds = 3", list.size() == 3);
        check("not empty after adds",  !list.isEmpty());

        // ── 3. Resize beyond capacity (default = 5) ──────────────────────────
        System.out.println("\n--- Resize ---");
        JArrayList<Integer> big = new JArrayList<>();
        for(int i = 1; i <= 10; i++) big.add(i);
        check("size = 10 after 10 adds", big.size() == 10);
        check("element at index 9 = 10", big.get(9) == 10);

        // ── 4. get() ─────────────────────────────────────────────────────────
        System.out.println("\n--- get() ---");
        check("get(0) = 10", list.get(0) == 10);
        check("get(2) = 30", list.get(2) == 30);
        try {
            list.get(3); // size = 3, index 3 is out of bounds
            check("get(size) throws", false);
        } catch (IllegalArgumentException e) {
            check("get(size) throws", true);
        }

        // ── 5. set() ─────────────────────────────────────────────────────────
        System.out.println("\n--- set() ---");
        list.set(1, 99);
        check("set(1, 99) → get(1) = 99", list.get(1) == 99);

        // ── 6. add(index, e) ─────────────────────────────────────────────────
        System.out.println("\n--- add(index, e) ---");
        JArrayList<Integer> ins = new JArrayList<>();
        ins.add(1); ins.add(3);
        ins.add(1, 2);   // insert 2 between 1 and 3
        check("inserted 2 at index 1",     ins.get(1) == 2);
        check("3 shifted to index 2",      ins.get(2) == 3);
        check("size increased to 3",       ins.size() == 3);

        // ── 7. remove(int) ───────────────────────────────────────────────────
        System.out.println("\n--- remove(int) ---");
        JArrayList<Integer> rem = new JArrayList<>();
        rem.add(1); rem.add(2); rem.add(3);
        int val = rem.remove(1);
        check("remove(1) returns 2",    val == 2);
        check("size = 2 after remove",  rem.size() == 2);
        check("3 shifted to index 1",   rem.get(1) == 3);

        // ── 8. remove(Object) ────────────────────────────────────────────────
        System.out.println("\n--- remove(Object) ---");
        JArrayList<String> sr = new JArrayList<>();
        sr.add("a"); sr.add("b"); sr.add("c");
        check("remove('b') = true",     sr.remove("b"));
        check("size = 2",               sr.size() == 2);
        check("'c' shifted to index 1", sr.get(1).equals("c"));
        check("remove absent = false",  !sr.remove("z"));

        // ── 9. contains() ────────────────────────────────────────────────────
        System.out.println("\n--- contains() ---");
        JArrayList<Integer> cl = new JArrayList<>();
        cl.add(5); cl.add(10);
        check("contains 5  = true",  cl.contains(5));
        check("contains 99 = false", !cl.contains(99));

        // ── 10. indexOf() ────────────────────────────────────────────────────
        System.out.println("\n--- indexOf() ---");
        JArrayList<Integer> il = new JArrayList<>();
        il.add(10); il.add(20); il.add(30);
        check("indexOf(20) = 1",  il.indexOf(20) == 1);
        check("indexOf(99) = -1", il.indexOf(99) == -1);

        // ── 11. sort() ───────────────────────────────────────────────────────
        System.out.println("\n--- sort() ---");
        JArrayList<Integer> sl = new JArrayList<>();
        sl.add(3); sl.add(1); sl.add(2);
        sl.sort();
        check("sort: [1,2,3] → get(0)=1", sl.get(0) == 1);
        check("sort: [1,2,3] → get(1)=2", sl.get(1) == 2);
        check("sort: [1,2,3] → get(2)=3", sl.get(2) == 3);

        // ── 12. First / Last ─────────────────────────────────────────────────
        System.out.println("\n--- getFirst / getLast ---");
        JArrayList<Integer> fl = new JArrayList<>();
        fl.add(1); fl.add(2); fl.add(3);
        check("getFirst = 1",  fl.getFirst() == 1);
        check("getLast  = 3",  fl.getLast()  == 3);
        check("removeFirst = 1", fl.removeFirst() == 1);
        check("removeLast  = 3", fl.removeLast()  == 3);
        check("size = 1 after removes", fl.size() == 1);

        // ── 13. clear() ──────────────────────────────────────────────────────
        System.out.println("\n--- clear() ---");
        JArrayList<Integer> cl2 = new JArrayList<>();
        cl2.add(1); cl2.add(2);
        cl2.clear();
        check("isEmpty after clear", cl2.isEmpty());
        check("size = 0 after clear", cl2.size() == 0);

        // ── 14. reversed() ───────────────────────────────────────────────────
        System.out.println("\n--- reversed() ---");
        JArrayList<Integer> rl = new JArrayList<>();
        rl.add(1); rl.add(2); rl.add(3);
        JArrayList<Integer> rv = (JArrayList<Integer>) rl.reversed();
        check("reversed get(0) = 3", rv.get(0) == 3);
        check("reversed get(1) = 2", rv.get(1) == 2);
        check("reversed get(2) = 1", rv.get(2) == 1);
        check("original get(0) = 1 (unchanged)", rl.get(0) == 1);

        // ── 15. Iterator ─────────────────────────────────────────────────────
        System.out.println("\n--- iterator() ---");
        JArrayList<Integer> it = new JArrayList<>();
        it.add(10); it.add(20); it.add(30);
        var itr = it.iterator();
        check("hasNext = true before first next()", itr.hasNext());
        check("next() = 10", itr.next().equals(10));
        check("next() = 20", itr.next().equals(20));
        check("next() = 30", itr.next().equals(30));
        check("hasNext = false after last element", !itr.hasNext());

        // ── Result ───────────────────────────────────────────────────────────
        System.out.println("\n=============================");
        System.out.println("  Passed : " + passed);
        System.out.println("  Failed : " + failed);
        System.out.println("  Total  : " + (passed + failed));
        System.out.println("=============================");
    }
}