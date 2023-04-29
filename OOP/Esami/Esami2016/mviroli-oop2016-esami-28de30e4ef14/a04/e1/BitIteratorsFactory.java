package ex2016.a04.e1;

import java.util.*;

/* 
 * A factory for various iterators over bits. Recall that a java.util.Iterator<X> is an interface
 * with methods next() and hasNext(), used to model a (possibly infinite) source of elements of type X.
 * Recall that if hasNext() gives 'false' then next() should throw a NoSuchElementException.   
 */

interface BitIteratorsFactory {
    
    enum Bit {
        ZERO, ONE
    }
    
    /**
     * @return an empty iterator
     */
    Iterator<Bit> empty();
    
    /**
     * @return an iterator indefinitely producing ZEROs 
     */
    Iterator<Bit> zeros();
    
    /**
     * @return an iterator indefinitely producing ONEs 
     */
    Iterator<Bit> ones();
    
    /**
     * @return an iterator producing 8 bits out of an integer in [0,255], starting from the least significative (LSB)
     * for instance: 127 produces ONE,ONE,ONE,ONE,ONE,ONE,ONE,ZERO
     * suggestion: 
     * - b % 2 gives the first bit, then do: b = b/2
     * - b % 2 now gives the second bit, then do: b = b/2
     * - b % 2 now gives the third bit,.. and so on
     */
    Iterator<Bit> fromByteStartingWithLSB(int b);
        
    /**
     * @return an iterator of bits out of a list of bits
     */
    Iterator<Bit> fromBitList(List<Bit> list);
        
    /**
     * @return an iterator of bits out of a list of booleans: false becomes ZERO, true becomes ONE
     * note this is optional..
     */
    Iterator<Bit> fromBooleanList(List<Boolean> list);
}
