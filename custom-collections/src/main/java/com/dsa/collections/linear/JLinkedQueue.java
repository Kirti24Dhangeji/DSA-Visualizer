package com.dsa.collections.linear;
import com.dsa.collections.interfaces.JSequencedCollection;

public class JLinkedQueue<E> extends JLinkedList<E> {
    private JLinkedList<E> que;

    //Constructors
    public JLinkedQueue()
    {
        que = new JLinkedList<>();
    }

    //is empty function
    public boolean isQueueEmpty()
    {
        return que.isEmpty();
    }

    //size of queue returns size of the queue
    public int sizeOfQueue()
    {
        return que.size();
    }

    //adds node at the last of the linklist 
    public void enqueue(E e)
    {
        que.addLast(e);
    }

    //remove last node following FIFO
    public E dequeue()
    {
        return que.removeFirst();
    }

    //conversion of linklist into array
    public Object[] queueIntoArray()
    {
        return que.toArray();
    }

    //reverse of queue
    public JSequencedCollection<E> reverseQue()
    {
        return que.reversed(); 
    }

    // get Front element
    public E getFirstNode()
    {
        return que.getFirst();
    }

    // get last element
    public E getLastNode()
    {
        return que.getLast();
    }    
}
