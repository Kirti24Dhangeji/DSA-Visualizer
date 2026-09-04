package com.dsa.collections.linear;

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

        
        // confusing constructor parameter sequence -DONE
        public Node( Node<E> prev ,E data,  Node<E> next)
        {
          
            this.prev = prev;
            this.data= data;
            this.next = next;

        }
    }

    public JLinkedList()
    {
        first = last = null;
        this.size = 0;
    }

    // refer addAll()
    public JLinkedList(JCollection<?> c) {


        if(c==null || c.isEmpty())
        {
            throw new IllegalArgumentException();
        }

        Object[] arr = c.toArray();

        for(Object i : arr)
        {
            this.add((E)i);
        }
        
    }

    @Override
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
            Node<E> newNode = new Node<>(last, e, null);
            last.next = newNode;
            last = newNode;

        }
        size++;
        return true;
    }


    @Override
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

    @Override
    public int size()
    {
        return size;
    }


    // change to ifficient - DONE
    @Override
    public boolean isEmpty()
    {
        return size==0;
    }

    // implement boolean contains(Object o) - done
    @Override
    public boolean contains(Object o) throws com.dsa.collections.exceptions.ListEmptyException
    {
        if(size == 0)
            throw new com.dsa.collections.exceptions.ListEmptyException("List is empty");
        else
        {
            Node<E> current = first;
            while(current != last.next)
            {
                if(current.data.equals(o))
                    return true;
                current = current.next;
            }
        }
        return false;

    }

    @Override
    public Object[] toArray() {
        Object []arr = new Object[size];

        Node<E> run = first;
        int i=0;
        while(run != last.next) {
            arr[i] = run.data;
            i++;
            run=run.next;
        }

        return arr;
    }

    @Override
    public boolean remove(Object o)
    {
        if(size == 0)
            return false;
        else
        {
            Node<E> current = first;

            while(current != last.next)
            {
                if(current.data.equals(o))
                {
                    if(current == last)
                    {
                        last = last.prev;
                        current.prev.next = null;
                        size--;
                        return true;
                    }
                    if(current == first)
                    {
                        first = first.next;
                        current.next.prev = null;
                        size--;
                        return true;
                    }
                   current.prev.next = current.next;
                   current.next.prev = current.prev;
                   size--;
                   return true;
                }
                current = current.next;
            }

        }
        
        return false;
    }


    @Override
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


    @Override
    public boolean addAll(JCollection<E> c)
    {

        if(c== null || c.isEmpty()  )
        {
            return false;
        }

        Object[] arr = c.toArray();

        for(Object i : arr)
        {
            this.add((E)i);//TYPECASTING
        }

        return true;
    }


    @Override
    public void clear()
    {
        first=null;
        last=null;
        size=0;
    }

    @Override
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



    @Override
    public void addFirst(E e)
    { 
        // upate size
        Node<E> newNode = new Node<>(null, e, first);
        if(first!=null)
            first.prev = newNode;
        first = newNode;
        if(last == null)
            last = newNode;
        size++;
    }


    @Override
    public void addLast(E e)
    {
        // upate size
        Node<E> newNode = new Node<>(last, e, null);
        if(last!=null)
            last.next = newNode;
        last = newNode;
        if(first == null)
            first = newNode;
        size++;
    }


    @Override
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


    @Override
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


    @Override
    public E removeFirst() throws com.dsa.collections.exceptions.ListEmptyException
    {

        Node<E> removing_node = first; 
        if(size == 0 )
        {
            throw new com.dsa.collections.exceptions.ListEmptyException("List is empty");
        }
        if(size == 1)
        {
            first = null;
            last = null;
        }
        else{
            first = first.next;
            first.prev=null;
        }
        size--;
        return removing_node.data; 

    }


    @Override
    public E removeLast() throws com.dsa.collections.exceptions.ListEmptyException
    {
        Node<E> removing_node = last;
        if(size == 0 )
        {
            throw new com.dsa.collections.exceptions.ListEmptyException("List is empty");
        }
        if(size == 1)
        {
            first = null;
            last = null;
        }
        else
        {
            last = last.prev;
            last.next=null;
        }
        size--;
        return removing_node.data;
    }


    @Override
    public JList<E> copyOf(JCollection<? extends E> c)
    {
        if (c==null || c.isEmpty()) {
            throw new IllegalArgumentException();
        }

        JList<E> list = new JLinkedList<>(c);

        return list;
    }


    @Override
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


    @Override
    public E get(int index) throws com.dsa.collections.exceptions.ListEmptyException
    {
        
        if(size == 0 )
        {
            throw new com.dsa.collections.exceptions.ListEmptyException("List is empty");
        }
        if(index < 0 || index >= size)
        {
           throw new IndexOutOfBoundsException("Invalid index: " + index);
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


    @Override
    public E set(int index, E element) throws com.dsa.collections.exceptions.ListEmptyException
    {
        
        if(size == 0)
        {
            throw new ListEmptyException("List is empty");
        }
        if(index < 0)
        {
            throw new IllegalArgumentException();
        }
        Node<E> current = first;
        int count_for_index = 0;
        while(current!=last.next)
        {
            if(count_for_index == index)
            {
                current.data = element;
                return current.data;
            }
            current = current.next;
            count_for_index++;
        }

        return null;

    }


    @Override
    public void add(int index, E element)
    {
        // update the size - DONE
        if(index == 0 )
        {
            addFirst(element);
            return;
        }
        else if(index == size)
        {
            addLast(element);
            return;
        }
        else if(index < 0 || index > size)
        {
            throw new IllegalArgumentException("Index is out of bounds");
        }
        int count = 0;

        Node<E> current = first;
        while(current!=last.next)
        {
            if(count+1 == index)
            {
                Node<E> newNode = new Node<>(current , element, current.next);
                current.next=newNode;
                newNode.next.prev=newNode;
            }
            count++;
            current=current.next;
        }
        size++;
        
    }

    @Override
    public E remove(int index) throws com.dsa.collections.exceptions.ListEmptyException  
    {
        int count_for_index = 0;
        E removed_data = null;
        if(size==0)
        {
            throw new com.dsa.collections.exceptions.ListEmptyException("List is empty");
        }
        else if(index < 0 || index >= size)
        {
            throw new IllegalArgumentException("Index is out of bounds");
        }
        else
        {
            if(index == 0)
            {
                return removeFirst();
            }
            else if(index == size-1)
            {
                return removeLast();
            }
            Node<E> current = first;
            while(current!=last.next)
            {
                if(count_for_index == index)
                {
                    removed_data = current.data;
                    current.prev.next= current.next;
                    current.next.prev=current.prev;
                    size--;
                    return removed_data;
                }
                current=current.next;
                count_for_index++;
            }
        }
        return null;
    }

    // implement int indexOf(Object o) - DONE
    @Override
    public int indexOf(Object o) throws com.dsa.collections.exceptions.ListEmptyException
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
                if(temp.data.equals(o))
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