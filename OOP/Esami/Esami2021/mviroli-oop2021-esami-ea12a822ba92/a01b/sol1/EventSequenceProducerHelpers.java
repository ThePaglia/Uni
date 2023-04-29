package a01b.sol1;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * An interface defining several helper functions (factories and algorithms) over EventSequenceProducers
 *
 */
public interface EventSequenceProducerHelpers {
	
	/**
	 * @param <E>
	 * @param iterator
	 * @return an EventSequenceProducers from the iterator
	 */
	<E> EventSequenceProducer<E> fromIterator(Iterator<Pair<Double,E>> iterator);
	
	/**
	 * @param <E>
	 * @param sequence
	 * @param fromTime
	 * @param toTime
	 * @return the EventSequenceProducer obtained from sequence by taking all events happening
	 * from @fromTime to @toTime
	 */
	<E> List<E> window(EventSequenceProducer<E> sequence, double fromTime, double toTime);
	
	/**
	 * @param <E>
	 * @param sequence
	 * @return an iterable over the content of all events
	 */
	<E> Iterable<E> asEventContentIterable(EventSequenceProducer<E> sequence);
	
	/**
	 * @param <E>
	 * @param sequence
	 * @param time
	 * @return the first event produced by @sequence after @time, or the empty Optional if there is not one 
	 */
	<E> Optional<Pair<Double,E>> nextAt(EventSequenceProducer<E> sequence, double time);
	
	/**
	 * @param <E>
	 * @param sequence
	 * @param predicate
	 * @return the EventSequenceProducer obtained from @sequence by filtering away all events whose 
	 * content does not pass the @predicate
	 */
	<E> EventSequenceProducer<E> filter(EventSequenceProducer<E> sequence, Predicate<E> predicate);
}
