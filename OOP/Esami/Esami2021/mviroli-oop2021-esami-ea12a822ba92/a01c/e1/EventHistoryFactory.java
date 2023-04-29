package a01c.e1;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Models a factory to create various EventHistory
 */
public interface EventHistoryFactory {

	/**
	 * @param <E>
	 * @param map, a map from time to the event happened at that time
	 * @return the event history made of the ordered sequence of events contained in the map
	 */
	<E> EventHistory<E> fromMap(Map<Double, E> map);

	/**
	 * @param <E>
	 * @param times, an iterator over the moments of time at which events happen (t1,t2,t3,...)
	 * @param content, an iterator over the content of events happening from time to time (e1,e2,e3,...)
	 * @return the history (t1,e1), (t2,e2), ...
	 * Note the size of the history is them minimum of sizes of times and content iterators
	 */
	<E> EventHistory<E> fromIterators(Iterator<Double> times, Iterator<E> content);
	
	/**
	 * @param <E>
	 * @param content, as an ordered list of events e1,e2,..,en
	 * @param initial
	 * @param delta
	 * @return the history (initial,e1), (initial+delta,e2), (initial+delta+delta,e3),...
	 */
	<E> EventHistory<E> fromListAndDelta(List<E> content, double initial, double delta);
	
	/**
	 * @param <E>
	 * @param content
	 * @param size
	 * @return the history (t1,e1)..(tn,en) where e1,e2,... are obtained from @content,
	 * t1, t2-t1, t3-t2 are obtained from Math.random(), and n=size
	 */
	<E> EventHistory<E> fromRandomTimesAndSupplier(Supplier<E> content, int size);
	
	/**
	 * @param file is a text file in which each line is of the kind "1.45:aabb", 
	 * where 1.45 is the time and 'aabb' is the event content as a String.
	 * @return the history made of events, one per line
	 * @throws IOException if the file can't be read
	 * Suggestions if needed:
	 * - use method split(":",2) in String to split "1.45:aabb" into new String[]{"1.45","aabb"}
	 * - use method Double.parseDouble to convert a string into a double
	 * - use a readLine of a BufferedReader that wraps a FileReader(file) to read from text files   
	 */
	EventHistory<String> fromFile(String file) throws IOException;

}