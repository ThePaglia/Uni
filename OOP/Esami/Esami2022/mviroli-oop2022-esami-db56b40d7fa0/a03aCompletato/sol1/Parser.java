package a03a.sol1;

import java.util.Iterator;

/**
 * An interface modelling a parser of elements from an iterator. Subsequent calls of the accept method
 * over different iterators should produce no side-effect. 
 */
public interface Parser<X> {

    /**
     * @param iterator
     * @return whether the elements produced by the iterator are correctly parsed.
     * A call to accept consumes elements without storing them (unless necessary). 
     * Iteration proceeds until all elements are correctly parsed and necessarily stops
     * when iteration is over: true result is given only at the end, while false
     * result could also be given in advance.
     * 
     * E.g.: a specific implementation might positively parse only sequences of "1"
     * with at most 10 elements.
     */
    boolean accept(Iterator<X> iterator);
    
}
