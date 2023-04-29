package a03c.e1;

import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * An interface modelling a factory for various kinds of tables
 *
 */
/**
 * @author mirko
 *
 */
public interface TableFactory {
	
	/**
	 * @param <R>
	 * @param <C>
	 * @param <V>
	 * @param map
	 * @return a table from a map associating each row-column pair to a value 
	 */
	<R,C,V> Table<R,C,V> fromMap(Map<Pair<R,C>,V> map);
	
	/**
	 * @param <R>
	 * @param <C>
	 * @param <V>
	 * @param rows
	 * @param columns
	 * @param valueFunction
	 * @return a table with given rows and columns, and with a function mapping rows and columns to values
	 */
	<R,C,V> Table<R,C,V> fromFunction(Set<R> rows, Set<C> columns, BiFunction<R,C,V> valueFunction);
	
	/**
	 * @param <G>, the type of nodes of the graph, used both for row and columns
	 * @param edges, a set of pair of edges (arcs from node to node)
	 * @return a table representing a graph, which is a particular kind of table where rows and columns are the nodes,
	 * and we have boolean true in all the row-column cells where there's an edge from the row's to the column's node  
	 */
	<G> Table<G,G,Boolean> graph(Set<Pair<G,G>> edges);
	
	/**
	 * @param <V>, the type of elements in the matrix, used for values in the table 
	 * @param values, a square matrix
	 * @return a table representing a square matrix, which is a particular kind of table where rows and columns are integer indexes,
	 * and values are of the element type of the matrix
	 * THIS IS OPTIONAL IN THIS EXAM  
	 */
	<V> Table<Integer,Integer,V> squareMatrix(V[][] values);

}
