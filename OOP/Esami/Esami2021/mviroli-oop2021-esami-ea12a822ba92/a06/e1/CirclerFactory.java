package a06.e1;

/**
 * An interface to model a factory of Circler.
 * In the following descriptions, assume the source has elements E1,E2,...,En
 */
public interface CirclerFactory {
	
	/**
	 * @param <T>
	 * @return a new circler that keeps iterating the source from left to right,
	 * doing multiple passes, hence yielding: 
	 * E1,E2,...,En,E1,E2,...,En,E1,...
	 */
	<T> Circler<T> leftToRight(); 
	
	/**
	 * @param <T>
	 * @return a new circler that iterates the source from left to right, then in the
	 * opposite way, then in the opposite way, and so on, hence yielding: 
	 * E1,E2,...,En,En,...,E2,E1,E1,E2,...,En,En,...
	 */
	<T> Circler<T> alternate();
	
	/**
	 * @param <T>
	 * @return a new circler that iterates the source from left to right, and then stays
	 * on last element, hence yielding: 
	 * E1,E2,...,En,En,En,En,En,En,...
	 */
	<T> Circler<T> stayToLast();
	
	/**
	 * @param <T>
	 * @return a new circler that behaves like the one created by leftToRight(), but skipping
	 * one element each time, that is, if the one created by leftToRight() would give
	 * A1,A2,A3,A4,A5,...., this one gives: A1,A3,A5,A7,...  
	 */
	<T> Circler<T> leftToRightSkipOne();
	
	/**
	 * @param <T>
	 * @return a new circler that behaves like the one created by alternate(), but skipping
	 * one element each time, that is, if the one created by alternate() would give
	 * A1,A2,A3,A4,A5,...., this one gives: A1,A3,A5,A7,...
	 * THIS IS OPTIONAL IN THIS EXAM!  
	 */
	<T> Circler<T> alternateSkipOne();
	
	/**
	 * @param <T>
	 * @return a new circler that behaves like the on create by stayToLast(), but skipping
	 * one element each time, that is, if the one created by stayToLast() would give
	 * A1,A2,A3,A4,A5,...., this one gives: A1,A3,A5,A7,...  
	 * THIS IS OPTIONAL IN THIS EXAM! 
	 */
	<T> Circler<T> stayToLastSkipOne();
}
