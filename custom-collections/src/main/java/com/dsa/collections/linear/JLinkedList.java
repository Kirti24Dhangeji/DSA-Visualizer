package com.dsa.collections.linear;
import java.io.IOException;
import java.util.Arrays;

import com.dsa.collections.exceptions.ListEmptyException;
import com.dsa.collections.interfaces.JCollection;
import com.dsa.collections.interfaces.JList;
import com.dsa.collections.interfaces.JSequencedCollection;
import com.dsa.collections.iterator.JIterator;


public class JLinkedList<E> implements JList<E> {

    private int size;
    private Node<E> first;
    private Node<E> last;

    private static class Node<E>{

        E data;
        Node<E> next ;
        Node<E> prev;

        
        // confusing constructor parameter sequence
        public Node( Node<E> next ,E data,  Node<E> prev)
        {
            this.data= data;
            this.next = next;
            this.prev = prev;

        }
    }

    public JLinkedList()
    {
        first = last = null;
        this.size = 0;
    }

    // refer addAll()
    public JLinkedList(JCollection<?> c) {
        
    }

    // wrong node linking
    public  boolean add(E e)
    {
        if(first == null)
        {
            final Node<E> newNode = new Node<>(null, e, null);
            first = newNode;
            last = newNode;

        }
        else
        {
            // logically incorrect.
            Node<E> l = last ;
            final Node<E> newNode = new Node<>(l, e, null);
            l = newNode;
            l.next = last ;
        }
        size++;

        return true;
    }



    public JIterator<E> iterator()
    {
        return new JIterator<E>() {
            private Node<E> current = first;

            @Override
            public boolean hasNext() {
                return current != last.next;
            }

            @Override
            public E next() {
                E currentData = current.data;
                current = current.next;
                return currentData;
            }
        };
    }

    public int size()
    {
        return size;
    }


    // change to ifficient
    public boolean isEmpty()
    {
        // if(size == 0)
        //     return true;
        // else
        //     return false;

        return size==0;
    }

    // implement boolean contains(Object o)
    public boolean contains(Node<E> n) throws com.dsa.collections.exceptions.ListEmptyException
    {
        if(size == 0)
            throw new com.dsa.collections.exceptions.ListEmptyException("List is empty");
        else
        {
            Node<E> current = first;
            while(current != last.next)
            {
                if(current.data.equals(n.data))
                    return true;
                current = current.next;
            }
        }
        return false;

    }

    public Object[] toArray() {
        Object []arr = new Object[size];

        Node<E> run = first;
        int i=0;
        while(run != last.next) {
            arr[i] = run.data;
            i++;
        }

        return arr;
    }

    // implement boolean remove(Object o)
    public boolean remove(Node<E> n)
    {
        // incorrect usage of flag. (try without flag.)
        boolean flag = true;
        if(size == 0)
            return false;
        else
        {
            Node<E> current = first;

            while(current != last.next)
            {
                if(current.data.equals(n.data))
                {
                    // handle edge cases.
                   current.prev.next = current.next;  //connecting the nodes
                   current.next.prev = current.prev;
                   flag = false;
                   break;
                }
            }

        }

        if(flag)
            return false;
        else
            return true;
    }


    public boolean containsAll(JCollection<?> c) throws com.dsa.collections.exceptions.ListEmptyException  //to check wether the list contains all the elements 
    {
        if(this.isEmpty())
            throw new ListEmptyException("List is empty.!");

        if(c.isEmpty())
            throw new IllegalArgumentException();

        Object collection_first_obj = c.iterator().next();

        Node<E> current = first;
        while (current != last.next) {

            if (current.data.equals(collection_first_obj)) {
                break;
            }

            current = current.next;
        }

        if (current == last.next)
            return false;

        JIterator<?> itr = c.iterator();
        while (current != last.next && itr.hasNext()) {

            Object obj = itr.next();
            
            if (!current.data.equals(obj)){
                return false;
            }

            current = current.next;
        }

        return true;
    }


    public boolean addAll(JCollection<E> c)
    {

        if(c== null || c.isEmpty()  )
        {
            return false;
        }

        Object[] arr = c.toArray();

        for(Object i : arr)
        {
            this.add((E)i);
        }

        return true;
    }


    public void clear()
    {
        first=null;
        last=null;
        size=0;
    }

    public JSequencedCollection<E> reversed()
    {
        if(size == 0)
        {
            return null;
        }

        Node<E> temp = last;

        JSequencedCollection<E> ref = new JLinkedList<>();
        
        while(temp!= first.prev)
        {
            ref.add(temp.data);
            temp= temp.prev;
        }
        return ref;
    }



    public void addFirst(E e)
    { 
        // upate size
        Node<E> newNode = new Node<>(null, e, null);
        if(size==0)
        {
            // handle last reference (pointer)
            first = newNode;
        }
        else{
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
        }
    }


    public void addLast(E e)
    {
        // upate size
        Node<E> newNode = new Node<>(null, e, null);
        if(size==0)
        {
            first = newNode;
            last = newNode;
        }
        else
        {
            last.next= newNode;
            newNode.prev = last;
            last = newNode;
        }
    }


    public E getFirst() throws com.dsa.collections.exceptions.ListEmptyException
    {
        if(size == 0 )
        {
            throw new com.dsa.collections.exceptions.ListEmptyException("List is empty");
        }
        else
        {
            return first.data;
        }
    }


    public E getLast() throws com.dsa.collections.exceptions.ListEmptyException
    {
        if(size == 0 )
        {
            throw new com.dsa.collections.exceptions.ListEmptyException("List is empty");
        }
        else
        {
            return last.data;
        }
    }


    public E removeFirst() throws com.dsa.collections.exceptions.ListEmptyException
    {
        if(size == 0 )
        {
            throw new com.dsa.collections.exceptions.ListEmptyException("List is empty");
        }
        else{
            // handle prev of 2nd node.
            Node<E> newNode = first;
            first = first.next;
            newNode=null;

        }

        // wrong return data
        return first.data;

    }


    public E removeLast() throws com.dsa.collections.exceptions.ListEmptyException
    {

        if(size == 0 )
        {
            throw new com.dsa.collections.exceptions.ListEmptyException("List is empty");
        }
        else
        {
            // handle prev of 2nd node.
            Node<E> newNode = last;
            last = last.prev;
            newNode=null;
        }

        // wrong return data
        return last.data;
    }


    public JList<E> copyOf(JCollection<? extends E> c)
    {
        if (c==null || c.isEmpty()) {
            throw new IllegalArgumentException();
        }

        JList<E> list = new JLinkedList<>(c);

        return list;
    }


    public void sort() throws com.dsa.collections.exceptions.ListEmptyException
    {
        if(size == 0 )
        {
            throw new com.dsa.collections.exceptions.ListEmptyException("List is empty");
        }
        else
        {
            Object[] arr = toArray();
            Arrays.sort(arr);
            clear();
            for(Object o : arr)
            {
                add((E)o);
            }

        }
    }


    public E get(int index) throws com.dsa.collections.exceptions.ListEmptyException
    {
        
        if(size == 0 )
        {
            throw new com.dsa.collections.exceptions.ListEmptyException("List is empty");
        }
        else
        {
            int temp = 0;
            Node<E> newNode = first;

            while(newNode!=last.next)
            {
                if(index == temp)
                {
                    return newNode.data;
                }
                temp++;
                newNode = newNode.next;
            }

        }
        
        return null;
    }


    /**
     * update the existing data present on the index.
     * 
     * check is index present in the list.
     * if present:
     *      update the node.data = element
     *      return updated node.data
     * else:
     *      return null / throw IllegalArgumentException()
     */
    public E set(int index, E element) throws com.dsa.collections.exceptions.ListEmptyException
    {

    }


    public void add(int index, E element)
    {
        // update the size
        if(index ==0)
        {
            addFirst(element);
        }
        int count = 0;

        Node<E> current = first;
        while(current!=last.next)
        {

            if(count+1 == index)
            {
                // handle edge cases.
                Node<E> newNode = new Node<>(null, element, null);
                newNode.prev=current;
                newNode.next=current.next;
                current.next=newNode;
                newNode.next.prev=newNode;
            }
            count++;
            current=current.next;
        }
        
    }


    public E remove(int index) throws com.dsa.collections.exceptions.ListEmptyException  //what to return ??
    {
        int temp=0;
        if(size==0)
        {
            throw new com.dsa.collections.exceptions.ListEmptyException("List is empty");
        }
        else if(index < 0)
        {
            throw new IllegalArgumentException("Index cannot be negative");
        }
        else
        {
            Node<E> current = first;
            while(current!=last.next)
            {
                // handle infinite loop condition.
                // handle temp increment.
                // misssing break statement.
                // return appropriate value.
                if(temp == index)
                {
                    current.prev.next= current.next;
                    current.next.prev=current.prev;
                    current.next=null;
                    current.prev = null;
                    current = null;
                }
            }


        }
        return null;
    }

    // implement int indexOf(Object o)
    public int indexOf(Node<E> e) throws com.dsa.collections.exceptions.ListEmptyException
    {
        if(size == 0)
        {
            throw new com.dsa.collections.exceptions.ListEmptyException("List is empty");
        }
        else
        {
            Node<E> temp = first;
            int index = 0;
            while(temp!=last.next)
            {
                if(temp.data.equals(e.data))
                {
                    return index;
                }
                index++;
                temp=temp.next;
            }


        }
        return -1;
    }




}