package a03b.sol1;

import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * An interface modelling a factory of various kinds of equivalence relations
 *
 */
public interface EquivalenceFactory {
	
	/**
	 * @param <X>
	 * @param partition
	 * @return the relation induced by the given partition, as discussed in Equivalence's javadoc
	 */
	<X> Equivalence<X> fromPartition(Set<Set<X>> partition);
	
	/**
	 * @param <X>
	 * @param domain
	 * @param predicate
	 * @return the equivalence such that x1 and x2 are equivalent if both in the 'domain' and also in the 'predicate' relation
	 */
	<X> Equivalence<X> fromPredicate(Set<X> domain, BiPredicate<X,X> predicate);
	
	/**
	 * @param <X>
	 * @param pairs
	 * @return the equivalence such that x1 and x2 are equivalent if the pair [x1,x2] is in pairs
	 */
	<X> Equivalence<X> fromPairs(Set<Pair<X,X>> pairs);
	
	/**
	 * @param <X>
	 * @param <Y>
	 * @param domain
	 * @param function
	 * @return the equivalence such that x1 and x2 are equivalent if both in domain and function(x1)=function(x2)
	 * THIS IS OPTIONAL IN THIS EXAM
	 */
	<X,Y> Equivalence<X> fromFunction(Set<X> domain, Function<X,Y> function);
}
