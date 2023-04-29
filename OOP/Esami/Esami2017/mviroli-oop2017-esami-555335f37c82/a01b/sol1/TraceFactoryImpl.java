package a01b.sol1;

import java.util.function.Supplier;
import java.util.stream.IntStream;

public class TraceFactoryImpl implements TraceFactory {

	@Override
	public <X> Trace<X> fromSuppliers(Supplier<Integer> deltaTime, Supplier<X> value, int size) {
		return new TraceImpl<>(IntStream.iterate(0,l -> l + deltaTime.get())
                						 .<Event<X>>mapToObj(l -> new EventImpl<X>(l,value.get()))
                						 .limit(size)
                						 .iterator());
	}

	@Override
	public <X> Trace<X> constant(Supplier<Integer> deltaTime, X value, int size) {
		return fromSuppliers(deltaTime, ()->value, size);
	}

	@Override
	public <X> Trace<X> discrete(Supplier<X> value, int size) {
		return fromSuppliers(()->1, value, size);
	}

}
