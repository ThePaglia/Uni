package a05b.sol1;

import java.util.Iterator;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class IteratorIteratorFactoryImpl implements IteratorIteratorFactory {

	private <E> Iterator<Iterator<E>> withSizes(Supplier<Stream<E>> supplier, Stream<Integer> sizes) {
		return sizes.map(i -> supplier.get().limit(i).iterator()).iterator();
	}
	
	private <E> Iterator<Iterator<E>> increasing(Supplier<Stream<E>> supplier) {
		return withSizes(supplier,Stream.iterate(1,x->x+1));
	}
	
	private Iterator<Iterator<Integer>> fromUnary(UnaryOperator<Integer> u) {
		return increasing(()->Stream.iterate(0, x->x+1).map(u::apply));
	}


	@Override
	public <E> Iterator<Iterator<E>> constantWithSingleton(E e) {
		return withSizes(() -> Stream.of(e),Stream.generate(()->1));
	}
	
	@Override
	public <E> Iterator<Iterator<E>> increasingSizeWithSingleton(E e) {
		return increasing(()->Stream.generate(()->e));
	}
	
	@Override
	public Iterator<Iterator<Integer>> squares() {
		return fromUnary(i -> i*i);
	}

	@Override
	public Iterator<Iterator<Integer>> evens() {
		return fromUnary(i -> i*2);
	}

}
