package a05.sol1;

import java.util.Iterator;
import java.util.function.Function;

/**
 * An interface modelling updates of a state, of type S, with an operation to derive a new state from previous one,
 * and one to extract a value of type A to observe state. So, this is essentially a couple of functions, S=>S and S=>A,
 * or alternatively, a function S=>Pair<S,A>.
 *
 * @param <S> is the type of state
 * @param <A> is the type of observed value
 */
public interface State<S,A> {
	
	/**
	 * @param s
	 * @return the next state starting from s
	 */
	S nextState(S s);
	
	/**
	 * @param s
	 * @return the value observed from state s
	 */
	A value(S s);
	
	/**
	 * @param <B>
	 * @param fun
	 * @return a new State, updated like the receiver does, but whose value
	 * is processed with function fun before being read
	 */
	<B> State<S,B> map(Function<A,B> fun);
	
	/**
	 * @param s0
	 * @return an iterator that produces all values obtained by iterative update of state,
	 * namely: value(s0), value(nextState(s0)), value(nextState(nextState(s0))),...
	 */
	Iterator<A> iterator(S s0);
}
