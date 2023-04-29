package ex2016.a03a.e1;

import java.util.*;
import java.util.function.*;

/* 
 * A factory for Bag, where one can create bags in many different ways.  
 */

interface BagFactory {
    
    /**
     * @return an empty bag
     */
    <X> Bag<X> empty();
    
    /**
     * @param set
     * @return a bag having one copy of each element in set
     */
    <X> Bag<X> fromSet(Set<X> set);
    
    /**
     * @param list
     * @return a bag having one element for each in the list, repetitions included
     */
    <X> Bag<X> fromList(List<X> list);
    
    /**
     * @param supplier, a function producing elements of type X (see java.util.Function.Supplier)
     * @param nElements, how many elements will be taken from supplier
     * @param copies, a function giving how many repetition of an element have to be considered (see java.util.Function.ToIntFunction)
     * @return a bag with nElements distinct values of type X, taken from supplier, with number of repetitions obtained from copies
     */
    <X> Bag<X> bySupplier(Supplier<X> supplier, int nElements, ToIntFunction<X> copies);
    
    /**
     * @param first, the first element in the bag
     * @param next, a function returning the next element to add in the bag (see java.util.Function.UnaryOperator)
     * @param nElements, how many elements will be considered
     * @param copies, a function giving how many repetition on an element has to be considered (see java.util.Function.ToIntFunction)
     * @return a bag with nElements distinct values of type X, taken from first/next, with number of repetitions obtained from copies
     */
    <X> Bag<X> byIteration(X first, UnaryOperator<X> next, int nElements, ToIntFunction<X> copies);
    
}
