package a06.sol1;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.Collectors;



/**
 * An alternative solution would be to use template method, with CirclerTemplate
 * being an asbtract class with an abstract method:
 * 
 * Stream<Integer> indexes(int size)
 * 
 * In this case, each method in the factory relies on a different implementation
 * of CirclerTemplate. Possibly, ...SkipOne versions could be Obtained in one shot
 * by a wrapper/decorator.
 *
 */
public class CirclerFactoryImpl implements CirclerFactory {
	
	private class CirclerTemplate<T> implements Circler<T> {
		
		private Iterator<T> iterator = null;
		private final Function<Integer, Stream<Integer>> indexes;
		
		protected CirclerTemplate(Function<Integer, Stream<Integer>> indexes) {
			this.indexes = indexes;
		}
		
		@Override
		public void setSource(List<T> elements) {
			this.iterator = indexes.apply(elements.size()).map(elements::get).iterator();
		}

		@Override
		public T produceOne() {
			return this.iterator.next();
		}

		@Override
		public List<T> produceMany(int n) {
			return Stream.generate(this::produceOne).limit(n).collect(Collectors.toList());
		}
	}
	
	private Stream<Integer> lr(int n) { 
		return Stream.iterate(0, i -> i + 1).map(i -> i % n);
	}
	
	private Stream<Integer> alt(int n) { 
		return Stream.iterate(0, i -> i+1).map(i -> (i / n) % 2 == 0 ? i % n : n - 1 - (i % n));
	}

	private Stream<Integer> last(int n) { 
		return Stream.iterate(0, i -> i == n-1 ? i : i+1);
	}
	
	private Stream<Integer> skipOne(Stream<Integer> stream) {
		var it = stream.iterator();
		return Stream.generate(()-> it.next()).peek(i -> it.next());
	}
				
	@Override
	public <T> Circler<T> leftToRight() {
		return new CirclerTemplate<>(this::lr);
	}
	
	
	@Override
	public <T> Circler<T> alternate() {
		return new CirclerTemplate<>(this::alt);
	}

	@Override
	public <T> Circler<T> stayToLast() {
		return new CirclerTemplate<>(this::last);
	}

	@Override
	public <T> Circler<T> leftToRightSkipOne() {
		return new CirclerTemplate<>(n -> skipOne(this.lr(n)));
	}

	@Override
	public <T> Circler<T> alternateSkipOne() {
		return new CirclerTemplate<>(n -> skipOne(this.alt(n)));
	}

	@Override
	public <T> Circler<T> stayToLastSkipOne() {
		return new CirclerTemplate<>(n -> skipOne(this.last(n)));
	}
	
}
