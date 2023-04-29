package a05b.e1;

import java.util.Iterator;
import java.util.stream.Stream;

/**
 * An interface to model a factory for various kinds of Iterator<Iterator<E>>, which
 * essentially models a sequence of sequences, where each elements is produced "by-need".
 * 
 * For an iterator of iterators we use the following notation:
 * 
 * {a1,a2,a3}, {b1,b2}, {c1,c2,c3,c4},...
 * 
 * which means that:
 * - the first iterator produced will give elements a1,a2,a3, one-by-one
 * - the second iterator produced will give elements b1,b2, one-by-one
 * - the third iterator produced will give elements c1,c2,c3,c4, one-by-one
 * - and so on
 * 
 * Note that the Iterator<Iterator<E>> produced by this factory all have something in common: 
 * each has a single "sequence" (or stream) inside, and it produces an infinite number of iterators 
 * representing portions of that sequence. For example, they are of kind:
 * 
 * {a1,a2,a3}, {a1,a2}, {a1,a2,a3,a4},...
 * 
 */
public interface IteratorIteratorFactory {
	
	/**
	 * @param <E>
	 * @param e
	 * @return the iterator of iterator giving: {e},{e},{e},{e},...
	 */
	<E> Iterator<Iterator<E>> constantWithSingleton(E e);
	
	/**
	 * @param <E>
	 * @param e
	 * @return the iterator of iterator giving: {e},{e,e},{e,e,e},{e,e,e,e},...
	 * THIS IS OPTIONAL IN THIS EXAM
	 */
	<E> Iterator<Iterator<E>> increasingSizeWithSingleton(E e);
	
	/**
	 * @return the iterator of iterator giving: {0},{0,1},{0,1,4},{0,1,4,9},{0,1,4,9,16},...
	 */
	Iterator<Iterator<Integer>> squares();

	/**
	 * @return the iterator of iterator giving: {0},{0,2},{0,2,4},{0,2,4,6},{0,2,4,6,8},...
	 */
	Iterator<Iterator<Integer>> evens();
}