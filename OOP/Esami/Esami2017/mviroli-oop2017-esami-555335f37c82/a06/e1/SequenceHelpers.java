package a06.e1;

import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;

/**
 * Utilities for working with sequences, including factories and combinators
 */
public interface SequenceHelpers {
	
	/**
	 * @param x
	 * @return a sequence always giving value x
	 */
	<X> Sequence<X> of(X x);
	
	/**
	 * @param l
	 * @return a sequence cyclically giving all elements in l (for example, if l is [1,2,3]
	 * it gives the sequence representing [1,2,3,1,2,3,1,2,3,1,2,3,...]
	 */
	<X> Sequence<X> cyclic(List<X> l);
	
	/**
	 * @param xs
	 * @return the same result of cyclic, but with a vararg as input (already implemented via default)
	 */
	default <X> Sequence<X> make(X... xs) {
		return cyclic(Arrays.asList(xs));
	}
	
	/**
	 * @param start
	 * @param increment
	 * @return the sequence: start, start+increment, start+increment+increment, ...
	 */
	Sequence<Integer> incrementing(int start, int increment);
	
	/**
	 * @param input
	 * @param op
	 * @return given sequence s0,s1,s2,s3,.. returns sequence s0, op(s0,s1), op(op(s0,s1),s2),.. 
	 */
	<X> Sequence<X> accumulating(Sequence<X> input, BinaryOperator<X> op);
	
	/**
	 * @param input
	 * @return given sequence s0,s1,s2,s3,.. returns sequence <s0,0>,<s1,1>,<s2,2>,.. (where <..> is Pair)
	 */
	<X> Sequence<Pair<X,Integer>> zip(Sequence<X> input);
}
