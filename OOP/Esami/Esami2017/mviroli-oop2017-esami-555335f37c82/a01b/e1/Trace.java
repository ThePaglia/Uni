package a01b.e1;

import java.util.Iterator;
import java.util.Optional;

/**
 * An immutable representation of a finite sequence of events (with increasing time) to be accessed once and sequentially
 * It uses class java.util.Optional, please check its documentation. Recall that:
 * - you create an empty Optional with: Optional.empty() 
 * - you create a non-empty Optional with: Optional.of(10)
 * - you check if an optional is not empty with: Optional.isPresent()
 * - you get the content of an optional with: Optional.get()
 */
public interface Trace<X> {

	/**
	 * @return the next event, if any; as this is returned the next call will give the successive element
	 */
	Optional<Event<X>> nextEvent();
	
	/**
	 * @return an iterator over the trace
	 */
	Iterator<Event<X>> iterator();
	
	
	/**
	 * @param time
	 * It skips all the events until reaching the first event in the future of time 
	 */
	void skipAfter(int time);
	
	/**
	 * @param trace: an input trace
	 * Creates a new trace, obtained consuming and combining this and trace: recall that the result should 
	 * respect temporal ordering of events 
	 */
	Trace<X> combineWith(Trace<X> trace);
	
	/**
	 * @param value
	 * Creates a new trace, obtained from this (and consuming it): events with content equal to value are dropped away
	 */
	Trace<X> dropValues(X value);
}
