package a04.sol1;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/*
 * This interface models an immutable (and purely functional) infinite stream of values of type E
 * (with no specific connection with the well-known interface java.util.stream.Stream)
 * The first element of the stream is given by: stream.next().getElement()
 * The second element of the stream is given by: stream.next().getStream().next().getElement()
 * and so on
 *   
 */
public interface FunctionalStream<E> {
	
	/**
	 * This interface model the result of asking this stream what comes next, as a pair of an element E
	 * and a new stream to be used next.
	 *
	 * @param <E>
	 */
	interface NextResult<E> {
		E getElement();
		FunctionalStream<E> getStream();
	}
	
	
	/**
	 * @return what this stream produces next. Note that invoking this method many times always give the same result
	 */
	NextResult<E> next();
	
	/**
	 * @param size
	 * @return a list of the next size elements obtained from this stream
	 */
	List<E> toList(int size);
	
	/**
	 * @return a java.util.Iterator to the same elements produced by this stream
	 * THIS IS OPTIONAL IN THIS EXAM
	 */
	Iterator<E> toIterator();
}
