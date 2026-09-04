package com.dsa.collections.linear;

import com.dsa.collections.exceptions.ListEmptyException;

public class JQueueTest {

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
                        + ", got " + e.getClass().getSimpleName() + ": " + e.getMessage() + ")");
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

    static JLinkedQueue<Integer> queueOf(int... values) {
        JLinkedQueue<Integer> q = new JLinkedQueue<>();
        for (int v : values) q.enqueue(v);
        return q;
    }
    
    static void testConstructor() {
        System.out.println("\n--- Constructor ---");

        JLinkedQueue<Integer> q = new JLinkedQueue<>();
        check("constructor: isEmpty() true on new queue", q.isEmpty());
        check("constructor: size() == 0",               q.size() == 0);
    }

    static void testEnqueue() throws Exception {
        System.out.println("\n--- enqueue(E) ---");

        JLinkedQueue<Integer> q = new JLinkedQueue<>();

        q.enqueue(10);
        check("enqueue: size becomes 1 after first enqueue", q.size() == 1);
        check("enqueue: isEmpty() false after enqueue",  !q.isEmpty());
        check("enqueue: front element is 10",                 q.peek().equals(10));

        q.enqueue(20);
        q.enqueue(30);
        check("enqueue: size is 3 after three enqueues", q.size() == 3);
        check("enqueue: front is still 10 (FIFO)",        q.peek().equals(10));
    }

    static void testDequeue() throws Exception {
        System.out.println("\n--- dequeue() ---");

        JLinkedQueue<Integer> q = queueOf(10, 20, 30);

        Integer d1 = q.dequeue();
        check("dequeue: first dequeue returns 10 (FIFO)",  d1.equals(10));
        check("dequeue: size decreases to 2",              q.size() == 2);
        check("dequeue: new front is 20",                  q.peek().equals(20));

        Integer d2 = q.dequeue();
        check("dequeue: second dequeue returns 20",        d2.equals(20));
        check("dequeue: size decreases to 1",              q.size() == 1);
        check("dequeue: front and rear both 30",           q.peek().equals(30));

        Integer d3 = q.dequeue();
        check("dequeue: third dequeue returns 30",         d3.equals(30));
        check("dequeue: queue is empty after all removed", q.isEmpty());
        check("dequeue: size is 0",                        q.size() == 0);

        // dequeue on empty queue must throw
        checkThrows("dequeue: empty queue -> ListEmptyException",
                ListEmptyException.class, q::dequeue);
    }

    static void testFIFOOrder() throws Exception {
        System.out.println("\n--- FIFO Order ---");

        JLinkedQueue<Integer> q = queueOf(1, 2, 3, 4, 5);

        for (int expected = 1; expected <= 5; expected++) {
            Integer actual = q.dequeue();
            check("FIFO: dequeued value is " + expected, actual.equals(expected));
        }
        check("FIFO: queue empty after full drain", q.isEmpty());
    }

    static void testIsEmptyAndSize() throws Exception {
        System.out.println("\n--- isEmpty() / size() ---");

        JLinkedQueue<Integer> q = new JLinkedQueue<>();
        check("isEmpty: true on fresh queue",    q.isEmpty());
        check("size:    0 on fresh queue",       q.size() == 0);

        q.enqueue(42);
        check("isEmpty: false after enqueue",    !q.isEmpty());
        check("size:    1 after one enqueue",    q.size() == 1);

        q.dequeue();
        check("isEmpty: true after draining",    q.isEmpty());
        check("size:    0 after draining",       q.size() == 0);
    }

    static void testGetFirstAndLast() throws Exception {
        System.out.println("\n--- peek() / getLastNode() ---");

        JLinkedQueue<Integer> q = queueOf(100, 200, 300);

        check("peek: returns 100",       q.peek().equals(100));

        // peek must not alter the queue
        check("peek: size unchanged (3)", q.size() == 3);

        // single element — both front and rear point to same node
        JLinkedQueue<Integer> single = queueOf(42);
        check("peek: single element is 42", single.peek().equals(42));

        // empty queue must throw
        checkThrows("peek: empty queue -> ListEmptyException",
                ListEmptyException.class,
                () -> new JLinkedQueue<Integer>().peek());
    }

    static void testQueueIntoArray() throws Exception {
        System.out.println("\n--- queueIntoArray() ---");

        JLinkedQueue<Integer> q = queueOf(10, 20, 30);
        Object[] arr = q.toArray();

        check("queueIntoArray: length matches size",  arr.length == q.size());
        check("queueIntoArray: arr[0] == 10 (front)", arr[0].equals(10));
        check("queueIntoArray: arr[1] == 20",         arr[1].equals(20));
        check("queueIntoArray: arr[2] == 30 (rear)",  arr[2].equals(30));

        // array should be independent — dequeue does not affect already-taken snapshot
        q.dequeue();
        check("queueIntoArray: snapshot unaffected by later dequeue", arr[0].equals(10));
    }

    static void testReverseQue() throws Exception {
        System.out.println("\n--- reversed()() ---");

        JLinkedQueue<Integer> q = queueOf(1, 2, 3, 4, 5);

        var rev = (JLinkedList<Integer>) q.reversed();

        check("reversed(): reversed size == 5",        rev.size() == 5);
        check("reversed(): first element is 5",        rev.get(0).equals(5));
        check("reversed(): last  element is 1",        rev.get(4).equals(1));
        check("reversed(): middle element (index 2) is 3", rev.get(2).equals(3));

        // original queue must be untouched
        check("reversed(): original front still 1",    q.peek().equals(1));
        check("reversed(): original size still 5",     q.size() == 5);

        // empty queue
        check("reversed(): empty queue returns null",
                new JLinkedQueue<Integer>().reversed() == null);
    }

    static void testInterleavedOperations() throws Exception {
        System.out.println("\n--- Interleaved enqueue / dequeue ---");

        JLinkedQueue<Integer> q = new JLinkedQueue<>();

        q.enqueue(1);
        q.enqueue(2);
        Integer d1 = q.dequeue();
        check("interleaved: dequeue after 2 enqueues returns 1", d1.equals(1));
        check("interleaved: size is 1",                          q.size() == 1);

        q.enqueue(3);
        q.enqueue(4);
        check("interleaved: size is 3 after 2 more enqueues",    q.size() == 3);
        check("interleaved: front is 2",                         q.peek().equals(2));

        Integer d2 = q.dequeue();
        Integer d3 = q.dequeue();
        check("interleaved: second dequeue returns 2",            d2.equals(2));
        check("interleaved: third  dequeue returns 3",            d3.equals(3));
        check("interleaved: one element left",                    q.size() == 1);
        check("interleaved: last remaining element is 4",         q.peek().equals(4));
    }

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("         JQueue Test Suite             ");
        System.out.println("========================================");

        try {
            testConstructor();
            testEnqueue();
            testDequeue();
            testFIFOOrder();
            testIsEmptyAndSize();
            testGetFirstAndLast();
            testQueueIntoArray();
            testReverseQue();
            testInterleavedOperations();
        } catch (Exception e) {
            System.out.println("\n[FATAL] Unexpected exception escaped a test group:");
            e.printStackTrace();
        }

        printSummary();
    }
}