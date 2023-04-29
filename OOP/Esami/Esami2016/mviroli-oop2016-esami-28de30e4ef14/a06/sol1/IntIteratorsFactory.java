package pr2016.a06.sol1;

import java.util.*;

/* 
 * A factory for various iterators over integers. Recall that a java.util.Iterator<X> is an interface
 * with methods next() and hasNext(), used to model a (possibly infinite) source of elements of type X.
 * Recall that if hasNext() gives 'false' then next() should throw a NoSuchElementException.
 * Note also that an iterator can actually by easily created also out of a collection or a stream.   
 */

interface IntIteratorsFactory {
     
    /**
     * @return an empty iterator
     */
    Iterator<Integer> empty();
    
    /**
     * @return an iterator indefinitely producing zeros 
     */
    Iterator<Integer> zeros();
    
    /**
     * @return an iterator indefinitely alternating ones and zeros, again and again
     * note its implementation is optional..
     */
    Iterator<Integer> alternateOneAndZeroIndefinitely();
    
    /**
     * @return an iterator from start to end, both included
     */
    Iterator<Integer> fromTo(int start, int end);
        
    /**
     * @return an iterator indefinitely going from start to stop, again and again
     */
    Iterator<Integer> fromToIndefinitely(int start, int stop);
    
    /**
     * @return an iterator out of a list
     */
    Iterator<Integer> fromList(List<Integer> list);
}
