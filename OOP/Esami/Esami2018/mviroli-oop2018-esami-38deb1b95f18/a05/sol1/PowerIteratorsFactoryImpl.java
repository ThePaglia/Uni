package a05.sol1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class PowerIteratorsFactoryImpl implements PowerIteratorsFactory {
	
	private <X> PowerIterator<X> fromStream(final Stream<X> stream){
		final Iterator<X> iterator = stream.iterator();
		return new PowerIterator<X>() {
			
			private List<X> pastList = new ArrayList<>();
	
			@Override
			public Optional<X> next() {
				X x = null;
				if (iterator.hasNext()) {
					x = iterator.next();
					pastList.add(x);
				}
				return Optional.ofNullable(x);
			}

			@Override
			public List<X> allSoFar() {
				return Collections.unmodifiableList(this.pastList);
			}

			@Override
			public PowerIterator<X> reversed(){
				final List<X> list = new ArrayList<>(pastList);
				Collections.reverse(list);
				return fromStream(list.stream());
			}
		};
	}

	@Override
	public PowerIterator<Integer> incremental(int start, UnaryOperator<Integer> successive) {
		return this.fromStream(Stream.iterate(start,successive));
	}

	@Override
	public <X> PowerIterator<X> fromList(List<X> list) {
		return this.fromStream(list.stream());
	}

	@Override
	public PowerIterator<Boolean> randomBooleans(int size) {
		final Random random = new Random();
		return this.fromStream(Stream.generate(()->random.nextBoolean()).limit(size));
	}

}
