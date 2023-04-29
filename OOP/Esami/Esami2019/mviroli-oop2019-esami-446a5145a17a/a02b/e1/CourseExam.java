package a02b.e1;

import java.util.Set;

/**
 * An interface to model the activities to perform to pass a university exam, where certain activities can be done only if others have been completed before. 
 * During execution one has to keep track of the activities executed so far: the exam is passed if all the activities have been executed.
 *
 * @param <T> is the type of tasks
 */
public interface CourseExam<A> {
	
	/**
	 * @return the set of all activities
	 */
	Set<A> activities();
	
	/**
	 * @return the set of activities that one can execute now
	 */
	Set<A> getPendingActivities();
	
	/**
	 * executes activity a, assuming this is possible..
	 * @param a 
	 */
	void completed(A a);
	
	/**
	 * @return if all activities have been executed, hence the exam is over
	 */
	boolean examOver();

}
