package com.dsa.collections.interfaces;

public interface JSequencedCollection<E> extends JCollection<E> {
    JSequencedCollection<E> reversed();
    void addFirst(E e);
    void addLast(E e);
    E getFirst();
    E getLast();
    E removeFirst();
    E removeLast();
}