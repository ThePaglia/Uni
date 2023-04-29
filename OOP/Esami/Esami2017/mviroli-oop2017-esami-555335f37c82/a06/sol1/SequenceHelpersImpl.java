package a06.sol1;

import java.util.Iterator;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author mirko
 * Soluzione con gli stream...
 */
public class SequenceHelpersImpl implements SequenceHelpers {

	private <X> Stream<X> toStream(Sequence<X> sx) {
		return Stream.generate(sx::nextElement);
	}

	private <X> Sequence<X> ofStream(Stream<X> s){
		final Iterator<X> it = s.iterator();
		return () -> it.next();
	}

	@Override
	public <X> Sequence<X> of(X x) {
		return () -> x;
	}

	@Override
	public <X> Sequence<X> cyclic(List<X> x) {
		return ofStream(Stream.generate(x::stream).flatMap(e->e));
	}
	
	
	@Override
	public Sequence<Integer> incrementing(int start, int increment) {
		return new Sequence<Integer>() {
			private int next = start;
			@Override
			public Integer nextElement() {
				final int old = next;
				next = next + increment;
				return old;
			}
		};
	}

	@Override
	public <X> Sequence<X> accumulating(Sequence<X> input, BinaryOperator<X> op) {
		return new Sequence<X>() {
			private X next = input.nextElement();
			@Override
			public X nextElement() {
				final X old = next;
				next = op.apply(old, input.nextElement());
				return old;
			}			
		};
	}

	@Override
	public <X> Sequence<Pair<X, Integer>> zip(Sequence<X> input) {
		return new Sequence<Pair<X,Integer>>() {
			private int index = 0;
			@Override
			public Pair<X,Integer> nextElement() {
				return new Pair<>(input.nextElement(),index++);
			}			
		};
	}

}
