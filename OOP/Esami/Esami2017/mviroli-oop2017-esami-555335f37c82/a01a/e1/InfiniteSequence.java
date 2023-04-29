package a01a.e1;

import java.util.List;
import java.util.stream.*;

/**
 * An immutable, infinite sequence of elements of type X, to be accessed once and sequentially 
 */
public interface InfiniteSequence<X> {
	
	/**
	 * @return the next element; as this is returned the next call will give the successive element, and
	 * there won't never be exceptions
	 */
	X nextElement();
	
	/**
	 * @param size
	 * @return a list with the next size elements (already implemented via default) 
	 */
	default List<X> nextListOfElements(int size) {
		return Stream.generate(this::nextElement).limit(size).collect(Collectors.toList());
	}
	
}
