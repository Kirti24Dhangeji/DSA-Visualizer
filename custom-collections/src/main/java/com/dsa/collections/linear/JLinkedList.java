package com.dsa.collections.linear;
import com.dsa.collections.exceptions.ListEmptyException;
import com.dsa.collections.interfaces.JCollection;
import com.dsa.collections.interfaces.JList;
import com.dsa.collections.interfaces.JSequencedCollection;
import com.dsa.collections.iterator.JIterator;


public class JLinkedList<E> implements JList<E> {

    private int size;
    private Node<E> first;
    private Node<E> last;


    public JLinkedList()
    {
        this.size = 0;
    }

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
            final Node<E> l = last ;
            final Node<E> newNode = new Node<>(l, e, null);
            l = newNode;
            l.next = last ;
        }
        size++;

        return true;
    }

    private static class Node<E>{

        E data;
        Node<E> next ;
        Node<E> prev;

        

        public Node( Node<E> next ,E data,  Node<E> prev)
        {
            this.data= data;
            this.next = next;
            this.prev = prev;

        }
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



    public boolean isEmpty()
    {
        if(size == 0)
            return true;
        else
            return false;
    }


    public boolean contains(Node<E> n) throws com.dsa.collections.exceptions.ListEmptyException
    {
        if(size == 0)
            throw new com.dsa.collections.exceptions.ListEmptyException("List is empty");
        else
        {
            Node<E> current = first;
            while(current != null)
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

    public boolean remove(Node<E> n)
    {

        boolean flag = true;
        if(size == 0)
            return false;
        else
        {
            Node<E> current = first;

            while(current != null)
            {
                if(current.data.equals(n.data))
                {
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


    public boolean containsAll(JLinkedList<E> temp) throws com.dsa.collections.exceptions.ListEmptyException  //to check wether the list contains all the elements 
    {
           
    }
    public boolean addAll(JCollection<E> c);


    public void clear()
    {
        first=null;
        last=null;
        size=0;
    }

    public JSequencedCollection<E> reversed();


    public void addFirst(E e)
    { 
        
        Node<E> newNode = new Node<>(null, e, null);
        if(size==0)
        {
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

            Node<E> newNode = first;
            first = first.next;
            newNode=null;

        }

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
            Node<E> newNode = last;
            last = last.prev;
            newNode=null;
        }
        return last.data;
    }


    public JList<E> copyOf(JCollection<? extends E> c)
    {

    }
    public void sort() throws com.dsa.collections.exceptions.ListEmptyException
    {
        if(size == 0 )
        {
            throw new com.dsa.collections.exceptions.ListEmptyException("List is empty");
        }
        else
        {
            
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

            while(newNode!=null)
            {
                if(index == temp)
                {
                    return newNode.data;
                }
                temp++;
                newNode= newNode.next;
            }

        }
        
        return null;
    }


    public E set(int index, E element) throws com.dsa.collections.exceptions.ListEmptyException
    {
        if(size == 0 && index > 0  )
        {
            throw new com.dsa.collections.exceptions.ListEmptyException("List is empty");
        }
        else if(index == 0)
        {
            addFirst(element);    //calling addfirst to add node
        }
        else
        {
            Node<E> current = first;
            int temp = -1;
            while(current!=null)
            {
                if(temp+1 == index)
                {
                    Node<E> newNode = new Node<>(null, element, null);
                    newNode.prev=current.prev;
                    newNode.next=current;
                    newNode.prev.next=newNode;
                    current.prev=newNode;

                }
                temp++;
                current=current.next;
            }
        }
        return element;

    }


    public void add(int index, E element)
    {

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
            //create new exception 
        }
        else
        {
            Node<E> current = first;
            while(current!=null)
            {
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
            while(temp!=null)
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