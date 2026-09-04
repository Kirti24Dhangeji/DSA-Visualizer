package com.dsa.collections.linear;

public class JLinkedQueue<E> extends JLinkedList<E> {
    // No field as JLinkedList is already providing front and rear and list.

    // Constructors
    public JLinkedQueue()
    {
        super();
    }

    // adds node at the last FIFO 
    public void enqueue(E e)
    {
        super.addLast(e);
    }

    //remove last node following FIFO
    public E dequeue()
    {
        return super.removeFirst();
    }

    // get Front element (peek)
    public E peek()
    {
        return super.getFirst();
    }  
}
