package a02b.e1;

/**
 * An interface modelling a cursors over elements of type X, namely, 
 * a sort of iterator which can be used to get current element, 
 * and to move to the next position.
 * It is assumed a cursor surely has at least one element.
 */
public interface Cursor<X>{
		
	/**
	 * @return the current element of the cursor; it is a getter, hence calling it
	 * multiple subsequent times gives same result
	 */
	X getElement(); 
	
	/**
	 * try to advance the cursor to the next position: if this is not possible
	 * because there are no more elements, then calling advance gives no side effect
	 * @return whether it was possibile to advance
	 */
	boolean advance(); // if it can't advance, it gives false and getElement yield same result

}
