package a02c.sol1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ListSplitterFactoryImpl implements ListSplitterFactory {

	/**
	 * Typical strategy-based technique: all splitters can be obtained by this default one,
	 * by proper functional arguments
	 */
	private static class ListSplitterImpl<X> implements ListSplitter<X> {

		private final BiPredicate<List<X>, Optional<X>> predicate;
		
		public ListSplitterImpl(BiPredicate<List<X>, Optional<X>> predicate) {
			this.predicate = predicate;
		}

		@Override
		public List<List<X>> split(List<X> list) {
			List<X> accumulator = new ArrayList<>();
			final List<List<X>> output = new ArrayList<>();
			for (final var x: list) {
				if (predicate.test(accumulator,Optional.of(x))) {
					output.add(accumulator);
					accumulator = new ArrayList<>();
				} 
				accumulator.add(x);
			}
			if (predicate.test(accumulator, Optional.empty())) {
				output.add(accumulator);
			}
			return output;
		}

	}

	@Override
	public <X> ListSplitter<X> asPairs() {
		return new ListSplitterImpl<>((acc,o)->acc.size()==2);
	}

	@Override
	public <X> ListSplitter<X> asTriplets() {
		return new ListSplitterImpl<>((acc,o)->acc.size()==3);
	}
	
	@Override
	public <X> ListSplitter<X> asTripletsWithRest() {
		return new ListSplitterImpl<>((acc,o)->o.isEmpty() || acc.size()==3);
	}

	@Override
	public <X> ListSplitter<X> bySeparator(X separator) {
		return new ListSplitterImpl<>((acc,o)-> o.isEmpty() || (acc.size()==1 && acc.get(0).equals(separator)) || o.get().equals(separator));
	}

	@Override
	public <X> ListSplitter<X> byPredicate(Predicate<X> predicate) {
		return new ListSplitterImpl<>((acc,o)-> o.isEmpty() || (acc.size()>0 && predicate.test(o.get()) == !predicate.test(acc.get(acc.size()-1))));
	}

}
