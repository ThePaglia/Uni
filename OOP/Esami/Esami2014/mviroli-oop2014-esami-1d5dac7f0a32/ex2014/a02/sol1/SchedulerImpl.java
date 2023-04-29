package ex2014.a02.sol1;

import java.util.*;
import java.util.function.Supplier;

public class SchedulerImpl<T> implements Scheduler<T>{
	
	private final LinkedList<T> waitingList = new LinkedList<>();
	private final LinkedList<T> stoppedList = new LinkedList<>();
	private Optional<T> executingTask = Optional.empty();
	
	@Override
	public void add(T t) {
		if (this.waitingList.contains(t) || this.stoppedList.contains(t) || this.executingTask.equals(Optional.of(t))){
			throw new IllegalArgumentException();
		}
		this.waitingList.addLast(t);
	}

	@Override
	public boolean isExecuting() {
		return this.executingTask.isPresent();
	}

	@Override
	public T getExecutingTask() {
		return this.executingTask.get();
	}

	@Override
	public void executeNext() {
		if (this.isExecuting()){
			throw new IllegalStateException();
		}
		if (this.waitingList.size()==0){
			throw new NoSuchElementException();
		}
		this.executingTask = Optional.of(this.waitingList.poll());
	}

	@Override
	public void complete() {
		if (!this.isExecuting()){
			throw new IllegalStateException();
		}
		this.executingTask = Optional.empty();
	}

	@Override
	public void stop() {
		if (!this.isExecuting()){
			throw new IllegalStateException();
		}
		this.stoppedList.addLast(this.executingTask.get());
		this.executingTask = Optional.empty();		
	}
	
	@Override
	public void preempt() {
		if (!this.isExecuting()){
			throw new IllegalStateException();
		}
		this.waitingList.addLast(this.executingTask.get());
		this.executingTask = Optional.empty();		
	}
	
	@Override
	public void unStop(T t) {
		if (!this.stoppedList.contains(t)){
			throw new NoSuchElementException();
		}
		if (this.stoppedList.removeFirstOccurrence(t)){
			this.waitingList.addLast(t);
		}
	}

	@Override
	public Set<T> allStopped() {
		return new HashSet<>(this.stoppedList);
	}

	@Override
	public Set<T> allWaiting() {
		return new HashSet<>(this.waitingList);
	}


}
