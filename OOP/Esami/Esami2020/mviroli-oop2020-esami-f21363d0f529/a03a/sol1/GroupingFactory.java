package a03a.sol1;

import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author mirko
 * An interface modelling a factory for several groupings
 */
public interface GroupingFactory {
	
	/**
	 * @param <G>
	 * @param <V>
	 * @param values indicates for each value what is the group it belongs to 
	 * @return a grouping obtained from values
	 */
	<G,V> Grouping<G,V> fromPairs(Iterable<Pair<G,V>> values);
	
	/**
	 * @param <V>, the type of values and groups
	 * @param values indicates the domain of grouping
	 * @return a grouping where each value v belongs to a group called v and including only v 
	 */
	<V> Grouping<V,V> singletons(Set<V> values);
	
	/**
	 * @param <V>, the type of values and groups
	 * @param values indicates the domain of grouping
	 * @param sameGroup, a predicate to state if values v1 and v2 belong to the same group
	 * @param champion, a predicate to state if a value v is also the representative of its group 
	 * @return a grouping based on all the input information
	 */
	<V> Grouping<V,V> withChampion(Set<V> values, BiPredicate<V,V> sameGroup, Predicate<V> champion);
	
	/**
	 * @param <G>
	 * @param <V>
	 * @param values indicates the domain of grouping
	 * @param mapper is a function taking a value and providing its group
	 * @return a grouping obtained applying the mapper to values in the domain
	 * THIS IS OPTIONAL IN THIS EXAM!
	 */
	<G,V> Grouping<G,V> fromFunction(Set<V> values, Function<V,G> mapper);
}
