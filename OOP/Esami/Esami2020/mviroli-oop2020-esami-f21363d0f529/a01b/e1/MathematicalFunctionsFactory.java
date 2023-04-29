package a01b.e1;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;



public interface MathematicalFunctionsFactory {
	
	/**
	 * @param <A>
	 * @param <B>
	 * @param domainPredicate
	 * @param value
	 * @return a mathematical function whose domain is made by all elements passing domainPredicate:
	 * each of them is mapped to value
	 */
	<A,B> MathematicalFunction<A,B> constant(Predicate<A> domainPredicate, B value);
	
	/**
	 * @param <A>
	 * @param <B>
	 * @param domainPredicate
	 * @return a mathematical function whose domain is made by all elements passing domainPredicate:
	 * each of them is mapped to itself 
	 */
	<A,B> MathematicalFunction<A,A> identity(Predicate<A> domainPredicate);
	
	/**
	 * @param <A>
	 * @param <B>
	 * @param domainPredicate
	 * @param function
	 * @return a mathematical function whose domain is made by all elements passing domainPredicate:
	 * each of them is mapped according to function, which has the known type java.util.function.Function 
	 */
	<A,B> MathematicalFunction<A,B> fromFunction(Predicate<A> domainPredicate, Function<A,B> function);
	
	/**
	 * @param <A>
	 * @param <B>
	 * @param map
	 * @return a mathematical function mapping keys of map into corresponding values
	 * THIS IS OPTIONAL IN THIS EXAM!
	 */
	<A,B> MathematicalFunction<A,B> fromMap(Map<A,B> map);
}
