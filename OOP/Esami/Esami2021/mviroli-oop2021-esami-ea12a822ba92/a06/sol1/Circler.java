package a06.sol1;

import java.util.List;

/**
 * An interface that models an infinite iteration of elements
 * taken from a source list of elements of type T 
 *
 * @param <T>
 */
public interface Circler<T> {
	
	/**
	 * it sets the source: each time this is called, the iteration will be reset
	 * 
	 * @param elements
	 */
	void setSource(List<T> elements);
	
	/**
	 * @return the next element of the iteration
	 */
	T produceOne();
	
	/**
	 * @param n
	 * @return a list of the n next elements of the iteration
	 */
	List<T> produceMany(int n);

}
