package com.dsa.collections.interfaces;

public interface JList<E> extends JSequencedCollection<E> {
    JList<E> copyOf(JCollection<? extends E> c);
    void sort();
    E get(int index);
    E set(int index, E element);
    void add(int index, E element);
    E remove(int index);
    int indexOf(Object o);
}