package a01a.e1;

import java.lang.StackWalker.Option;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class AcceptorFactoryAdvancedImpl implements AcceptorFactory {


    @Override
    public Acceptor<String, Integer> countEmptyStringsOnAnySequence() {
        return generalised(0, (s,i) -> Optional.of(i + (s.length()==0 ? 1 : 0)), s -> Optional.of(s));
    }

    @Override
    public Acceptor<Integer, String> showAsStringOnlyOnIncreasingSequences() {
        return generalised(new ArrayList<Integer>(),
                            (i, list) -> Optional.<ArrayList<Integer>>of(list)
                                            .filter(ll -> ll.isEmpty() || i > list.get(list.size()-1))
                                            .map(ll -> { ll.add(i); return ll;}),
                            l -> Optional.of(l.stream().map(String::valueOf).collect(Collectors.joining(":"))));
    }

    @Override
    public Acceptor<Integer, Integer> sumElementsOnlyInTriples() {
        return generalised(new ArrayList<Integer>(),
                            (i, list) -> Optional.<ArrayList<Integer>>of(list)
                                            .filter(ll -> ll.isEmpty() || list.size() < 3)
                                            .map(ll -> { ll.add(i); return ll;}), 
                            l -> l.size() < 3 ? Optional.empty() : Optional.of(l.stream().collect(Collectors.summingInt(i -> i))));
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
    public <E, O, S> Acceptor<E, O> generalised(S initial, BiFunction<E, S, Optional<S>> stateFun,
            Function<S, Optional<O>> outputFun) {
        return new Acceptor<>() {
			private Optional<S> state = Optional.of(initial);

			@Override
			public boolean accept(E e) {
                if (state.isEmpty()) {
                    return false;
                }
				state = stateFun.apply(e, state.get());
				return state.isPresent();
			}

			@Override
			public Optional<O> end() {
                if (state.isEmpty()) {
                    return Optional.empty();
                }
				return outputFun.apply(state.get());
			}
		};
    }

}
