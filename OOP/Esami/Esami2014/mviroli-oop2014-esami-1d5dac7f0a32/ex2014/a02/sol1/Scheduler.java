package ex2014.a02.sol1;

import java.util.Set;

/*
 * This interface models a scheduler of tasks of type T:
 * - Tasks enter the system by method add()
 * - One task can be in execution at a time, it's execution is interrupted by:
 * - A task can be compInterruption of a task either happens by method
 * -- 'complete' (the task exits the system)
 * -- 'stop' (the task is stopped and goes to a queue of stopped tasks)
 * -- 'preempt' (the task is moved back into the waiting list). 
 * - By method 'unStop' a stopped task becomes waiting
 * .. other methods read the current state of the scheduler  
 */

public interface Scheduler<T> {
	
	/**
	 * @param t, the task to add (it is not executed yet)
	 * @throws a IllegalArgumentException if the task is already added
	 */
	void add(T t);
	
	/**
	 * @return true if some task is currently in execution
	 */
	boolean isExecuting();
	
	/**
	 * @return the task under execution
	 * @throws a NoSuchElementException if no task is in execution
	 */
	T getExecutingTask();
	
	/**
	 * Executes the next task in the queue 
	 *
	 * @throws a IllegalArgumentException if a task is currently in execution
	 * @throws a NoSuchElementException if no task is waiting 
	 */
	void executeNext();
	
	/**
	 * Removes the currently executed task from this scheduler 
	 *
	 * @throws a IllegalStateException if no task is currently in execution
	 */
	void complete();
	
	/**
	 * Stops the currently executed task and moves it at the end of the queue of stopped tasks 
	 *
	 * @throws a IllegalStateException if no task is currently in execution
	 */
	void stop();
	
	/**
	 * Stops the currently executed task and moves it back at the end of the queue of waiting tasks 
	 *
	 * @throws a IllegalStateException if no task is currently in execution
	 */
	void preempt();
	
	/**
	 * Takes task t from the queue of stopped tasks and moves it at the end of the queue of waiting tasks 
	 *
	 * @throws a NoSuchElementException if task t is not currently stopped
	 */
	void unStop(T t);
	
	/**
	 * @return the set of stopped tasks (this should be a defensive copy!)
	 */
	Set<T> allStopped();
	
	/**
	 * @return the set of waiting tasks (this should be a defensive copy!)
	 */
	Set<T> allWaiting();

}
