package a04.sol1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@FunctionalInterface
public interface FunctionalStreamTemplate<E> extends FunctionalStream<E> {

	@Override
	default List<E> toList(int size) {
		var list = new LinkedList<E>();
		FunctionalStream<E> stream = this;
		for (int i=0; i<size; i++) {
			var next = stream.next();
			list.add(next.getElement());
			stream = next.getStream();
		}
		return list;
	}
	
	@Override
	default Iterator<E> toIterator() {
		return new Iterator<>() {

			FunctionalStream<E> stream = FunctionalStreamTemplate.this;
			
			@Override
			public boolean hasNext() {
				return true;
			}

			@Override
			public E next() {
				var v = stream.next().getElement();
				stream = stream.next().getStream();
				return v;
			}
		};
	}

	public static class NextResultImpl<E> implements NextResult<E>{
		private final E element;
		private final FunctionalStream<E> nextIterator;
		
		public NextResultImpl(E element, FunctionalStream<E> nextIterator) {
			this.element = element;
			this.nextIterator = nextIterator;
		}

		@Override
		public E getElement() {
			return this.element;
		}

		@Override
		public FunctionalStream<E> getStream() {
			return this.nextIterator;
		}
		
	}

}
