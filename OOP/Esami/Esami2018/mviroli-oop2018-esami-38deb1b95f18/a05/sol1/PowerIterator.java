package a05.sol1;

import java.util.List;
import java.util.Optional;

/**
 * An interface modelling an iterator of elements of type T,
 * with methods to "recall" what happened in the past.
 * Note that it can also model an infinite stream of elements
 * 
 * @param <T>
 */
public interface PowerIterator<T> {

	/**
	 * @return an empty optional if there are no more elements, otherwise it gives an optional 
	 * filled with the next element
	 */
	Optional<T> next();
	
	/**
	 * @return the list of elements produced so far, without altering the state of iteration
	 */
	List<T> allSoFar();
	
	
	/**
	 * @return a new iterator, to get all elements already produced, in reversed order.
	 * It does not alter the state of this iteration
	 * (this part is optional in this exam)
	 */
	PowerIterator<T> reversed();
}
