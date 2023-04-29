package a04a.sol1;

import java.util.List;

/**
 * An interface that provides methods to iterate and produce each time a sequence of elements of type E
 */
public interface SequencesProvider<E> {
	
	/**
	 * @return the next sequence of the iteration
	 */
	List<E> nextSequence();
	
	/**
	 * @return whether there are other sequences to be produced
	 */
	boolean hasOtherSequences();
	
	/**
	 * brings the state of iteration at the beginning
	 * NOTE: this method is optional for this exam
	 */
	void reset();

	
}
