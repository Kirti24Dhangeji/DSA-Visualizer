package com.dsa.collections.interfaces;

public interface JSequencedCollection<E> extends JCollection<E> {
    JSequencedCollection<E> reversed();
    void addFirst(E e);
    void addLast(E e);
    E getFirst() throws com.dsa.collections.exceptions.ListEmptyException;
    E getLast() throws com.dsa.collections.exceptions.ListEmptyException;
    E removeFirst() throws com.dsa.collections.exceptions.ListEmptyException;
    E removeLast() throws com.dsa.collections.exceptions.ListEmptyException;
}