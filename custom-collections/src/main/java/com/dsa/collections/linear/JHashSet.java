package com.dsa.collections.linear;

import com.dsa.collections.interfaces.*;
import com.dsa.collections.iterator.JIterator;

public class JHashSet<E> implements JSet<E> {

    private Object [] elements;
    private int size;
    private int capacity;
    private static final double LOAD_FACTOR = 0.75;
    private final Object DELETED = new Object();

    /* HELPER FUNCTIONS */
    private int hash(Object element) {
        return element == null ? 0 : element.hashCode();
    }

    private int indexFor(Object element) {
        return Math.floorMod(hash(element), elements.length);
    }

    private void resize() {
        Object [] temp = elements;
        elements = new Object[capacity = capacity * 2];

        for(Object element : temp) {
            if(element != null && element != DELETED) {
              reinsert(element);
            }
        }

    }

    private void reinsert(Object element) {
        int index = indexFor(element);
        while(elements[index] != null) {
            index = (index + 1) % elements.length;
        }
        elements[index] = element;
    }

    /* CONSTRUCTORS */
    public JHashSet() {
        this.capacity = 10;
        this.elements = new Object[capacity];
        this.size = 0;
    }

    public JHashSet(int capacity) {
        if(capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }

        this.capacity = capacity;
        this.elements = new Object[capacity];
        this.size = 0;
    }

    /* INTERFACE FUNCTIONS */
    @Override
    public boolean add(E element) {
        if(element == null) {
            throw new IllegalArgumentException("Null elements are not allowed");
        }

        if((size + 1.0) /elements.length > LOAD_FACTOR) {
            resize();
        }

        int index = indexFor(element);
        int firstDeletedIndex = -1;
        for(int i = 0 ; i<elements.length; i++) {
            if(elements[index] == null) {
                if(firstDeletedIndex != -1) {
                    elements[firstDeletedIndex] = element;
                } else {
                    elements[index] = element;
                }
                size++;
                return true;
            } else if(elements[index] == DELETED) {
                if(firstDeletedIndex == -1) {
                    firstDeletedIndex = index;
                }
            } else if(elements[index].equals(element)) {
                return false; // Element already exists
            }
            index = Math.floorMod(index+1, elements.length);
        }

        if(firstDeletedIndex != -1) {
            elements[firstDeletedIndex] = element;
            size++;
            return true;
        }

        return false;
    }

    @Override
    public boolean remove(Object element) {
        int index = indexFor(element);
        for(int i = 0; i<elements.length; i++) {
            if(elements[index] == null) {
                return false; // Element not found
            } else if(elements[index] != DELETED && elements[index].equals(element)) {
                elements[index] = DELETED;
                size--;
                return true;
            }
            index = Math.floorMod(index+1, elements.length);
        }
        return false;
    }

    @Override
    public boolean contains(Object element) {
        int index = indexFor(element);
        for(int i = 0; i<elements.length; i++) {
            if(elements[index] == null) {
                return false; // Element not found
            } else if(elements[index] != DELETED && elements[index].equals(element)) {
                return true;
            }
            index = Math.floorMod(index+1, elements.length);
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }
    
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    
    @Override
    public Object[] toArray() {

        Object result[] = new Object[size];
        int i = 0;
        for(Object element : elements) {
            if(element != null && element != DELETED) {
                result[i++] = element;
            }
        }
        return result;
    }

    @Override
    public void clear() {

        elements = new Object[capacity];
        size = 0;

    }
    
    @Override
    public JIterator<E> iterator() {
        return new JIterator<E>() {
            private int currentIndex = 0;
            private int elementsReturned = 0;

            @Override
            public boolean hasNext() {
                return elementsReturned < size;
            }

            @Override
            public E next() {
                while (currentIndex < elements.length) {
                    if (elements[currentIndex] != null && elements[currentIndex] != DELETED) {
                        elementsReturned++;
                        return (E) elements[currentIndex++];
                    }
                    currentIndex++;
                }
                throw new java.util.NoSuchElementException();
            }
        };
    }

}
