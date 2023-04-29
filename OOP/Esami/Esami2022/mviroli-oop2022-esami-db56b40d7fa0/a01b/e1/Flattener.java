package a01b.e1;

import java.util.List;

/**
 * An interface modelling a transformer of lists of lists (of I) to lists (of O),
 * producing no side-effect.
 */
public interface Flattener<I,O>{
		
	/**
	 * @param list
	 * @return a new list obtained transforming the input, without side-effects
	 */
	List<O> flatten(List<List<I>> list);

}
