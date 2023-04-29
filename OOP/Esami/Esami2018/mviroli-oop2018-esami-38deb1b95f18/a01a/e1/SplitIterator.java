package a01a.e1;

import java.util.Optional;

/**
 * An interface to model an iterator, with the ability of splitting iteration by method split().
 * Split on iterator A creates a new iterator B: the first part of the iteration is delegated to B, while
 * the second part is carried on by A.
 *
 * @param <X> is the type of elements produced by the iterator
 */
public interface SplitIterator<X> {
	
	/**
	 * @return an optional containing the next element produced by the iterator; if this is empty it means the iteration is concluded
	 */
	Optional<X> next();
	
	/**
	 * Creates a new SpliIterator of the same kind of the receiver, and delegates to it the first part of the iteration. 
	 * The receiver iterates on the second part instead.
	 * 
	 * @return the created SpliIterator
	 * @throw an UnsupportedOperationException if the specific implementation does not support splitting
	 */
	SplitIterator<X> split();
}
