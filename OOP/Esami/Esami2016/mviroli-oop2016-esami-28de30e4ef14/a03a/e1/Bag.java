package ex2016.a03a.e1;

import java.util.*;

/**
 * An immutable bag of elements of type X: once created it can't be modified
 * A bag is a multiset, namely, a set where you can have many copies of an element 
 */
public interface Bag<X> {
       
    /**
     * @param x
     * @return the number of elements x in this bag (0 if none exists)
     */
    int numberOfCopies(X x);
    
    /**
     * @return the number of elements in this bag, considering also repetitions
     */
    int size();
    
    /**
     * @return a copy of this bag as a java.util.List, where ordering does not matter
     */
    List<X> toList();
        
}
