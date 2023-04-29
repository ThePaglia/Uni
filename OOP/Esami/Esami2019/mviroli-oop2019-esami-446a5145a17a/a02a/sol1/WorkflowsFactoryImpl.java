package a02a.sol1;

import java.util.*;
import java.util.stream.*;

public class WorkflowsFactoryImpl implements WorkflowsFactory {
	
	// the map associates to a task to those it needs to be done before starting
	private <T> Workflow<T> fromGraph(Map<T,Set<T>> map) {
		return new Workflow<>() {
			
			private Set<T> done = new HashSet<>();
			
			@Override
			public Set<T> getTasks() {
				return map.keySet();
			}

			@Override
			public Set<T> getNextTasksToDo() {
				return this.isCompleted() ? Set.of() : this.getTasks().stream().filter(this::canBeDone).collect(Collectors.toSet());
			}
			
			private boolean canBeDone(T t) {
				return !this.done.contains(t) && this.done.containsAll(map.get(t));
			}

			@Override
			public void doTask(T t) {
				if (!this.getNextTasksToDo().contains(t)) {
					throw this.getTasks().contains(t) ? new IllegalStateException() : new IllegalArgumentException();
				}
				this.done.add(t);
			}

			@Override
			public boolean isCompleted() {
				return this.done.equals(this.getTasks());
			}
			
		};
	}
	
	@Override
	public <T> Workflow<T> singleTask(T task) {
		return tasksSequence(List.of(task));
	}

	@Override
	public <T> Workflow<T> tasksSequence(List<T> tasks) {
		// t1 -> t0, t2 -> t1, ... plus t0->nothing
		Stream<Pair<T,Set<T>>> s = Stream.concat(
				IntStream.range(1,tasks.size()).mapToObj(i->new Pair<>(tasks.get(i), Set.of(tasks.get(i-1)))), 
				Stream.of(new Pair<>(tasks.get(0),Set.of())));
		return fromGraph(s.collect(Collectors.toMap(Pair::getX,Pair::getY)));
	}

	@Override
	public <T> Workflow<T> tasksJoin(Set<T> initialTasks, T finalTask) {
		// i0-> nothing, i1 -> nothing ... plus final->initialiTasks
		Stream<Pair<T,Set<T>>> s = Stream.concat(
				initialTasks.stream().map(t->new Pair<>(t, Set.of())),
				Stream.of(new Pair<>(finalTask,initialTasks)));
		return fromGraph(s.collect(Collectors.toMap(Pair::getX,Pair::getY)));
	}
	
	@Override
	public <T> Workflow<T> tasksFork(T initialTask, Set<T> finalTasks) {
		Stream<Pair<T,Set<T>>> s = Stream.concat(
				finalTasks.stream().map(t->new Pair<>(t, Set.of(initialTask))),
				Stream.of(new Pair<>(initialTask,Set.of())));
		return fromGraph(s.collect(Collectors.toMap(Pair::getX,Pair::getY)));
	}

	@Override
	public <T> Workflow<T> concat(Workflow<T> first, Workflow<T> second) {
		return new Workflow<>() {
			
			private boolean onFirst = true;

			@Override
			public Set<T> getTasks() {
				Set<T> set = new HashSet<>(first.getTasks());
				set.addAll(second.getTasks());
				return set;
			}

			@Override
			public Set<T> getNextTasksToDo() {
				return this.onFirst ? first.getNextTasksToDo() : second.getNextTasksToDo();
			}

			@Override
			public void doTask(T t) {
				if (this.onFirst) {
					first.doTask(t);
					if (first.isCompleted()) {
						this.onFirst = false;
					}
				} else {
					second.doTask(t);
				}
			}

			@Override
			public boolean isCompleted() {
				return !this.onFirst && second.isCompleted();
			}
			
		};
	}
}
