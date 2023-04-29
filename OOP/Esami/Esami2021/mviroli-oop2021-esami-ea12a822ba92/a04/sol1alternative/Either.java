package a04.sol1alternative;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * This interface models the result of a computation which could have failed.
 * It is structured in a failure part or success part: only one of them is available.
 * In case of success, the result is available and of type B -- the failure part is unavailable.
 * In case of failure, the reason of failure is available and of type A -- the success part is unavailable.  
 *
 * @param <A>, the type of failure report
 * @param <B>, the type of success result
 */
public interface Either<A, B> {
	
	/**
	 * @return whether computation failed
	 */
	boolean isFailure();
	
	/**
	 * @return whether computation succeeded
	 */
	boolean isSuccess();
	
	/**
	 * @return the failure report, optionally (if available, otherwise empty)
	 */
	Optional<A> getFailure();
	
	/**
	 * @return the success result, optionally (if available, otherwise empty)
	 */
	Optional<B> getSuccess();
	
	/**
	 * @param other
	 * @return the success result if available, or other otherwise
	 */
	B orElse(B other);
	
	/**
	 * @param <B1>
	 * @param function
	 * @return a new Either, obtained by the receiver simply applying function to the result of computation (if available);
	 * if the receiver was a failure, a failure is returned
	 */
	<B1> Either<A, B1> map(Function<B, B1> function);
	
	/**
	 * @param <B1>
	 * @param function
	 * @return a new Either, obtained applying function to the result of computation (if available) to obtain a whole new Either
	 */
	<B1> Either<A, B1> flatMap(Function<B, Either<A, B1>> function);
		
	/**
	 * @param <A1>
	 * @param predicate
	 * @param failure
	 * @return a new Either, which fails giving error failure if the receiver already failed 
	 * or if the computation result does no pass the predicate, otherwise it gives an Either
	 * similar to the receiver  
	 */
	<A1> Either<A1,B> filterOrElse(Predicate<B> predicate, A1 failure);
	
	/**
	 * @param <C>
	 * @param funFailure
	 * @param funSuccess
	 * @return gets a value from the receiving either, obtained by applying funFailure to the failure report (if available), 
	 * or funSuccess to the computation result (if available)
	 */
	<C> C fold(Function<A,C> funFailure, Function<B,C> funSuccess);
}