package a02c.sol1;

import java.util.function.Predicate;

/**
 * An interface modelling a factory of various kind of ListSplitters
 */
public interface ListSplitterFactory {
	
	/**
	 * @param <X>
	 * @return a ListSplitter that breaks the input list into pieces of size=2, discarding the final part if smaller
	 */
	<X> ListSplitter<X> asPairs();
	
	/**
	 * @param <X>
	 * @return a ListSplitter that breaks the input list into pieces of size=3, discarding the final part if smaller
	 */
	<X> ListSplitter<X> asTriplets();
	
	/**
	 * @param <X>
	 * @return a ListSplitter that breaks the input list into pieces of size=3, keeping the final part even if smaller
	 */
	<X> ListSplitter<X> asTripletsWithRest();
	
	/**
	 * @param <X>
	 * @param separator
	 * @return a ListSplitter that breaks the input list each time the separator occurs
	 * for instance, if separator is 0, then splitting (10,20,0,30,0,40,50) gives ((10,20),(0),(30),(0),(40,50))
	 */
	<X> ListSplitter<X> bySeparator(X separator);
	
	/**
	 * @param <X>
	 * @param predicate
	 * @return an ListSplitter that breaks the input list in pieces of consecutive elements for which the predicate holds, 
	 * then one for which the predicate does not hold, then holds, and so on.. 
	 * for instance, if predicate is x->x>0, then splitting (10,20,-5,-6,30,-2) gives ((10,20),(-5,-6),(30),(-2))
	 * THIS IS OPTIONAL IN THIS EXAM
	 */
	<X> ListSplitter<X> byPredicate(Predicate<X> predicate);

}
