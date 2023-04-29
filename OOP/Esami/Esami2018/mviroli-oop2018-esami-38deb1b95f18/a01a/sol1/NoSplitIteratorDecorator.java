package a01a.sol1;

import java.util.Optional;

public class NoSplitIteratorDecorator<X> implements SplitIterator<X> {
	
	private final SplitIterator<X> si;
	
	public NoSplitIteratorDecorator(SplitIterator<X> si) {
		this.si = si;
	}

	@Override
	public Optional<X> next() {
		return this.si.next();
	}

	@Override
	public SplitIterator<X> split() {
		throw new UnsupportedOperationException();
	}

	
	
}
