package a01b.e1;

import java.util.Optional;
import java.util.Set;

/**
 * An interface modelling a function from A to B
 * It is called "mathematical" just because the domain A could be conceptually infinite,
 * otherwise it would be more or less just a usual Map: in fact this interface is somewhat
 * that of a standard Map. Note that a function has a domain that is not the entire type A,
 * for instance, A could be Integer, and the domain of a function be only the positive Integer.
 *
 * @param <A> the domain
 * @param <B> the codomain
 */
public interface MathematicalFunction<A,B> {
	
	/**
	 * @param a
	 * @return the result of applying the function to argument a, as an empty optional if a is not in the domain 
	 */
	Optional<B> apply(A a);
	
	/**
	 * @param a
	 * @return whether a is in the domain of this function (see method apply above)
	 */
	boolean inDomain(A a);

	/**
	 * @param <C>
	 * @param f
	 * @return a new function, essentially the function mapping an "a of A" into a "c of C"
	 * by first applying the function this, and then the function f
	 */
	<C> MathematicalFunction<A,C> composeWith(MathematicalFunction<B,C> f); 
	
	/**
	 * @param domainValue
	 * @param codomainValue
	 * @return a new function, very similar to this, with the only difference that it also maps
	 * domainValue to codomainValue (codomainValue could be already in domain of this or not)
	 */
	MathematicalFunction<A,B> withUpdatedValue(A domainValue, B codomainValue);
	
	/**
	 * @param subDomain
	 * @return a new function, very similar to this, with the only difference that its
	 * domain is smaller, it includes only the elements that also belong to subDomain
	 * THIS IS OPTIONAL IN THIS EXAM!
	 */
	MathematicalFunction<A,B> restrict(Set<A> subDomain);

}
