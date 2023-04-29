package a05a.sol1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class IteratorOfListsFactoryImpl implements IteratorOfListsFactory {
	
	private static <E> IteratorOfLists<E> fromStream(Supplier<Stream<List<E>>> streamSupplier) {
		return new IteratorOfLists<E>() {
			
			{ reset(); } // non-static initializer
			
			private Iterator<List<E>> iterator;

			@Override
			public void reset() {
				iterator = streamSupplier.get().iterator();
			}

			@Override
			public List<E> nextList() {
				return iterator.next();
			}

			@Override
			public boolean hasOtherLists() {
				return iterator.hasNext();
			}
			
		};
	}
	
	
	private <E> Stream<List<E>> iterativeStream(E e){
		return Stream.iterate(0, i->i+1).map(i -> Collections.nCopies(i, e));
	}
	
	@Override
	public <E> IteratorOfLists<E> iterative(E e) {
		return fromStream(()->iterativeStream(e));
	}

	@Override
	public <E> IteratorOfLists<E> iterativeOnList(List<E> list) {
		return fromStream(()->Stream.iterate(0, i->i+1).map(i-> 
			IntStream.range(0, i).mapToObj(j -> list.get(j % list.size())).collect(Collectors.toList())
		));
	}
	
	@Override
	public <E> IteratorOfLists<E> iterativeWithPreamble(E e, List<E> preamble) {
		return fromStream(()->iterativeStream(e).map(l -> {
			List<E> ll = new ArrayList<>(preamble);
			ll.addAll(l);
			return ll;
		}));
	}

	

}
