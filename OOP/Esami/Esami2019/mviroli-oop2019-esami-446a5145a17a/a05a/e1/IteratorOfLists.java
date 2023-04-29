package a05a.e1;

import java.util.List;

/**
 * An interface that provides methods to iterate and produce each time a list of elements of type E
 */
public interface IteratorOfLists<E> {
	
	/**
	 * @return the next list of the iteration
	 */
	List<E> nextList();
	
	/**
	 * @return whether there are other lists to be produced
	 */
	boolean hasOtherLists();
	
	/**
	 * brings the state of iteration at the beginning
	 * NOTE: this method is optional for this exam
	 */
	void reset();

	
}
