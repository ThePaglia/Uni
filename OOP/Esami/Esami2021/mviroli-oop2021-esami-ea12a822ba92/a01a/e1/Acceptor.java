package a01a.e1;

import java.util.Optional;

/**
 * An interface modelling a step-by-step acceptor of a sequence of elements
 * of type E. Acceptation of a sequence may be rejected, either if an element 
 * is considered wrong, or when the whole sequence has been completed and it is not
 * to be accepted.
 * If the sequence is accepted, a result is provided.
 * When the sequence is accepted, or if at some point is not accepted, the object 
 * should then no longer be used. 
 * @param <E> is the type of accepted elements
 * @param <R> is the type of the result
 */
public interface Acceptor<E,R> {
	
	/**
	 * @param e is the element to be accepted 
	 * @return true if this element has been accepted
	 */
	boolean accept(E e);
	
	/**
	 * Used to indicate that the input sequence is over
	 * @return the result as an Optional, present only if the sequence is overall accepted
	 */
	Optional<R> end();
}
