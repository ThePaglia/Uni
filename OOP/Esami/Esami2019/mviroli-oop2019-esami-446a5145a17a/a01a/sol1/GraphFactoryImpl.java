package a01a.sol1;

import java.util.*;
import java.util.stream.*;

public class GraphFactoryImpl implements GraphFactory {
	
	/**
	 * A useful internal factory from a stream of edges..
	 */
	private static <X> Graph<X> fromStream(Stream<Pair<X,X>> edgesStream){
		return new Graph<X>() {
			
			private Set<Pair<X,X>> edges = edgesStream.collect(Collectors.toSet());

			@Override
			public Set<X> getNodes() {
				return this.edges.stream()
						         .flatMap(p -> Stream.of(p.getX(),p.getY()))
						         .collect(Collectors.toSet());
			}

			@Override
			public boolean edgePresent(X start, X end) {
				return this.edges.contains(new Pair<>(start,end));
			}

			@Override
			public int getEdgesCount() {
				return this.edges.size();
			}

			@Override
			public Stream<Pair<X, X>> getEdgesStream() {
				return this.edges.stream();
			}
			
		};
	}
	
	/**
	 * A useful internal factory turning a directed graph into a bidirectional one
	 */
	private static <X> Graph<X> bidirectionalFromDirected(Graph<X> graph){
		return new Graph<X>() {
			
			@Override
			public Set<X> getNodes() {
				return graph.getNodes();
			}

			@Override
			public boolean edgePresent(X start, X end) {
				return graph.edgePresent(start, end) || graph.edgePresent(end, start);
			}

			@Override
			public int getEdgesCount() {
				return graph.getEdgesCount()*2;
			}

			@Override
			public Stream<Pair<X, X>> getEdgesStream() {
				return graph.getEdgesStream().flatMap(p -> Stream.of(p,new Pair<>(p.getY(),p.getX()))).distinct();
			}
			
		};
	}
	
	// From here everything is rather straightforward

	@Override
	public <X> Graph<X> createDirectedChain(List<X> nodes) {
		return fromStream(IntStream.range(0, nodes.size()-1).mapToObj(i -> new Pair<>(nodes.get(i),nodes.get(i+1))));
	}

	@Override
	public <X> Graph<X> createBidirectionalChain(List<X> nodes) {
		return bidirectionalFromDirected(createDirectedChain(nodes));
	}

	@Override
	public <X> Graph<X> createDirectedCircle(List<X> nodes) {
		List<X> nodesWithEnd = new LinkedList<>(nodes);
		nodesWithEnd.add(nodes.get(0));
		return this.createDirectedChain(nodesWithEnd);
	}

	@Override
	public <X> Graph<X> createBidirectionalCircle(List<X> nodes) {
		return bidirectionalFromDirected(createDirectedCircle(nodes));
	}

	@Override
	public <X> Graph<X> createDirectedStar(X center, Set<X> nodes) {
		return fromStream(nodes.stream().map(n -> new Pair<>(center,n)));
	}

	@Override
	public <X> Graph<X> createBidirectionalStar(X center, Set<X> nodes) {
		return bidirectionalFromDirected(createDirectedStar(center,nodes));
	}

	@Override
	public <X> Graph<X> createFull(Set<X> nodes) {
		return fromStream(nodes.stream().flatMap(n-> nodes.stream().filter(n2->n2!=n).map(n2 -> new Pair<>(n,n2))));
	}

	@Override
	public <X> Graph<X> combine(Graph<X> g1, Graph<X> g2) {
		return new Graph<X>() {
			
			private int size = (int)this.getEdgesStream().count(); 

			@Override
			public Set<X> getNodes() {
				return this.getEdgesStream().flatMap(p -> Stream.of(p.getX(), p.getY())).collect(Collectors.toSet());
			}

			@Override
			public boolean edgePresent(X start, X end) {
				return g1.edgePresent(start, end) || g2.edgePresent(start, end);
			}

			@Override
			public int getEdgesCount() {
				return this.size;
			}

			@Override
			public Stream<Pair<X, X>> getEdgesStream() {
				return Stream.concat(g1.getEdgesStream(),g2.getEdgesStream()).distinct();
			}
			
		};
	}
	
	
}
