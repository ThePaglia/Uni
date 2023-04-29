package a01a.sol1;

import java.util.List;

/**
 * An interface modelling a transformer of lists (of X) to lists (of Y),
 * intended to work by splitting the input list into subsequences, and
 * then turn each of them into an element of the output.
 */
public interface SubsequenceCombiner<X,Y>{
		
	/**
	 * @param list
	 * @return a new list obtained transforming the input, without side-effects
	 */
	List<Y> combine(List<X> list);

}
