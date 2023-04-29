package ex2016.a01b.sol1;

import java.util.List;

/* 
 * A builder of immutable lists of elements, where one can add elements, and finally, call
 * build() just once to retrieve the produced immutable list 
 */

public interface ListBuilder<X> {
    
    
    /**
     * @param x, is the element to add
     * @throws IllegalArgumentException is x is not accepted by the builder
     */
    void addElement(X x);
    
    
    /**
     * @returns the immutable List previously indicated
     * @throws IllegalStateException if the List so far is not correct
     */
    List<X> build();
}