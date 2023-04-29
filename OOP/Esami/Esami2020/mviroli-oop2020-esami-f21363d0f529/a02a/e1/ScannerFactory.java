package a02a.e1;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * An interface modelling a factory of various kinds of Scanner.
 */
public interface ScannerFactory {
	
	/**
	 * takes any sequence and...
	 * @param <X> the type of input's elements
	 * @param <Y> the type of output's elements
	 * @param filter
	 * @param mapper
	 * @return a list of only those elements passing the filter, each transformed by the mapper
	 * e.g. (10,20,30,40) with filter x->x>20 and map x->x*2 gives (60,80)
	 */
	<X,Y> Scanner<X,List<Y>> collect(Predicate<X> filter, Function<X,Y> mapper);
	
	/**
	 * takes any sequence and...
	 * @param <X> the type of input's elements, and of the single, optional output
	 * @param filter
	 * @return the first element found that passes the filter, or empty otherwise
	 * e.g. (10,20,31,41) with filter x->x%2==1 gives Optional.of(31)
	 */
	<X> Scanner<X,Optional<X>> findFirst(Predicate<X> filter);
	
	/**
	 * takes a sequence of integers and...
	 * @return the maximum element of the maximal prefix ordered subsequence, or empty if the list is empty
	 * e.g. (10,20,31,5,60) gives Optional.of(31) -- note the maximal prefix ordered subsequence is (10,20,31)
	 * In italian: "l'ultimo elemento della più lunga sottosequenza iniziale di valori ordinati"...
	 */
	Scanner<Integer,Optional<Integer>> maximalPrefix();
	
	/**
	 * takes a sequence of numbers and...
	 * @return a list of cumulative sums
	 * e.g. (10,20,30,40) --> (10,30,60,100) 
	 * THIS IS OPTIONAL IN THIS EXAM 
	 */
	Scanner<Integer,List<Integer>> cumulativeSum();
	
}
