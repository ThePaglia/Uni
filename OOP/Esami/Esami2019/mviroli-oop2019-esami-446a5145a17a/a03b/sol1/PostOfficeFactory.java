package a03b.sol1;

/**
 * An interface to model a factory for various kinds of PostOffice
 */
public interface PostOfficeFactory {
	
	/**
	 * @param desksSize is the number of desks
	 * @return a new PostOffice, where desks always accept both operations
	 */
	PostOffice createFlexible(int desksSize);
	
	/**
	 * @param desksSize is the number of desks
	 * @return a new PostOffice, where a desk serves each time a different operation (if it served a SEND,
	 * the next time it will serve a RECEIVE, then again a SERVE, and so on) 
	 * THIS IS OPTIONAL IN THIS EXAM!
	 */
	PostOffice createAlternating(int desksSize);

}
