package a02a.e1;

/**
 * An interface modelling a recursive iterator over elements of type X, namely, 
 * a sort of iterator which can be used to get current element, 
 * and which gives a new (recursive) iterator to model advance to next element.
 * It is assumed that a recursive iterator modelling no elements is just null.
 */
public interface RecursiveIterator<X>{
		
	/**
	 * @return the current element of the iterator; it is a getter, hence calling it
	 * multiple subsequent times gives same result
	 */
	X getElement();
	
	/**
	 * advances to next position
	 * @return a new iterator pointing to next element, or null if there's no such
	 */
	RecursiveIterator<X> next();

}
