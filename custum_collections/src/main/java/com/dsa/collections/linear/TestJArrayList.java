package com.dsa.collections.linear;

public class TestJArrayList {
    private static int passed=0, failed=0;
    private static int counter=1;

    private static void check(String label, boolean result) {
        if(result) {
            System.out.println(counter + ".) PASS: " + label);
            passed++;
        } else {
            System.out.println(counter + ".) FAIL: " + label);
            failed++;
        }
        counter++;
    }

    public static void main(String[] args) {
        System.out.println("\n--- Default Constructor ---");
        JArrayList<Integer> list = new JArrayList<>();
        check("default constructor: size=0", list.size()==0);
        check("default constructor: empty list", list.isEmpty());

        System.out.println("\n--- Parameterized constructor with negative value ---");
        try {
            new JArrayList<>(-1);
            check("negative capacity exception throws", false);
        } catch (IllegalArgumentException e) {
            check("negative capacity exception throws", true);
        }

        System.out.println("\n--- size() and isEmpty() ---");
        list.add(10);
        list.add(20);
        list.add(30);
        check("size after three add()=3", list.size()==3);
        check("not empty after add() call", !list.isEmpty());

        System.out.println("\n--- resize() working ---");
        JArrayList<Integer> big = new JArrayList<>();
        for(int i=0; i<10; i++)
            big.add(i+1);
        check("after 10 add(): size=10", big.size()==10);
        check("element at 9th index=10", big.get(9)==10);

        System.out.println("\n--- get() ---");
        check("get(0)=10", list.get(0) == 10);
        check("get(2)=30", list.get(2) == 30);
        try {
            list.get(3);
            check("get(size) throws", false);
        } catch (IllegalArgumentException e) {
            check("get(size) throws", true);
        }

        System.out.println("\n--- set() ---");
        list.set(1, 99);
        check("set(1, 99) -> get(1)=99", list.get(1)==99);

        System.out.println("\n--- add(int, E) ---");
        JArrayList<Integer> ins = new JArrayList<>();
        ins.add(1);
        ins.add(3);
        ins.add(1, 2);
        check("inserted 2 at index 1", ins.get(1)==2);
        check("3 shifter to index 2", ins.get(2)==3);
        check("size increased to 3", ins.size()==3);

        System.out.println("\n--- remove(int) ---");
        JArrayList<Integer> rem = new JArrayList<>();
        rem.add(1); rem.add(2); rem.add(3);
        int val = rem.remove(1);
        check("remove(1) returns 2",    val == 2);
        check("size = 2 after remove",  rem.size() == 2);
        check("3 shifted to index 1",   rem.get(1) == 3);

        System.out.println("\n--- remove(Object) ---");
        JArrayList<String> sr = new JArrayList<>();
        sr.add("a"); sr.add("b"); sr.add("c");
        check("remove('b') = true",     sr.remove("b"));
        check("size = 2",               sr.size() == 2);
        check("'c' shifted to index 1", sr.get(1).equals("c"));
        check("remove('z') = false",  !sr.remove("z"));

        System.out.println("\n--- contains() ---");
        JArrayList<Integer> cl = new JArrayList<>();
        cl.add(5); cl.add(10);
        check("contains 5  = true",  cl.contains(5));
        check("contains 99 = false", !cl.contains(99));

        System.out.println("\n--- indexOf() ---");
        JArrayList<Integer> il = new JArrayList<>();
        il.add(10); il.add(20); il.add(30);
        check("indexOf(20) = 1",  il.indexOf(20) == 1);
        check("indexOf(99) = -1", il.indexOf(99) == -1);

        System.out.println("\n--- sort() ---");
        JArrayList<Integer> sl = new JArrayList<>();
        sl.add(3); sl.add(1); sl.add(2);
        sl.sort();
        check("sort: [1,2,3] → get(0)=1", sl.get(0) == 1);
        check("sort: [1,2,3] → get(1)=2", sl.get(1) == 2);
        check("sort: [1,2,3] → get(2)=3", sl.get(2) == 3);

        System.out.println("\n--- getFirst(), getLast() ---");
        JArrayList<Integer> fl = new JArrayList<>();
        fl.add(1); fl.add(2); fl.add(3);
        check("getFirst = 1",  fl.getFirst() == 1);
        check("getLast  = 3",  fl.getLast()  == 3);
        check("removeFirst = 1", fl.removeFirst() == 1);
        check("removeLast  = 3", fl.removeLast()  == 3);
        check("size = 1 after removes", fl.size() == 1);

        System.out.println("\n--- clear() ---");
        JArrayList<Integer> cl2 = new JArrayList<>();
        cl2.add(1); cl2.add(2);
        cl2.clear();
        check("isEmpty after clear", cl2.isEmpty());
        check("size = 0 after clear", cl2.size() == 0);

        System.out.println("\n--- reversed() ---");
        JArrayList<Integer> rl = new JArrayList<>();
        rl.add(1); rl.add(2); rl.add(3);
        JArrayList<Integer> rv = (JArrayList<Integer>) rl.reversed();
        check("reversed get(0) = 3", rv.get(0) == 3);
        check("reversed get(1) = 2", rv.get(1) == 2);
        check("reversed get(2) = 1", rv.get(2) == 1);
        check("original get(0) = 1 (unchanged)", rl.get(0) == 1);

        System.out.println("\n--- iterator() ---");
        JArrayList<Integer> it = new JArrayList<>();
        it.add(10); it.add(20); it.add(30);
        var itr = it.iterator();
        check("hasNext = true before first next()", itr.hasNext());
        check("next() = 10", itr.next().equals(10));
        check("next() = 20", itr.next().equals(20));
        check("next() = 30", itr.next().equals(30));
        check("hasNext = false after last element", !itr.hasNext());

        System.out.println("\n=============================");
        System.out.println("  Passed : " + passed);
        System.out.println("  Failed : " + failed);
        System.out.println("  Total  : " + (passed + failed));
        System.out.println("=============================");
    }
}