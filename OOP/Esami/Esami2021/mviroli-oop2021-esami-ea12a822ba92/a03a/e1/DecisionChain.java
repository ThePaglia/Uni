package a03a.e1;

import java.util.Optional;

/**
 * An interface to model an object that takes an input 'a'
 * and should decide (based on an internal logic) if it
 * should return a result 'b', or pass the decision on what
 * to return to a new DecisionChain.
 *
 * @param <A> type of input
 * @param <B> type of output
 */
public interface DecisionChain<A,B> {
	
	/**
	 * @param a is the input
	 * @return optionally the result to be associated to 'a', or empty if this object cannot decide
	 */
	Optional<B> result(A a);
	
	/**
	 * This method should be called on input 'a' only if method 'result' gave an empty optional
	 * 
	 * @param a is the input
	 * @return the new DecisionChain that should decide what to return 
	 */
	DecisionChain<A,B> next(A a);
	
	/**
	 * A gift for the student: a (template) method that gives the final result, either obtained
	 * directly by this object, or by recursive delegation to the new decider.
	 * Implementations of this interface should NOT override this method.
	 * 
	 * @param a
	 * @return the final result of decision
	 */
	default B finalResult(A a) {
		return this.result(a).orElseGet(()->this.next(a).finalResult(a));
	}
}
