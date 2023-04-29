package a01a.sol1;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import aula.ex1.InfiniteSequence;

/**
 * Utilities for working with infinite sequences, including factories, combinators, and selectors
 */
public interface InfiniteSequenceOps {
	
	/**
	 * @param x
	 * @return an infinite sequence always giving value x
	 */
	<X> InfiniteSequence<X> ofValue(X x);
	
	/**
	 * @param l
	 * @return an infinite sequence iteratively giving all elements in l (for example, if l is [1,2,3]
	 * it gives the infinite sequence representing [1,2,3,1,2,3,1,2,3,1,2,3,...]
	 */
	<X> InfiniteSequence<X> ofValues(List<X> l);
	
	/**
	 * @param xs
	 * @return the same result of ofValues, but with a vararg as input (already implemented via default)
	 */
	default <X> InfiniteSequence<X> make(X... xs) {
		return ofValues(Arrays.asList(xs));
	}
	
	/**
	 * Breaks an input sequence in intervals, and gives the average value of each
	 * 
	 * @param iseq: an infinite sequence of doubles in input 
	 * @param intervalSize: length for intervals
	 * @return a new infinite sequence of average values for each interval taken from iseq
	 */
	InfiniteSequence<Double> averageOnInterval(InfiniteSequence<Double> iseq, int intervalSize);
	
	/**
	 *  Breaks an input sequence in intervals, and gives the last value of each
	 *
	 * @param iseq: an infinite sequence in input 
	 * @param intervalSize: length for intervals
	 * @return a new infinite sequence of the last values of each interval taken from iseq
	 */
	<X> InfiniteSequence<X> oneEachInterval(InfiniteSequence<X> iseq, int intervalSize);
	
	/**
	 * @param iseq: an infinite sequence in input
	 * @return a new infinite sequence of booleans representing whether elements of iseq are equal two-by-two
	 */
	<X> InfiniteSequence<Boolean> equalsTwoByTwo(InfiniteSequence<X> iseq);
	
	/**
	 * @param isx: an infinite sequence in input
	 * @param isy: another infinite sequence in input
	 * @return a new infinite sequence of booleans representing whether elements of isx and isy are pointwise equal
	 */
	<X,Y extends X> InfiniteSequence<Boolean> equalsOnEachElement(InfiniteSequence<X> isx, InfiniteSequence<Y> isy);
	
	/**
	 * @param iseq: an infinite sequence in input
	 * @return an iterator over iseq
	 */
	<X> Iterator<X> toIterator(InfiniteSequence<X> iseq);
}
