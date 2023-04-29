package a05.sol1;

import java.util.*;
import java.util.function.UnaryOperator;

/* 
 * A factory for various power iterators.   
 */


interface PowerIteratorsFactory {
            
    /**
     * @return an iterator over the infinite sequence that begins with 'start', and applies 'successive' each time
     * recall that you compute the next of 'i' with instruction 'successive.apply(i)'
     */
    PowerIterator<Integer> incremental(int start, UnaryOperator<Integer> successive);
    
    /**
     * @param list
     * @return an iterator over the element of the input list
     */
    <X> PowerIterator<X> fromList(List<X> list);
    
    /**
     * @return an iterator over a sequence of random booleans, with length size
     * such a sequence should be produced "by need", since size could be very large
     */
    PowerIterator<Boolean> randomBooleans(int size);
}
