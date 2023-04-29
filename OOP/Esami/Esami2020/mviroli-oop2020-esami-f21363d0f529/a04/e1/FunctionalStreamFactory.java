package a04.e1;

import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public interface FunctionalStreamFactory {
	
	
	/**
	 * @param <E>
	 * @param list with elements (e1,e2,...,en)
	 * @return a stream of elements {e1,e2,...,en,e1,e2,...,en,e1,e2,...,en,...}
	 */
	<E> FunctionalStream<E> fromListRepetitive(List<E> list);
	
	/**
	 * @param <E>
	 * @param initial
	 * @param op
	 * @return a stream of elements {initial, op(initial), op(op(initial)),...}
	 */
	<E> FunctionalStream<E> iterate(E initial, UnaryOperator<E> op);
	
	/**
	 * @param <A>
	 * @param <B>
	 * @param fstream with elements {a1,a2,a3,...}
	 * @param mapper 
	 * @return a stream with elements {mapper(a1),mapper(a2),mapper(a3),...}
	 */
	<A,B> FunctionalStream<B> map(FunctionalStream<A> fstream, Function<A,B> mapper);
}
