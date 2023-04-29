package a03b.sol1;


/**
 * This interface models a so-called "functional lens" (lente), providing
 * the ability to read (get) a sub-element of type A from an element of type S,
 * and to write (set) a sub-element of type A from an element of type S. Writing
 * is "functional" in the sense that it does not lead to side-effect, but rather,
 * it creates a new element where the sub-element is changed.
 * For instance, a lens could be used to "focus" on first element of a list:
 * - the getter takes e.g. (10,20,30,40,50) and returns 10
 * - the setter takes e.g. (10,20,30,40,50) and when setting 0 returns (0,20,30,40,50)
 * so in this case the lens is an abstraction of a getter/copy-setter for the first 
 * element of a list.
 * So, different lenses could be created to analogously read/change parts of various structures. 
 *
 * @param <S> the type of elements to which lenses are applied
 * @param <A> the type of sub-elements
 */
public interface Lens<S,A> {
	
	/**
	 * @param s
	 * @return a certain sub-part of 's'
	 */
	A get(S s);
	
	/**
	 * @param a
	 * @param s
	 * @return a copy of 's', obtained by changing the sub-part to 'a'
	 */
	S set(A a, S s);
}
