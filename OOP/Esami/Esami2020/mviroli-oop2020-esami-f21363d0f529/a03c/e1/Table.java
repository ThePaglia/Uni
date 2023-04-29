package a03c.e1;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * An interface modelling a table, with rows, columns and cells: essentially a set of triples
 * of a row, a column and a value in the cell.
 *
 * @param <R> the type of rows
 * @param <C> the type of columns
 * @param <V> the type of values in cells
 */
public interface Table<R,C,V> {
	
	/**
	 * @return the set of all rows
	 */
	Set<R> rows();
	
	/**
	 * @return the set of all columns
	 */
	Set<C> columns();

	/**
	 * @return a map associating columns to row-value maps
	 * THIS IS OPTIONAL IN THIS EXAM!
	 */
	Map<C,Map<R,V>> asColumnMap();
	
	/**
	 * @return a map associating rows to column-value maps
	 */
	Map<R,Map<C,V>> asRowMap();
	
	/**
	 * @param row
	 * @param column
	 * @return the value in that specific row and column, if it exists
	 */
	Optional<V> getValue(R row, C column);
	
	/**
	 * @param row
	 * @param column
	 * @param value
	 * 
	 * puts the value in that row-column cell
	 *  
	 */
	void putValue(R row, C column, V value);
}
