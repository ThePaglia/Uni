package a04a.sol1;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class SequencesProvidersFactoryImpl implements SequencesProvidersFactory {
	
	private static <E> SequencesProvider<E> fromStream(Supplier<Stream<List<E>>> streamSupplier) {
		return new SequencesProvider<E>() {
			
			{ reset(); } // non-static initializer
			
			private Iterator<List<E>> iterator;

			@Override
			public void reset() {
				iterator = streamSupplier.get().iterator();
			}

			@Override
			public List<E> nextSequence() {
				return iterator.next();
			}

			@Override
			public boolean hasOtherSequences() {
				return iterator.hasNext();
			}
			
		};
	}
	
	private <E> Stream<List<E>> iterativeStream(E e){
		return Stream.iterate(0, i->i+1).map(i -> Collections.nCopies(i, e));
	}
	
	private <E> Stream<List<E>> alternatingStream(E e1, E e2){
		return Stream.iterate(0, i->i+1).flatMap(i -> Stream.of(Collections.nCopies(i, e1),Collections.nCopies(i, e2)));
	}

	@Override
	public <E> SequencesProvider<E> iterative(E e) {
		return fromStream(()->iterativeStream(e));
	}

	@Override
	public <E> SequencesProvider<E> alternating(E e1, E e2) {
		return fromStream(()->alternatingStream(e1,e2));
	}

	@Override
	public <E> SequencesProvider<E> iterativeBounded(E e, int bound) {
		return fromStream(()->iterativeStream(e).limit(bound));
	}

	@Override
	public <E> SequencesProvider<E> alternatingBounded(E e1, E e2, int bound) {
		return fromStream(()->alternatingStream(e1,e2).limit(bound));
	}

}
