package a02a.sol1;

import java.util.Iterator;

/**
 * An interface modelling a scanner, which takes the input (to be consumed step by step) and extracts 
 * some information, typically holding a state during traversal -- the details depend on the concrete implementation. 
 * 
 * @param <X>, the type of elements in the input sequence
 * @param <Y>, the type of the result
 */
public interface Scanner<X,Y>{
	
	Y scan(Iterator<X> input);
}
