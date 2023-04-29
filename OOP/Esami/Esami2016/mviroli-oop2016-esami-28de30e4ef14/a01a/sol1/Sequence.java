package ex2016.a01a.sol1;

import java.util.*;

/**
 * A sequence of elements of type X, which is not mutable: once created it can't be modified
 */
public interface Sequence<X> {
   
    /**
     * @return the element at (0-based) 'position'
     * Note the result is an optional, which is empty if the position is out-of-range
     */
    Optional<X> getAtPosition(int position);
    
    /**
     * @return the number of elements in this sequence
     */
    int size();
    
    /**
     * @return a copy of this sequence as a java.util.List
     */
    List<X> asList();
    
    /**
     * executes an action on all elements of this sequence
     * this action is represented by invocation of method execute on the executor passed as argument
     * (similar to java.util.function.Consumer)
     */
    void executeOnAllElements(Executor<X> executor);
    

    /**
     * Simply, Sequence.Executor is the contract to be used for method executeOnAllElements 
     */
    interface Executor<X> {
        void execute(X x);
    }
    
}
