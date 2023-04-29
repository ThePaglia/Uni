package a01b.e1;

/**
 * A factory to generate empty grids of various size and type
 *
 */
public interface GridFactory {

	/**
	 * @param <E>
	 * @param rows
	 * @param cols
	 * @return an empty grid with the specified size
	 */
	<E> Grid<E> create(int rows, int cols);
}
