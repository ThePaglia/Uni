package a01c.e1;

/**
 * This interface models a (possibly unbounded) sequence of events of type E, produced iteratively,
 * each tagged with a double indicating the time at which it happens. 
 * A next event has a time greater than the previous.
 */
public interface EventHistory<E> {
	
	/**
	 * @return the time of current event
	 */
	double getTimeOfEvent();
	
	/**
	 * @return the content of current event
	 */
	E getEventContent();
	
	/**
	 * moves to next event
	 * @return whether the next event actually exists (false means the history is over)
	 */
	boolean moveToNextEvent();
}
