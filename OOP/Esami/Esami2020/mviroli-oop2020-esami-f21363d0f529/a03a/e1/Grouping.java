package a03a.e1;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * An interface modelling a strategy to take values and group them.
 * This actually forms a so-called "partition": each value belongs
 * to a single group.
 * For instance, the domain could be the set [0..99], and we could group 
 * them into two groups (with type boolean) even numbers (true), and odd 
 * numbers (false). 
 *  
 * @param <G>, the type of groups
 * @param <V>, the type of values
 */
public interface Grouping<G,V> {
	
	/**
	 * @param group
	 * @return the set of values belonging to 'group'
	 */
	Set<V> getValuesOfGroup(G group);
	
	/**
	 * @return the set of groups
	 */
	Set<G> getGroups();
	
	/**
	 * @param data
	 * @return the group to which the value belongs, if it exists (hence, with an optional)
	 */
	Optional<G> getGroupOf(V data);
	
	/**
	 * @return a map from groups to the corresponding set of values
	 */
	Map<G,Set<V>> asMap();
	
	/**
	 * @param initial1
	 * @param initial2
	 * @param result
	 * @return a new grouping, obtained from this, by joining group initial1 and initial2 into result
	 * THIS IS OPTIONAL IN THIS EXAM!
	 */
	Grouping<G,V> combineGroups(G initial1, G initial2, G result);

}
