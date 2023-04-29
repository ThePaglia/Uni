package a02b.sol1;

import java.util.List;
import java.util.function.Consumer;

/**
 * An interface modelling helpers (factories and transformers) of various kinds of Cursor
 */
public interface CursorHelpers {
	
	/**
	 * @param list, assumed not to be empty
	 * @return a cursor over all the elements of list considered one after the other
	 */
	<X> Cursor<X> fromNonEmptyList(List<X> list);// mandatory

	/**
	 * @return a cursor over 0,1,2,3,4,... assuming we never reach MAXINT
	 */
	Cursor<Integer> naturals();

	/**
	 * @param input, assumed not to be empty, by definition
	 * @param max, assumed to be positive
	 * @return a cursor considering the first max-elements given by input; if
	 * max is greater then input's size, then the output is the same as the input
	 */
	<X> Cursor<X> take(Cursor<X> input, int max);

	/**
	 * @param input, assumed not to be empty, by definition
	 * @param consumer
	 * Applies the consumer (that is, its method accept) to all elements of the cursor.
	 * This application typically produces some side-effect.
	 */
	<X> void forEach(Cursor<X> input, Consumer<X> consumer);

	/**
	 * @param input, assumed not to be empty, by definition
	 * @param max, assumed to be positive
	 * @return extract elements from cursor (no more than max), and creates a list
	 */
	<X> List<X> toList(Cursor<X> input, int max);

}
