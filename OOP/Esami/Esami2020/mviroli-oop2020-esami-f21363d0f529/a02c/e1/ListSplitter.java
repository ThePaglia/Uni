package a02c.e1;

import java.util.List;

/**
 * An interface modelling a transformation of a list into a list of lists,
 * obtainined by splitting the input list in parts, as the concrete implementation will dictate.
 * 
 * @param <X>
 */
public interface ListSplitter<X>{
		
	/**
	 * @param list
	 * @return the input list broken in pieces, as a list of lists
	 */
	List<List<X>> split(List<X> list);

}
