package a02a.e1;

import java.util.List;

/**
 * An interface modelling helpers (factories and transformers) of various kinds of RecursiveIterators
 * Note that recursion might in general be a good technique to implement some of such methods.
 */
public interface RecursiveIteratorHelpers {
    
    /**
	 * @param list
	 * @return an iterator over all the elements of list considered one after the other
	 */
	<X> RecursiveIterator<X> fromList(List<X> list);

    /**
	 * @param input
	 * @param max, assumed to be positive
	 * @return extracts elements from the input iterator (no more than max), and creates a list
	 */
	<X> List<X> toList(RecursiveIterator<X> input, int max);

    /**
     * @param first
     * @param second
     * @return an iterator of pairs of elements from first and second, orderly
     * it provides elements until one of the two iterators is over
     */
    <X,Y> RecursiveIterator<Pair<X,Y>> zip(RecursiveIterator<X> first, RecursiveIterator<Y> second);

    /**
     * @param iterator
     * @return an iterator of pairs of elements from the input iterator and orderly 0,1,2,3,...
     * if input gives (a,b,c,d), output is ([a,0], [b,1], [c,2], [d,3])
     */
    <X> RecursiveIterator<Pair<X,Integer>> zipWithIndex(RecursiveIterator<X> iterator);

    /**
     * @param iterator
     * @return an iterator alternating elements of first and second, until one of them is over,
     * at which point the other iterator proceeds
     * if first gives (a,b,c,d) and second gives (e,f), output is (a,e,b,f,c,d)
     */
    <X> RecursiveIterator<X> alternate(RecursiveIterator<X> first, RecursiveIterator<X> second);
}
