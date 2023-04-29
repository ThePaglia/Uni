package a04.e1;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 
 *  An interface for modelling a factory of Either
 *
 */
public interface EitherFactory {
	
	/**
	 * @param <A>
	 * @param <B>
	 * @param b
	 * @return a success result with value b
	 */
	<A,B> Either<A,B> success(B b);
	
	/**
	 * @param <A>
	 * @param <B>
	 * @param b
	 * @return a failure result with value a
	 */
	<A,B> Either<A,B> failure(A a);
	
	/**
	 * @param <A>
	 * @param computation
	 * @return either a success (if computation correctly produces a value) 
	 * or a failure (if computation raises an exception)
	 */
	<A> Either<Exception, A> of(Supplier<A> computation);
	
	/**
	 * @param <A>
	 * @param <B>
	 * @param <C>
	 * @param list
	 * @param function
	 * @return an either: it applies function to all elements of lists; if all results are success it returns a 
	 * success with the list of results; if one fails, it returns that failure.
	 */
	<A,B,C> Either<A, List<C>> traverse(List<B> list, Function<B, Either<A,C>> function);

}
