	package a02a.sol1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class ScannerFactoryImpl implements ScannerFactory {
	
	/**
	 * Typical strategy-based technique: all scanners can be obtained by the default one,
	 * by proper arguments (datas and lambdas)
	 */
	private <I,S,O> Scanner<I,O> defaultScanner(S initialState, BiFunction<I,S,S> updateState, Function<S,O> mapper){
		return new Scanner<>() {

			@Override
			public O scan(Iterator<I> input) {
				S state = initialState;
				while(input.hasNext()) {
					state = updateState.apply(input.next(), state);
				}
				return mapper.apply(state);
			}
			
		};
	}
	
	private <I,O> Scanner<I,O> simpleScanner(O initialState, BiFunction<I,O,O> updateState){
		return defaultScanner(initialState,updateState,x->x); 
	}
	
	

	@Override
	public Scanner<Integer, List<Integer>> cumulativeSum() {
		return simpleScanner(
				new LinkedList<Integer>(), 
				(sum,list) -> {
					list.add(sum+(list.isEmpty() ? 0 : list.get(list.size()-1)));
					return list;
				});
	}

	@Override
	public <X, Y> Scanner<X, List<Y>> collect(Predicate<X> filter, Function<X, Y> mapper) {
		return simpleScanner(
				new LinkedList<Y>(), 
				(e,list) -> {
					if (filter.test(e)) {
						list.add(mapper.apply(e));
					} 
					return list;
				});
	}

	@Override
	public <X> Scanner<X, Optional<X>> findFirst(Predicate<X> filter) {
		return simpleScanner(
				Optional.<X>empty(), 
				(e,opt) -> opt.isPresent() ? opt : Optional.of(e).filter(filter));
	}

	@Override
	public Scanner<Integer, Optional<Integer>> maximalPrefix() {
		return defaultScanner(
				new Pair<>(false,Optional.<Integer>empty()), 
				(e,p) -> {
					if (p.getY().isEmpty()) { 
						return new Pair<>(false,Optional.of(e));
					} else if (p.getX()) {
						return p;
					} else if (e > p.getY().get()) { 
						return new Pair<>(false,Optional.of(e));
					}
					return new Pair<>(true,p.getY());
				},
				Pair::getY);
	}

}
