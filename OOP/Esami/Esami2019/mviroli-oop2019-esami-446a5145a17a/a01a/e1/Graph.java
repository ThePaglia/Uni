package a01a.e1;

import java.util.Set;
import java.util.stream.Stream;

/**
 * An interface for modelling a directed graph
 * 
 * @param X, the type of labels, namely, nodes of the graph
 */
public interface Graph<X> {
	
	/**
	 * @return the set of nodes
	 */
	Set<X> getNodes();
	
	/**
	 * @param start
	 * @param end
	 * @return whether there's a direct edge from start to stop
	 */
	boolean edgePresent(X start, X end);
	
	/**
	 * @return the number of edges
	 */
	int getEdgesCount();
	
	/**
	 * @return a stream over all edges, modelled as pairs. 
	 * IT IS IN THE OPTIONAL PART OF THE EXAM!
	 */
	Stream<Pair<X,X>> getEdgesStream();

}
