package a01b.e1;

import java.util.List;
import java.util.function.Function;

/**
 * An interface modelling a factory of Flattener.
 * A possible way of modelling all produced flatteners is as follow:
 * they all iterate all inner lists in input, modifying a List<O> state each time,
 * until at some points (0, 1 or more, but surely at the end) that state is moved 
 * to the output.
 */
public interface FlattenerFactory {
	
	/**
	 * @return a Flattener that turns each inner list in its sum
	 * e.g.: [[e1,e2,e3],[e4,e5,e6],[e7,e8]] --> (e1+e2+e3),(e4+e5+e6),(e7+e8)
	 */
	Flattener<Integer,Integer> sumEach();

	/**
	 * @return a generic Flattener that appends all inner lists
	 * e.g.: [[e1,e2,e3],[e4,e5,e6],[e7,e8]] --> [e1,e2,e3,e4,e5,e6,e7,e8]
	 */
	<X> Flattener<X,X> flattenAll();

	/**
	 * @return a Flattener that concats all strings of pairs of inner lists
	 * e.g.: [[s1,s2,s3],[s4],[s5,s6],[s7],[s8]] --> [s1+s2+s3+s4, s5+s6+s7, s8]
	 */
	Flattener<String, String> concatPairs();

	/**
	 * @return a generic Flattener that turns each list into a single element of the output
	 * e.g.: [[s1,s2,s3],[s4],[s5,s6]] --> [f([s1,s2,s3]), f([s4]), f([s5,s6])]
	 */
	<I,O> Flattener<I,O> each(Function<List<I>,O> mapper);

	/**
	 * @return a  Flattener that implements algebraic sum of vectors of integers
	 * You can assume all inner lists have same size
	 * e.g.: [[s1,s2],[s3,s4],[s5,s6],[s7,s8]] --> [s1+s3+s5+s7, s2+s4+s6+s8]
	 */
	Flattener<Integer,Integer> sumVectors();
}
