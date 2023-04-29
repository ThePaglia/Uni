package a01a.sol1;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import aula.ex1.InfiniteSequence;

/**
 * @author mirko
 * Soluzione con gli stream...
 */
public class InfiniteSequenceOpsImpl implements InfiniteSequenceOps {

	private <X> Stream<X> toStream(InfiniteSequence<X> sx) {
		return Stream.generate(sx::nextElement);
	}

	private <X> InfiniteSequence<X> ofStream(Stream<X> s){
		final Iterator<X> it = s.iterator();
		return () -> it.next();
	}

	@Override
	public <X> InfiniteSequence<X> ofValue(X x) {
		return () -> x;
	}

	@Override
	public <X> InfiniteSequence<X> ofValues(List<X> x) {
		return ofStream(Stream.generate(x::stream).flatMap(e->e));
	}
	
	@Override
	public InfiniteSequence<Double> averageOnInterval(InfiniteSequence<Double> s, int intervalSize) {
		return () -> toStream(s).limit(intervalSize).mapToDouble(d->d).average().getAsDouble();
	}

	@Override
	public <X> InfiniteSequence<Boolean> equalsTwoByTwo(InfiniteSequence<X> is) {
		return () -> is.nextElement().equals(is.nextElement());
	}

	@Override
	public <X> InfiniteSequence<X> oneEachInterval(InfiniteSequence<X> s, int intervalSize) {
		return () -> toStream(s).limit(intervalSize).reduce((a,b)->b).get();
	}

	@Override
	public <X,Y extends X> InfiniteSequence<Boolean> equalsOnEachElement(InfiniteSequence<X> isx, InfiniteSequence<Y> isy) {
		return () -> isx.nextElement().equals(isy.nextElement());
	}

	@Override
	public <X> Iterator<X> toIterator(InfiniteSequence<X> sx) {
		return toStream(sx).iterator();
	}

}
