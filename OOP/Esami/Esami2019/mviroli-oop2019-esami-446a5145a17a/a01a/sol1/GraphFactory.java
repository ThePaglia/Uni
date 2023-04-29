package a01a.sol1;

import java.util.List;
import java.util.Set;

public interface GraphFactory {

	/**
	 * @param <X>
	 * @param nodes, say x1,..,xn, all distinct
	 * @return a graph with edges x1->x2, x2->x3, .., x(n-1)->xn
	 */
	<X> Graph<X> createDirectedChain(List<X> nodes);
	
	/**
	 * @param <X>
	 * @param nodes, say x1,..,xn, all distinct
	 * @return a graph with edges x1<->x2, x2<->x3, .., x(n-1)<->xn
	 */
	<X> Graph<X> createBidirectionalChain(List<X> nodes);
	
	/**
	 * @param <X>
	 * @param nodes, say x1,..,xn, all distinct
	 * @return a graph with edges x1->x2, x2->x3, .., x(n-1)->xn, xn->x1
	 */
	<X> Graph<X> createDirectedCircle(List<X> nodes);
	
	/**
	 * @param <X>
	 * @param nodes, say x1,..,xn, all distinct
	 * @return a graph with edges x1<->x2, x2<->x3, .., x(n-1)<->xn, xn<->x1
	 */
	<X> Graph<X> createBidirectionalCircle(List<X> nodes);
	
	/**
	 * @param <X>
	 * @param nodes, say x1,..,xn
	 * @param center, say c
	 * @return a graph with edges c->x1, c->x2, .., x->xn
	 */
	<X> Graph<X> createDirectedStar(X center, Set<X> nodes);
	
	/**
	 * @param <X>
	 * @param nodes, say x1,..,xn
	 * @param center, say c
	 * @return a graph with edges c<->x1, c<->x2, .., x<->xn
	 */
	<X> Graph<X> createBidirectionalStar(X center, Set<X> nodes);
	
	/**
	 * @param <X>
	 * @param nodes, say x1,..,xn
	 * @return a graph with an edge xi -> xj for each i!=j
	 */
	<X> Graph<X> createFull(Set<X> nodes);
	
	/**
	 * @param <X>
	 * @param g1
	 * @param g2
	 * @return a graph obtained by set-union of nodes and edges of g1 and g2 
	 * IT IS IN THE OPTIONAL PART OF THE EXAM!
	 */
	<X> Graph<X> combine(Graph<X> g1, Graph<X> g2);
	
	
}
