package ex2015.a05.sol1;

import java.util.List;

/* 
 * An immutable Cyclic list of elements of type X. 
 * A cyclic list is constructed from a standard list, but then you can access elements at any
 * non-negative position, which is done by moving back at the beginning as you reach the end.
 */
public interface CList<X> {
    
    /**
     * @return the number of elements in the cyclic list not considering the cycle
     */
    int size();
    
    /**
     * @return the element in position pos, assuming any non-negative number is accepted 
     */
    X getElem(int pos);
    
    /**
     * @return whether an element equal to x is contained somewhere in the cyclic list
     */    
    boolean contains(X x);
    
    /**
     * @return a new cyclic list, with element x added at position pos
     */    
    CList<X> add(X x, int pos);
    
    /**
     * @return a new cyclic list, with same elements of this, but where first element is the one at position pos
     */    
    CList<X> shift(int pos);

    
    /**
     * @return the ordered list of elements in the cyclic list
     */    
    List<X> toList();
}
