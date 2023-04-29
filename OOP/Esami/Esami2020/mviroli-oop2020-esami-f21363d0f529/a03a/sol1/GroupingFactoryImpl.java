package a03a.sol1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class GroupingFactoryImpl implements GroupingFactory {

	@Override
	public <G, V> Grouping<G, V> fromPairs(Iterable<Pair<G,V>> values) {
		Map<G,Set<V>> map = new HashMap<>();
		values.forEach(p -> map.merge(p.getX(), new HashSet<>(Set.of(p.getY())), (s1,s2) -> {s2.addAll(s1); return s2;}));
		return new GroupingImpl<>(map);
	}

	@Override
	public <G, V> Grouping<G, V> fromFunction(Set<V> values, Function<V, G> mapper) {
		return fromPairs(()->values.stream().map(v->new Pair<>(mapper.apply(v),v)).iterator());
	}

	@Override
	public <V> Grouping<V, V> singletons(Set<V> values) {
		return withChampion(values, Object::equals, v->true );
	}

	@Override
	public <V> Grouping<V, V> withChampion(Set<V> values, BiPredicate<V, V> sameGroup, Predicate<V> champion) {
		return fromFunction(values, v-> values.stream().filter(w -> sameGroup.test(v, w)).findAny().get());
	}

}
