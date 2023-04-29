package ex2016.a02a.sol1;

import java.util.*;


/**
 * A multiple (FIFO) queue, e.g., to manage people paying at the supermarket
 *
 * @param <T>, the type of requester, also called 'elements'
 * @param <Q>, the type of queues (e.g., they name, or ID, or position)
 */
/**
 * @author mirko
 *
 * @param <T>
 * @param <Q>
 */
public interface MultiQueue<T,Q> {
    
    /**
     * @return the set of queues currently available (or working)
     */
    Set<Q> availableQueues();
    
    /**
     * @param creates a new queue
     * @throws IllegalArgumentException if queue is already available
     */
    void openNewQueue(Q queue);
    
    /**
     * @param queue, is the queue we check
     * @return whether queue is empty
     * @throws IllegalArgumentException if queue is not available
     */
    boolean isQueueEmpty(Q queue);
    
    /**
     * @param elem, is the element to add
     * @param queue, is the queue where the element is to be added
     * @throws IllegalArgumentException if queue is not available
     */
    void enqueue(T elem, Q queue);
    
    /**
     * @param queue, is the queue where to take the next element
     * @return the next element in queue, or empty if there's none
     * @throws IllegalArgumentException if queue is not available
     */
    Optional<T> dequeue(Q queue);
    
    /**
     * dequeues one element from any queue, where possible
     * @return a map of dequeued elements 
     */
    Map<Q,Optional<T>> dequeueOneFromAllQueues();
    
    /**
     * @return the set of all enqueued elements
     */
    Set<T> allEnqueuedElements();
    
    /**
     * Empties a queue
     * @param queue, the queue to be emptied
     * @return the list of elements enqueued
     * @throws IllegalArgumentException if queue is not available
     */
    List<T> dequeueAllFromQueue(Q queue);
    
    /**
     * Empties a queue and move all of its elements in some other available queue
     * @param , the queue to be emptied
     * @throws IllegalArgumentException if queue is not available
     * @throws IllegalStateException if there's no alternative queue for moving elements to
     */
    void closeQueueAndReallocate(Q queue);       
}
