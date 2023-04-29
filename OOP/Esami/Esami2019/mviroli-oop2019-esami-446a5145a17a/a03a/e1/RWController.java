package a03a.e1;

/**
 * An interface that models the controller of a computational resource (e.g. a file),
 * that could be accessed either in READ or WRITE mode. Depending on the implementation,
 * there may be constraints on the possibility of many concurrent accesses. This controller
 * uses incremental numeric IDs to identify clients: clients use the resource ones and then exit.
 */
public interface RWController {
	
	/**
	 * @return gives a new (incremental) ID to a new client, 0 the first time, then 1,2,3,...
	 */
	int enter();
	
	/**
	 * client with 'id' asks to write
	 * @param id
	 * @throws IllegalStateException if this client is not currently waiting, or is not allowed to read
	 */
	void goRead(int id);
	
	/**
	 * client with 'id' asks to write
	 * @param id
	 * @throws IllegalStateException if this client is not currently waiting, or is not allowed to write
	 */
	void goWrite(int id);
		
	/**
	 * client with 'id' asks to exit
	 * @param id
	 * @throws IllegalStateException if this client is not currently reading or writing
	 */
	void exit(int id);
}
