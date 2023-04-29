package a02b.sol1;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

import esami.ex2013.a05.e3.Function;

public class TransducerImpl<X,Y> implements Transducer<X, Y> {
	
	private final int maxSize;
	private Function<List<X>,Y> mapper;
	private List<X> queue;
	private boolean inputOver;
	
	// Package-private
	TransducerImpl(int maxSize, Function<List<X>, Y> mapper) {
		super();
		this.maxSize = maxSize;
		this.mapper = mapper;
		this.queue = new LinkedList<>();
	}
	
	private void checkIllegalState(Supplier<Boolean> condition) {
		if (condition.get()) {
			throw new IllegalStateException();
		}
	}

	@Override
	public Y getOutputElement() {
		this.checkIllegalState(()->!isNextOutputReady());
		final int size = Math.min(this.maxSize, this.queue.size());
		final Y y = mapper.apply(this.queue.subList(0, size));
		this.queue = this.queue.subList(size, this.queue.size());
		return y;
	}

	@Override
	public boolean isNextOutputReady() {
		return this.queue.size()>= this.maxSize || (this.inputOver && this.queue.size()>0);
	}

	@Override
	public boolean isOutputOver() {
		return this.inputOver && this.queue.isEmpty();
	}

	@Override
	public void provideNextInput(X y) {
		this.checkIllegalState(()->this.inputOver);
		this.queue.add(y);
	}

	@Override
	public void inputIsOver() {
		this.checkIllegalState(()->this.inputOver);
		this.inputOver = true;
	}
	
	

}
