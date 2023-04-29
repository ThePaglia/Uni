package a01a.e1;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A factory for various kinds of acceptor. Note that an acceptor defines essentially
 * three aspects:
 * A) if the next element is correct and hence can be accepted
 * B) if conclusion of a sequence is correct and hence the result can be provided
 * C) what is the result
 * 
 */
public interface AcceptorFactory {
	
	/**
	 * @return an acceptor of a sequence of strings such that:
	 * A) any element is accepted
	 * B) any sequence is accepted
	 * C) the result is the number of empty strings (strings with size 0)
	 */
	Acceptor<String,Integer> countEmptyStringsOnAnySequence();

	/**
	 * @return an acceptor of a sequence of int such that:
	 * A) any element is accepted, provided it is greater than previous one
	 * B) any increasing sequence is overall accepted
	 * C) the result is the string "e1:e2:...:en"
	 */
	Acceptor<Integer,String> showAsStringOnlyOnIncreasingSequences();
	
	/**
	 * @return an acceptor of a sequence of int such that:
	 * A) any element is accepted
	 * B) any sequence of 3 elements is overall accepted
	 * C) the result is the sum
	 */
	Acceptor<Integer,Integer> sumElementsOnlyInTriples();
	
	/**
	 * @param <E> is the type of input
	 * @param <O1> is the type of output of a1
	 * @param <O2> is the type of output of a2
	 * @param a1 is the first acceptor
	 * @param a2 is the second acceptor
	 * @return an acceptor returning a pair with the result that a1 would give and the result a2 would give
	 */
	<E,O1,O2> Acceptor<E,Pair<O1,O2>> acceptBoth(Acceptor<E,O1> a1, Acceptor<E,O2> a2);
	
	/**
	 * A general way to build an acceptor, which proceeds step by step maintaining an internal state
	 * of type S.
	 * @param initial, is the initial state
	 * @param stateFun, is a function from input and state to new state (empty if input is not accepted)
	 * @param outputFun, is a function from state to output (empty if output should not be provided)
	 * @return the acceptor
	 */
	<E, O, S> Acceptor<E, O> generalised(S initial, BiFunction<E, S, Optional<S>> stateFun, Function<S,Optional<O>> outputFun);
		
}
