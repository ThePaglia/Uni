package a03b.sol1;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EquivalenceFactoryImpl implements EquivalenceFactory {

	@Override
	public <X> Equivalence<X> fromPartition(Set<Set<X>> partition) {
		var domain = partition.stream().flatMap(s -> s.stream()).collect(Collectors.toSet());
		return fromFunction(domain, x -> partition.stream().filter(s -> s.contains(x)).findAny().get());
	}

	@Override
	public <X> Equivalence<X> fromPredicate(Set<X> domain, BiPredicate<X,X> predicate){
		Set<Set<X>> partition = new HashSet<>();
		for (final var x: domain) {
			var optSet = partition.stream().filter(s -> predicate.test(s.iterator().next(),x)).findAny();
			if (optSet.isEmpty()) {
				partition.add(new HashSet<>(Collections.singleton(x)));
			} else {
				optSet.get().add(x);
			}
		}
		return fromPartition(partition);
	}
	
	@Override
	public <X> Equivalence<X> fromPairs(Set<Pair<X, X>> pairs) {
		var domain = pairs.stream().flatMap(p -> Stream.of(p.getX(),p.getY())).collect(Collectors.toSet());
		return fromPredicate(domain, (x,y)-> pairs.stream().anyMatch(p -> p.equals(new Pair<>(x,y))));
	}

	@Override
	public <X, Y> Equivalence<X> fromFunction(Set<X> domain, Function<X, Y> function) {
		return new Equivalence<>() {

			@Override
			public boolean areEquivalent(X x1, X x2) {
				return domain.contains(x1) && domain.contains(x2) && function.apply(x1).equals(function.apply(x2));
			}

			@Override
			public Set<X> domain() {
				return domain;
			}

			@Override
			public Set<X> equivalenceSet(X x) {
				return domain.stream().filter(y -> areEquivalent(x,y)).collect(Collectors.toSet());
			}

			@Override
			public Set<Set<X>> partition() {
				return Set.copyOf(domain.stream().collect(Collectors.groupingBy(function,Collectors.toSet())).values());
			}

			@Override
			public boolean smallerThan(Equivalence<X> equivalence) {
				var thatPartition = equivalence.partition();
				return this.partition().stream().allMatch(s -> thatPartition.stream().anyMatch(s2 -> s2.containsAll(s)));
			}
			
		};
	}
}
