package a03c.sol1;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TableFactoryImpl implements TableFactory {
	
	static class TableImpl<R,C,V> implements Table<R,C,V>{
		private final Map<Pair<R, C>, V> map;
		private final Set<R> rows;
		private final Set<C> columns;
		

		public TableImpl(Map<Pair<R, C>, V> map) {
			this.map = new HashMap<>(map);
			this.rows = this.map.keySet().stream().map(Pair::getX).collect(Collectors.toSet());
			this.columns = this.map.keySet().stream().map(Pair::getY).collect(Collectors.toSet());
		}

		@Override
		public Map<C, Map<R, V>> asColumnMap() {
			return this.columns.stream().collect(
					Collectors.toMap(c->c, 
							c-> this.rows.
							stream().
							map(r->new Pair<>(r,this.getValue(r,c))).
							filter(p->p.getY().isPresent()).
							collect(Collectors.toMap(p->p.getX(), p->p.getY().get()))));
		}

		@Override
		public Map<R, Map<C, V>> asRowMap() {
			return this.rows.stream().collect(
					Collectors.toMap(r->r, 
							r-> this.columns.
							stream().
							map(c->new Pair<>(c,this.getValue(r,c))).
							filter(p->p.getY().isPresent()).
							collect(Collectors.toMap(p->p.getX(), p->p.getY().get()))));
		}

		@Override
		public Optional<V> getValue(R row, C column) {
			return Optional.ofNullable(this.map.get(new Pair<>(row,column)));
		}

		@Override
		public void putValue(R row, C column, V value) {
			this.map.put(new Pair<>(row,column), value);
		}

		@Override
		public Set<R> rows() {
			return this.rows;
		}

		@Override
		public Set<C> columns() {
			return this.columns;
		}
		
		
	}

	@Override
	public <R, C, V> Table<R, C, V> fromMap(Map<Pair<R, C>, V> map) {
		return new TableImpl<>(map);
	}

	@Override
	public <R, C, V> Table<R, C, V> fromFunction(Set<R> rows, Set<C> columns, BiFunction<R, C, V> valueFunction) {
		return new TableImpl<>(
				rows.stream().flatMap(r -> columns.stream().
						map(c -> new Pair<>(r,c))).
						collect(Collectors.toMap(p->p, p->valueFunction.apply(p.getX(),p.getY()))));
	}

	@Override
	public <G> Table<G, G, Boolean> graph(Set<Pair<G, G>> edges) {
		final Set<G> nodes = edges.stream().flatMap(p -> Stream.of(p.getX(),p.getY())).collect(Collectors.toSet());
		return this.fromFunction(nodes,nodes,(n,m)->edges.contains(new Pair<>(n,m)));
	}

	@Override
	public <V> Table<Integer, Integer, V> squareMatrix(V[][] values) {
		final Set<Integer> rows = Stream.iterate(0,x->x+1).limit(values.length).collect(Collectors.toSet());
		return this.fromFunction(rows,rows,(n,m)->values[n][m]);
	}

}
