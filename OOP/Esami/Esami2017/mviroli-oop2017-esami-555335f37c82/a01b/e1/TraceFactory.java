package a01b.e1;

import java.util.function.Supplier;

/**
 * A factory interface for traces
 * Recall that a Supplier<X> (a functional interface) has a method get() that provides you 
 * with elements of type X
 */
public interface TraceFactory {

	/**
	 * @param sdeltaTime: a supplier of positive int values, representing increments of time
	 * @param svalue: a supplier of values
	 * @param size
	 * @return a trace of length size, initial time 0, incrementing time by what sdeltaTime supplies each time, 
	 * and values provided by svalue supplier
	 */
	<X> Trace<X> fromSuppliers(Supplier<Integer> sdeltaTime, Supplier<X> svalue, int size);
	
	/**
	 * @param sdeltaTime: a supplier of positive int values
	 * @param value: a value
	 * @param size
	 * @return a trace of length size, initial time 0, incrementing time by sdeltaTime, 
	 * and values always equal to value
	 */
	<X> Trace<X> constant(Supplier<Integer> sdeltaTime, X value, int size);
	
	/**
	 * @param svalue: a supplier of values
	 * @param size
	 * @return a trace of length size, time 0,1,2,3... and values provided by svalue
	 */
	<X> Trace<X> discrete(Supplier<X> svalue, int size);	
}
