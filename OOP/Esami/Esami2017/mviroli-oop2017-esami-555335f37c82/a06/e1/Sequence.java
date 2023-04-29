package a06.e1;

import java.util.List;
import java.util.stream.*;

/**
 * An immutable, infinite sequence of elements of type X, to be accessed once and sequentially 
 */
public interface Sequence<X> {
	
	/**
	 * @return the next element; when this is returned, the next call will give the successive element without exceptions
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
