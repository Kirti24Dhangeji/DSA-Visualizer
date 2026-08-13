package com.dsa.collections.interfaces;

public interface JList<E> extends JSequencedCollection<E> {

    JList<E> copyOf(JCollection<? extends E> c);
    void sort() throws com.dsa.collections.exceptions.ListEmptyException;
    E get(int index) throws com.dsa.collections.exceptions.ListEmptyException;
    E set(int index, E element) throws com.dsa.collections.exceptions.ListEmptyException;
    void add(int index, E element);
    E remove(int index) throws com.dsa.collections.exceptions.ListEmptyException;
    int indexOf(Object o) throws com.dsa.collections.exceptions.ListEmptyException;
    
}