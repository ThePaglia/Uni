package a04a.sol1;

/**
 * An interface to model a factory for various kinds of SequencesProvider
 */
public interface SequencesProvidersFactory {
	
	/**
	 * @param <E>
	 * @param e
	 * @return lists of increasing size without a limit, all filled with element e
	 * {}, {e}, {e,e}, {e,e,e}, {e,e,e,e},...
	 */
	<E> SequencesProvider<E> iterative(E e);
	
	/**
	 * @param <E>
	 * @param e1
	 * @param e2
	 * @return lists of increasing size without a limit, all filled with element e1 and e2, alternatively
	 * {}, {}, {e1}, {e2}, {e1,e1}, {e2,e2}, {e1,e1,e1}, {e2,e2,e2},...
	 */
	<E> SequencesProvider<E> alternating(E e1, E e2);
	
	/**
	 * @param <E>
	 * @param e
 	 * @param bound is the number of sequences to be produced overall
	 * @return lists of increasing size, all filled with element e
	 * {}, {e}, {e,e}, {e,e,e}, {e,e,e,e},...  
	 */
	<E> SequencesProvider<E> iterativeBounded(E e, int bound);
	
	/**
	 * @param <E>
	 * @param e1
	 * @param e2
	 * @param bound is the number of sequences to be produced overall
	 * @return lists of increasing size without a limit, all filled with element e1 and e2, alternatively
	 * {}, {}, {e1}, {e2}, {e1,e1}, {e2,e2}, {e1,e1,e1}, {e2,e2,e2},...
	 */
	<E> SequencesProvider<E> alternatingBounded(E e1, E e2, int bound);
}
