package com.dsa.collections.linear;

import com.dsa.collections.exceptions.ListEmptyException;
import com.dsa.collections.interfaces.JCollection;
import com.dsa.collections.interfaces.JList;
import com.dsa.collections.interfaces.JSequencedCollection;
import com.dsa.collections.iterator.JIterator;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class JArrayList<E> implements JList<E> {
    protected Object array[];
    protected int size;
    private int capacity =5; // if size is not mentioned, create array of 5 elements by default

    /* -------------------- Helper methods -------------------- */
    private void resize() {
        if(array.length == capacity) {
            Object new_array[] = new Object[capacity = capacity*2]; // creates larger array with twice of size.
            System.arraycopy(array, 0, new_array, 0, array.length);
            array = new_array; // make the original reference point to the new array
        }
    }

    /* -------------------- Constructors -------------------- */
    /**
     * Constructs an empty array with initial capacity of 5.
     */
    public JArrayList() {
        super();
        array = new Object[capacity]; // array of 5.
        size = 0; // initial size (no of elements in array)
    }

    /**
     * Constructs an empty array of initial capacity given.
     * 
     * @param initialCapacity total length of the array to be created
     * @throws IllegalArgumentException if the given capacity is zero/negative.
     */
    public JArrayList(int initialCapacity) {
        super();
        
        if(initialCapacity <= 0)
            throw new IllegalArgumentException("illegal capacity:" + initialCapacity);

        capacity = initialCapacity;
        array = new Object[capacity];
        size = 0;
    }

    /**
     * Constructs an array with the same elements as the collection passed.
     * If the JCollection is empty, creates a default array.
     * 
     * @param c JCollection object
     */
    public JArrayList(JCollection<? extends E> c) {
        Object[] a = c.toArray();
        if ((size = a.length) != 0) {
            if (c.getClass() == JArrayList.class) {
                array = a;
            } else {
                array = Arrays.copyOf(a, size, Object[].class);
            }
            capacity = size;
        } else {
            // replace with empty array.
            array = new Object[capacity];
        }
    }

    /* -------------------- JIterable interface methods -------------------- */
    /**
     * creates the JIterator instance pointing to the very first element in the JArrayList
     * @return JIterator points to the 0th index of array.
     */
    @SuppressWarnings("unchecked")
    @Override
    public JIterator<E> iterator() {
        return new JIterator<E>() {
            int current =0;
            // int last =-1; // if last not exists

            @Override
            public boolean hasNext() {
                return (current < size);
            }

            @Override
            public E next() {
                if(!hasNext())
                    throw new NoSuchElementException();

                int i=current;

                current = i+1;
                // last = i;
                return (E) array[i];
            }
        };
    }

    /* -------------------- JCollection interface methods -------------------- */

    /**
     * @return size of the array i.e: no of elements
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * @return capacity of the array i.e: no of blocks allocated for the array 
     */
    public int capacity() {
        return capacity;
    }

    /**
     * check if the array is empty
     * 
     * @return true if size is zero i.e: list is empty
     */
    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * search the element in the array
     * 
     * @param o element to be search in the array
     * @return true if element found, false otherwise
     * 
     * @throws ListEmptyException throws if list is empty
     */
    @Override
    public boolean contains(Object o) {
        if(this.isEmpty())
            return false;

        for(int i=0; i<size; i++) {
            if(array[i].equals(o))
                return true;
        }

        return false;
    }

    /**
     * converts the JArrayList to the array
     * 
     * @return Object array containing the JArrayList elements
     */
    @Override
    public Object[] toArray() {
        Object tempArray[] = Arrays.copyOf(array, size);

        return tempArray;
    }

    /**
     * adds the element in the last of the array
     * 
     * @param e element to be added
     * @return true on element add
     * 
     */
    @Override
    public boolean add(E e) {
        if(size == capacity)
            this.resize();

        array[size++] = e;
        return true;
    }

    /**
     * check the passed {@code Object} is present in the JArrayList,
     * if found remove it.
     * 
     * @param o reference to the Object to be removed
     * @return true if value found and removed values
     */
    @Override
    public boolean remove(Object o) {
        if(this.isEmpty())
            return false;

        for(int i=0; i<size; i++) {
            if(array[i].equals(o)) {
                int j = i+1;
                // shifts after elements to the left to fill the gap of one element
                while(j < size) {
                    array[j-1] = array[j];
                    j++;
                }
                size--;
                return true;
            }
        }

        return false;
    }

    /**
     * check if all elements in the passed JCollection object are present in {@code this} JArrayList
     * 
     * @param c reference to the JCollection to be checked
     * @return true if all elements are present, false otherwise
     * 
     * @throws ListEmptyException if the {@code this} is empty
     * @throws IllegalArgumentException if the {@code JCollection} is empty
     */
    @Override
    public boolean containsAll(JCollection<?> c) throws ListEmptyException {
        if(this.isEmpty())
            throw new ListEmptyException("list is empty.!");

        if(c.isEmpty())
            throw new IllegalArgumentException();

        if(!(c instanceof JArrayList))
            throw new IllegalArgumentException("illegal c: " + c);

        /* Find the first occurrance of the Collection int the array */
        int idx = 0;
        JIterator<?> itr = c.iterator();
        while(idx < size && itr.hasNext()) {
            Object element = itr.next();

            if(element.equals(array[idx])) {
                break;
            }
            idx++;
        }

        /* Check if the index is beyond the array size */
        if(idx >= size)
            return false;

        /* Check all elements in the Collection are same in the array */
        while(idx < size && itr.hasNext()) {
            Object element = itr.next();

            if(!(element.equals(array[idx])))
                return false;
        }

        return true;
    }

    /**
     * add all the elements of the {@code c} to {@code this} JArrayList
     * 
     * @param c reference to the JCollection to be added
     * @return true after adding all values
     * 
     * @throws IllegalArgumentException if the {@code JCollection} is empty 
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean addAll(JCollection<E> c) {
        if(c.isEmpty())
            throw new IllegalArgumentException();
            
        /* Iterate over the Collection and add one by one element to current instance */
        JIterator<?> itr = c.iterator();
        while(itr.hasNext()) {
            Object o = itr.next();
            this.add((E) o);
        }

        return true;
    }

    /**
     * set all index of the JArrayList to null value.
     * keeps the capacity unchanged
     */
    @Override
    public void clear() {
        /* reset the array elements */
        for(int i=0; i<size; i++)
            array[i] = null;

        size = 0; // adjust the size to the 0th index
    }


    /* -------------------- JSequencedCollection interface methods -------------------- */
    /**
     * returns the JSequencesCollection having elements in the reverse order of {@code this} object
     * 
     * @return reversed JSequenceCollection of {@code this} object.
     */
    @Override
    public JSequencedCollection<E> reversed() {
        JArrayList<E> jal = new JArrayList<>(this); // make copy of this JArrayList

        /**
         * reverse the copied JArrayList
         */
        for(int i=0, j=jal.size-1; i<j; i++, j--) {
            Object temp = jal.array[i];
            jal.array[i] = jal.array[j];
            jal.array[j] = temp;
        }

        return (JSequencedCollection<E>) jal;
    }

    /**
     * adds element at the 0th index of the JArrayList
     * 
     * @param       e element to be added.
     */
    @Override
    public void addFirst(E e) {
        add(0, e);
    }

    /**
     * adds element at the last index of the JArrayList
     * 
     * @param       e element to be added.
     */
    @Override
    public void addLast(E e) {
        add(e);
    }

    /**
     * returns the first element in the JArrayList
     * 
     * @return      element at the 0th index.
     * @throws      ListEmptyException if the list is empty
     */
    @SuppressWarnings("unchecked")
    @Override
    public E getFirst() throws ListEmptyException {
        if(this.isEmpty())
            throw new ListEmptyException("list is empty.!");
        return (E) array[0];
    }

    /**
     * returns the last element in the JArrayList
     * 
     * @return      element at the last index.
     * @throws      ListEmptyException if the list is empty
     */
    @SuppressWarnings("unchecked")
    @Override
    public E getLast() throws ListEmptyException {
        if(this.isEmpty())
            throw new ListEmptyException("list is empty.!");
        return (E) array[size-1];
    }

    /**
     * removes the first element in the JArrayList
     * 
     * @return      element at the 0th index.
     * @throws      ListEmptyException if the list is empty
     */
    @Override
    public E removeFirst() throws ListEmptyException {
        if(this.isEmpty())
            throw new ListEmptyException("list is empty.!");
        return this.remove(0);
    }

    /**
     * removes the last element in the JArrayList
     * 
     * @return      element at the last index.
     * @throws      ListEmptyException if the list is empty
     */
    @Override
    public E removeLast() throws ListEmptyException {
        if(this.isEmpty())
            throw new ListEmptyException("list is empty.!");
        return this.remove(size-1);
    }

    
    /* -------------------- JList interface methods -------------------- */
    /**
     * copy the JCollection object into a new JList object with the same element in the JCollection
     * 
     * @param c the JCollection object of the datatype (descendant of type E)
     * @return a JList object with the exact element as {@code c}
     */
    @SuppressWarnings("unchecked")
    @Override
    public JList<E> copyOf(JCollection<? extends E> c) {
        if(c.isEmpty())
            return null;

        JList<E> copied = new JArrayList<>();
        
        JIterator<?> itr = c.iterator();
        while(itr.hasNext()) {
            Object ele = itr.next();
            copied.add((E) ele);
        }

        return copied;
    }

    /**
     * sort the JArrayList in the assending order
     * 
     * @throws ListEmptyException if the list is empty
     */
    @Override
    public void sort() throws ListEmptyException {
        if(this.isEmpty())
            throw new ListEmptyException("list is empty.!");

        /**
         * sort only the range of list which is active for user.
         * not removed ones.
         */
        Arrays.sort(array, 0, size);
    }

    /**
     * return the element on the index
     * 
     * @param index the index of element to return
     * @return element on the index
     * 
     * @throws ListEmptyException if the list is empty
     * @throws IllegalArgumentException if the index is not present in the array
     */
    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {        
        if(index >= size || index < 0)
            throw new IllegalArgumentException("can't fetch value from empty array.!");

        return (E) array[index];
    }

    /**
     * update the element on the specified index
     * 
     * @param index the index of the element should be updated
     * @param e the new element to be set on the index
     * @return the updated element
     * 
     * @throws ListEmptyException if the list is empty
     * @throws IllegalArgumentException if the index is not present in the array
     */
    @Override
    public E set(int index, E element) {
        if(index >= size || index < 0)
            throw new IllegalArgumentException("can't set non-existing element in array.!");

        array[index] = element;
        return element;
    }

    /**
     * adds the element at the specified index and shifts the after elements one index right
     * 
     * @param index the index on which the element should be added
     * @param e the element to be added
     */
    @Override
    public void add(int index, E e) {
        final int s;
        Object[] elementData;
        if ((s = size) == (elementData = this.array).length)
            resize();
        System.arraycopy(elementData, index,
                         elementData, index + 1,
                         s - index);
        elementData[index] = e;
        size = s + 1;

        array = elementData; // modify this.array
    }

    /**
     * removes the element at the specified index
     * 
     * @param index index of the element to be removed
     * @return element to be removed
     * 
     * 
     * @throws ListEmptyException if the list is empty
     * @throws IllegalArgumentException if the index is not present in the array
     */
    @Override
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        if(index >= size || index < 0)
            throw new IllegalArgumentException("can't remove non-existing element from array.!");

        /**
         * copy the element to delete in the local reference.
         * copy the after elements to one index left.
         * this overwrites the element on the {@code index}
         */
        E element = (E) array[index];
        System.arraycopy(array, index+1, array, index, size-1-index);
        size--;

        return element;
    }

    /**
     * searches the element o in the JArrayList and returns the index of first occurance of the element
     * 
     * @param o object to be searched
     * @return index of first occurance of the element
     */
    @Override
    public int indexOf(Object o) throws ListEmptyException {
        if(this.isEmpty())
            throw new ListEmptyException("list is empty.!");

        for(int i=0; i<size; i++)
            if(array[i].equals(o))
                return i;

        return -1;
    }
}