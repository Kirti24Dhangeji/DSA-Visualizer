package com.dsa.collections.linear;

import java.util.Arrays;

import com.dsa.collections.exceptions.StackEmptyException;
import com.dsa.collections.interfaces.JSequencedCollection;

public class JStack<E> extends JArrayList<E> {
    /**
     * container for the stack is already exists in the parent class.
     * we only need to call appropriate functions to perform stack operations.
     * The private field of the stack class is the index representing the top element.
     */
    private int top;

    /**
     * Constructor constructs a new stack with the initial capacity provided.
     * @param capacity
     */
    public JStack() {
        super();
        this.top = -1;
    }

    /**
     * Constructor constructs a new stack with the initial capacity provided.
     * @param capacity
     */
    public JStack(int capacity) {
        super(capacity);
        this.top = -1;
    }

    /**
     * Size of the stack is the one more than the top.
     * As the indexing is 0-based.
     */
    @Override
    public int size() {
        return top+1;
    }

    /**
     * @return TRUE if top is at -1. (no element in the stack.)
     */
    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    /**
     * Convert the stack into the array with the size as 'top + 1'
     */
    @Override
    public Object[] toArray() {
        Object tempArray[] = Arrays.copyOf(super.array, top+1);

        return tempArray;
    }

    /**
     * @return list containing all stack elements in reversed order.
     */
    @Override
    public JSequencedCollection<E> reversed() {
        return super.reversed();
    }

    /**
     * clear the whole stack.
     */
    @Override
    public void clear() {
        super.clear();
    }

    /**
     * Add new element at last of the stack.
     * 
     * @param element
     * @return true if element is added successfully.
     */
    public boolean push(E element) {
        top++;
        return super.add(element);
    }

    public E pop() {
        if(top == -1)
            throw new StackEmptyException("Stack is empty exception.!");

        top--;
        return super.removeLast();
    }

    public E peek() {
        if(top == -1)
            throw new StackEmptyException("Stack is empty exception.!");

        return super.getLast();
    }

    public int top() {
        return top;
    }
}