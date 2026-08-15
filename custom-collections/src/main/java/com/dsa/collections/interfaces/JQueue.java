package com.dsa.collections.interfaces;

public interface JQueue<E> extends JCollection<E> {
    
    boolean enqueue(E e);
    E dequeue();
    E peek();
}