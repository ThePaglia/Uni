package a01a.sol1;

import java.util.List;
import java.util.function.Function;

/**
 * An interface modelling a factory of SubsequenceCombiner.
 * The general idea is that in all cases the input list will be 
 * iterated until a certain condition is satisfied: the identified 
 * subsequence will then be trasformed into an elemento of the output.
 * When the input is over, the current subsequence (if not empty) will
 * be used to generate a new output element.
 * 
 */
public interface SubsequenceCombinerFactory {

	/**
	 * @return a SubsequenceCombiner that turns triplets of integers into their sum
	 * e.g.: e1,e2,e3,e4,e5,e6,e7,e8 --> (e1+e2+e3),(e4+e5+e6),(e7+e8)
	 */
	SubsequenceCombiner<Integer,Integer> tripletsToSum();
	
	/**
	 * @return a SubsequenceCombiner that turns triplets of elements into a list of them
	 * e.g.: e1,e2,e3,e4,e5,e6,e7,e8 --> List(e1+e2+e3),List(e4+e5+e6),List(e7+e8)
	 */
	<X> SubsequenceCombiner<X,List<X>> tripletsToList();
	
	/**
	 * @return a SubsequenceCombiner that turns subsequences of integers ending with a zero
	 * into their size (zero excluded)
	 * e.g.: e1,e2,0,f1,f2,f3,0,g1,g2,g3,g4,g5 --> 2,3,5
	 */
	SubsequenceCombiner<Integer,Integer> countUntilZero();

	/**
	 * @return a generic SubsequenceCombiner that maps one element of the input into one of the ouput
	 * e.g.: e1,e2,e3 --> f(e1),f(e2),f(e3)
	 */
	<X,Y> SubsequenceCombiner<X,Y> singleReplacer(Function<X,Y> function);
	
	/**
	 * @return a SubsequenceCombiner that turns subsequences of integers as soon as their sum
	 * overcomes @threshold into a list of them
	 * for an example, look at its testcase in class Test
	 */
	SubsequenceCombiner<Integer,List<Integer>> cumulateToList(int threshold);

}
