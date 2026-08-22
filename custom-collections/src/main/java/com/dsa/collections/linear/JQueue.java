package com.dsa.collections.linear;
import com.dsa.collections.linear.JLinkedList;

public class JQueue<E> extends JLinkedList<E> {

    private JLinkedList<E> que;

    //Constructors
    public JQueue()
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
    public void dequeue()
    {
        que.removeFirst();
    }

    //conversion of linklist into array
    public Object[] queueIntoArray()
    {
        return que.toArray();
    }

    //reverse of queue
    public void reverseQue()
    {
        que.reversed(); 
    }

    
}
