package a05.sol1;

import java.util.function.Function;

/**
 * A Factory for State objects
 */
public interface StateFactory {
	
	/**
	 * @param <S>
	 * @param <A>
	 * @param fun
	 * @return a new state from a function S=>Pair<S,A> (see documentation in State.java)  
	 */
	<S,A> State<S,A> fromFunction(Function<S,Pair<S,A>> fun);
	
	/**
	 * @param <S>
	 * @param <A>
	 * @param <B>
	 * @param state1
	 * @param state2
	 * @return a new state whose update is equivalent to apply first the update of state1
	 * and then the update of state2; the extracted value is that of state2
	 */
	<S,A,B> State<S,B> compose(State<S,A> state1, State<S,B> state2);
	
	/**
	 * @return a state of integers, where each update performs +1,^2,/2, orderly, and the extracted
	 * value is the string representation.
	 * Check the expected behaviour in method Test.testIncSquareHalve.
	 */
	State<Integer, String> incSquareHalve();
	
	enum CounterOp {
		INC, RESET, GET;
	}
	
	/**
	 * @param op
	 * @return a state representing a counter:
	 * - counterOp(INC) creates a state that updates by incrementing a value by one and returning nothing (e.g. null)
	 * - counterOp(RESET) creates a state that updates by resetting the value to 0 and returning nothing (e.g. null)
	 * - counterOp(GET) creates a state that updates by leaving the value unchanged and returning its current value
	 * Check the expected behaviour in method Test.testCounter.
	 */
	State<Integer, Integer> counterOp(CounterOp op);
}
