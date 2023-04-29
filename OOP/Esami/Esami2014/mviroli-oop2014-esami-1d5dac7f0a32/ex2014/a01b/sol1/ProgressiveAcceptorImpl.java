package ex2014.a01b.sol1;

import java.util.*;

public class ProgressiveAcceptorImpl<X> implements ProgressiveAcceptor<X> {

	private ProgressiveFilter<X> filter;
	private Aggregator<X> aggregator;
	private int size = -1;
	private final List<X> elements = new LinkedList<X>();

	private void checkInitialization() {
		if (this.filter == null || this.aggregator == null || this.size < 0) {
			throw new IllegalStateException();
		}
	}

	@Override
	public void setProgressiveFilter(ProgressiveFilter<X> filter) {
		this.filter = Objects.requireNonNull(filter);
	}

	@Override
	public void setAggregator(Aggregator<X> aggregator) {
		this.aggregator = Objects.requireNonNull(aggregator);
	}

	@Override
	public void setSize(int size) {
		if (size < 0) {
			throw new IllegalArgumentException();
		}
		this.size = size;
	}

	@Override
	public boolean accept(int pos, X elem) {
		this.checkInitialization();
		if (pos > this.elements.size()) {
			return false;
		}
		if (pos > 0 && !this.filter.isNextOK(this.elements.get(pos - 1), elem)) {
			return false;
		}
		if (pos < this.elements.size()) {
			this.elements.set(pos, elem);
		} else {
			this.elements.add(elem);
		}
		ListIterator<X> it = this.elements.listIterator(pos + 1);
		while (it.hasNext()) {
			it.next();
			it.remove();
		}
		return true;
	}

	@Override
	public X aggregateAll() {
		this.checkInitialization();
		Iterator<X> it = this.elements.iterator();
		X x = it.next();
		while (it.hasNext()) {
			x = this.aggregator.aggregate(x, it.next());
		}
		return x;

	}

}
