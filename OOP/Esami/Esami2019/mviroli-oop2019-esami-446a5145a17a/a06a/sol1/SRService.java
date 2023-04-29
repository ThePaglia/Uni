package a06a.sol1;

/**
 * An interface that models a service to allow people to SEND or RECEIVE packets (both operation can take some time). 
 * Depending on the implementation, there may be constraints on the possibility of many people concurrently accessing 
 * the service. 
 * This service uses incremental numeric IDs to identify clients: clients requests the operation and then exit.
 */
public interface SRService {
	
	/**
	 * @return gives a new (incremental) ID to a new client, 0 the first time, then 1,2,3,...
	 */
	int enter();
	
	/**
	 * client with 'id' asks to receive
	 * @param id
	 * @throws IllegalStateException if this client is not currently waiting, or is not allowed to receive
	 */
	void goReceive(int id);
	
	/**
	 * client with 'id' asks to send
	 * @param id
	 * @throws IllegalStateException if this client is not currently waiting, or is not allowed to send
	 */
	void goSend(int id);
		
	/**
	 * client with 'id' asks to exit
	 * @param id
	 * @throws IllegalStateException if this client is not currently sending or receiving
	 */
	void exit(int id);
}
