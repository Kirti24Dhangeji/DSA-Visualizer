package com.dsa.collections.interfaces;

public interface JCollection<E> extends JIterable<E> {
    int size();
    boolean isEmpty();
    boolean contains(Object o) throws com.dsa.collections.exceptions.ListEmptyException;
    Object[] toArray();
    boolean add(E e);
    boolean remove(Object o);
    boolean containsAll(JCollection<?> c) throws com.dsa.collections.exceptions.ListEmptyException;
    boolean addAll(JCollection<E> c);
    void clear();
}