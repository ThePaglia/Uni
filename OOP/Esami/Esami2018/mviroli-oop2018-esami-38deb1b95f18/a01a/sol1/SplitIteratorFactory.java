package a01a.sol1;

import java.util.List;
import java.util.function.Function;

/**
 * A factory for creating SplitIterators
 */
public interface SplitIteratorFactory {
	
	/**
	 * @param start
	 * @param stop
	 * @return a SplitIterator from start to stop included
	 */
	SplitIterator<Integer> fromRange(int start, int stop);
	
	/**
	 * @param start
	 * @param stop
	 * @return a SplitIterator from start to stop included, without splitting ability
	 */
	SplitIterator<Integer> fromRangeNoSplit(int start, int stop);
	
	/**
	 * Creates a SplitIterator over a list. 
	 * 
	 * @param list
	 * @return a SplitIterator over list
	 */
	<X> SplitIterator<X> fromList(List<X> list);
	
	/**
	 * @param list
	 * @return a SplitIterator over list, without splitting ability
	 */
	<X> SplitIterator<X> fromListNoSplit(List<X> list);
	
	/**
	 * @param si
	 * @return a new SplitIterator created from si, simply excluding the first element produced by si
	 */
	<X> SplitIterator<X> excludeFirst(SplitIterator<X> si);
	
	/**
	 * @param si
	 * @param mapper
	 * @return a new SpliIterator created from si, mapping each produced element by mapper
	 */
	<X,Y> SplitIterator<Y> map(SplitIterator<X> si, Function<X,Y> mapper);

}
