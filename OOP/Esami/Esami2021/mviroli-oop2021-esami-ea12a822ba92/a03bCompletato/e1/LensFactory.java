package a03b.e1;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * An interface to model several factories of Lenses, with many opportunities of reuse,
 * especially if one considers the idea of "composing lenses"
 *
 */
public interface LensFactory {
	
	/**
	 * @param <E>
	 * @param i
	 * @return a lens over the i-th element of a List<E>
	 */
	<E> Lens<List<E>,E> indexer(int i);
	
	/**
	 * @param <E>
	 * @param i
	 * @param i
	 * @return a lens of i-th row and j-th column of a List<List<E>>
	 */
	<E> Lens<List<List<E>>,E> doubleIndexer(int i, int j);
	
	/**
	 * @param <A>
	 * @param <B>
	 * @return a lens over the first component of a pair
	 */
	<A,B> Lens<Pair<A,B>,A> left();
	
	/**
	 * @param <A>
	 * @param <B>
	 * @return a lens over the second component of a pair
	 */
	<A,B> Lens<Pair<A,B>,B> right();
	
	/**
	 * @param <A>
	 * @param <B>
	 * @param <C>
	 * @param i
	 * @return a lens over the second component of the second component of the i-th element of a List<Pair<A,Pair<B,C>>> 
	 */
	<A,B,C> Lens<List<Pair<A,Pair<B,C>>>,C> rightRightAtPos(int i); 

}
