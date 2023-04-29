package a01a.sol1;

import java.util.LinkedList;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

// Soluzione avanzata in stile funzionale, con uso di strategy e interfacce funzionali 


public class AcceptorFactoryAdvancedImpl implements AcceptorFactory {

	@Override
	public Acceptor<String, Integer> countEmptyStringsOnAnySequence() {
		return generalised(0,(str,i) -> Optional.of(i + (str.isEmpty() ? 1 : 0)),Optional::of);	
	}

	@Override
	public Acceptor<Integer, Integer> sumElementsOnlyInTriples() {
		return generalised(
				new LinkedList<Integer>(),
				(e,l) -> {l.add(e); return Optional.of(l).filter(ll -> ll.size()<=3);},
				l -> Optional.of(l)
							.filter(ll -> ll.size()==3)
							.map(ll -> ll.stream().collect(Collectors.summingInt(i->i))));
	}


	@Override
	public Acceptor<Integer, String> showAsStringOnlyOnIncreasingSequences() {
		return generalised(
				new LinkedList<Integer>(),
				(e,l) -> Optional.<LinkedList<Integer>>of(l)
						.filter(ll -> ll.isEmpty() || e > ll.getLast())
						.map(ll -> {ll.add(e); return ll;}),
				l -> Optional.of(l.stream().map(String::valueOf).collect(Collectors.joining(":"))));
	}

	@Override
	public <E, O1, O2> Acceptor<E, Pair<O1, O2>> acceptBoth(Acceptor<E, O1> a1, Acceptor<E, O2> a2) {
		return new Acceptor<>() {

			@Override
			public boolean accept(E e) {
				return a1.accept(e) && a2.accept(e);
			}

			@Override
			public Optional<Pair<O1, O2>> end() {
				return a1.end().flatMap(o1 -> a2.end().map( o2 -> new Pair<>(o1,o2)));
			}
			
		}; 
	}

	@Override
	public <E, O, S> Acceptor<E, O> generalised(S initial, BiFunction<E, S, Optional<S>> stateFunction,
			Function<S, Optional<O>> outputFunction) {
		return new Acceptor<>() {
			private Optional<S> state = Optional.of(initial);

			@Override
			public boolean accept(E e) {
				state = state.flatMap(s -> stateFunction.apply(e, s));
				return state.isPresent();
			}

			@Override
			public Optional<O> end() {
				return this.state.flatMap(outputFunction::apply);
			}
		};
	}
}
