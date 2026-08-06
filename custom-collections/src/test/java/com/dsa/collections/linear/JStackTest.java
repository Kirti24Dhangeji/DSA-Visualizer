package com.dsa.collections.linear;

import com.dsa.collections.exceptions.StackEmptyException;

public class JStackTest {
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

    public static void main(String[] args) {
        System.out.println("\nDefault Constructor.");
        JStack<String> stack1 = new JStack<>();
        check("stack initialization checking with size: ", stack1.size() == 0);
        check("stack initialization checking with top: ", stack1.top() == -1);
        check("stack initialization checking with capacity: ", stack1.capacity() == 5);


        
        System.out.println("\nParameterized Constructor.");
        JStack<String> stack2 = new JStack<>(4);
        check("stack initialization checking with size: ", stack2.size() == 0);
        check("stack initialization checking with top: ", stack2.top() == -1);
        check("stack initialization checking with capacity: ", stack2.capacity() == 4);

        // use a generalized name for stack
        JStack<String> stack = stack2;

        System.out.println("\nPush() and Peek().");
        stack2.push("Saksham");
        check("stack insertion checking with size: ", stack.size() == 1);
        check("stack insertion checking with top: ", stack.top() == 0);
        check("stack insertion checking with peek: ", stack.peek().equals("Saksham"));

        stack2.push("Kirti");
        check("stack insertion checking with size: ", stack.size() == 2);
        check("stack insertion checking with top: ", stack.top() == 1);
        check("stack insertion checking with peek: ", stack.peek().equals("Kirti"));

        stack2.push("Pushkar");
        check("stack insertion checking with size: ", stack.size() == 3);
        check("stack insertion checking with top: ", stack.top() == 2);
        check("stack insertion checking with peek: ", stack.peek().equals("Pushkar"));



        System.out.println("\nPop() and Peek().");
        check("top element after pop() calling at first: ", stack2.pop().equals("Pushkar"));
        check("stack deletion checking with size: ", stack.size() == 2);
        check("stack deletion checking with top: ", stack.top() == 1);
        check("stack insertion checking with peek: ", stack.peek().equals("Kirti"));

        check("top element after pop() calling at second: ", stack2.pop().equals("Kirti"));
        check("stack deletion checking with size: ", stack.size() == 1);
        check("stack deletion checking with top: ", stack.top() == 0);
        check("stack deletion checking with peek: ", stack.peek().equals("Saksham"));

        check("top element after pop() calling at third: ", stack2.pop().equals("Saksham"));
        check("stack deletion checking with size: ", stack.size() == 0);
        check("stack deletion checking with top: ", stack.top() == -1);
        try {
            stack.peek();
            check("stack deletion checking with peek: pass", false);
        } catch(StackEmptyException e) {
            check("stack deletion checking with peek: pass", true);
        }

        try {
            stack.pop();
            check("stack deletion checking with pop: pass", false);
        } catch(StackEmptyException e) {
            check("stack deletion checking with pop: pass", true);
        }



        System.out.println("\n=============================");
        System.out.println("  Passed : " + passed);
        System.out.println("  Failed : " + failed);
        System.out.println("  Total  : " + (passed + failed));
        System.out.println("=============================");
    }
}