package a01b.e1;

import java.util.NoSuchElementException;

/**
 * An interface to model a producer of successive events in time, modelled as pairs
 * of a double representing time of event, and a content of type E.
 */
public interface EventSequenceProducer<E> {
	
	/**
	 * @return the next event
	 * @throws NoSuchElementException if there are no more events to be produced (at this point,
	 * this object should no longer be used)
	 */
	Pair<Double,E> getNext() throws NoSuchElementException;
}
