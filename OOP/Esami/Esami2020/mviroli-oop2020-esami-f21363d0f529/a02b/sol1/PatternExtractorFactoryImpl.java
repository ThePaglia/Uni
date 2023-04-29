package a02b.sol1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class PatternExtractorFactoryImpl implements PatternExtractorFactory {

	/**
	 * Typical strategy-based technique: all patternextractors can be obtained by this default one,
	 * by proper functional arguments
	 */
	
	private static class PatternExtractorImpl<X,Y> implements PatternExtractor<X,Y> {

		private final Predicate<X> predicate;
		private final Function<List<X>, Y> mapper;
		
		public PatternExtractorImpl(Predicate<X> predicate, Function<List<X>, Y> mapper) {
			super();
			this.predicate = predicate;
			this.mapper = mapper;
		}

		@Override
		public List<Y> extract(List<X> list) {
			List<X> accumulator = new ArrayList<>();
			final List<Y> output = new ArrayList<>();
			for (final var x: list){
				if (predicate.test(x)) {
					accumulator.add(x);
				} else if (!accumulator.isEmpty()) {
					output.add(this.mapper.apply(accumulator));
					accumulator = new ArrayList<>();
				} 
			}
			if (!accumulator.isEmpty()) {
				output.add(this.mapper.apply(accumulator));
			}
			return output;
		}
	}

	@Override
	public PatternExtractor<Integer, Integer> countConsecutiveZeros() {
		return new PatternExtractorImpl<>( x -> x==0, List::size);
	}

	@Override
	public PatternExtractor<Double, Double> averageConsecutiveInRange(double min, double max) {
		return new PatternExtractorImpl<>( x -> x>=min && x<=max, list -> list.stream().mapToDouble(d->d).average().getAsDouble());
	}

	@Override
	public PatternExtractor<String, String> concatenateBySeparator(String separator) {
		return new PatternExtractorImpl<>( x -> !x.equals(separator), list -> list.stream().reduce((x,y)->x+y).get());
	}
	
	private boolean isNumeric(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public PatternExtractor<String, Double> sumNumericStrings() {
		return new PatternExtractorImpl<>(this::isNumeric, list -> list.stream().map(Double::parseDouble).reduce((x,y)->x+y).get());
	}

	
}
