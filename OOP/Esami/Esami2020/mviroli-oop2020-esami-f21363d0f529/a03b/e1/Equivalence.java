package a03b.e1;

import java.util.Set;

/**
 * An interface modelling an equivalence relation over a domain of values.
 * An equivalence relation essentially partitions the domain in subsets S1,..,S2,
 * also called "equivalence sets".
 * It is said that two elements are in the equivalence relation if they belong
 * to the same subset Si.
 * For instance, for numbers in [0..99], the binary relation "having the same number of units"
 * is an equivalence relation, partition the domain into the 10 equivalence sets
 * {0,10,20,30,40,50,60,70,80,90},{1,11,21,31,41,51,61,71,81,91},{2,12,22,...},...,{9,19,29,..}
 *
 * @param <X>
 */
public interface Equivalence<X> {
	
	/**
	 * @param x1
	 * @param x2
	 * @return if x1 and x2 are in the equivalence relation
	 */
	boolean areEquivalent(X x1, X x2);
	
	/**
	 * @return the set of all values
	 */
	Set<X> domain();
	
	/**
	 * @param x
	 * @return the set of elements equivalent to x
	 */
	Set<X> equivalenceSet(X x);

	/**
	 * @return the set of all equivalence sets, namely, the partition
	 */
	Set<Set<X>> partition();
	
	/**
	 * @param eq
	 * @return true, if the partition of 'this' is "finer" (italian "più fine") of the one in equivalence
	 * Namely, if any equivalence set in 'this' is included in an equivalence set in 'eq'
	 * THIS IS OPTIONAL IN THIS EXAM 
	 */
	boolean smallerThan(Equivalence<X> eq);
		
}
