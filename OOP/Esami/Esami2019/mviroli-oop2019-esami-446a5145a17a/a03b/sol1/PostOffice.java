package a03b.sol1;

import java.util.List;
import java.util.Optional;

/**
 * An interface that models a Post Office, with a fixed number of "desks" (sportelli), working as follows:
 * - at arrival time, a client get a ticket ('getTicket') specifying an Operation (send or receive a packet)
 * - if next in line (smallest ticket), and a desk is free, the client can 'goToDesk'
 * - when operation is done, the client leaves the desk ('deskReleased')
 * - a client could also quit the PostOffice while waiting ('exitOnWait')
 * 
 * Depending on the implementation, a desk at a given time could decide to serve only a SEND or a RECEIVE
 */

public interface PostOffice {
	
	enum Operation { SEND, RECEIVE }
	
	/**
	 * @return gives a new (incremental) ID to a new client, 0 the first time, then 1,2,3,...
	 * it also registers the fact that this client wants to perform a specific operation
	 */
	int getTicket(Operation operation);
	
	/**
	 * @param desk, is the position of the desk in 0..SIZE-1
	 * @return an empty Optional if the desk is free, a full Optional if the desk is performing an operation
	 */
	Optional<Operation> deskState(int desk);
	
	/**
	 * @return the ordered list of tickets of people waiting
	 */
	List<Integer> peopleWaiting();
	
	/**
	 * The client with smallest ticket goes to a desk
	 * @param ticket
	 * @param desk
	 * @throws IllegalStateException if this is not the next client, or if the desk refuses the operation
	 */
	void goToDesk(int ticket, int desk);
	
	/**
	 * The client leaves the desk, which is now free and ready for next operation
	 * @param desk
	 */
	void deskReleased(int desk);
	
	/**
	 * A waiting client (with ticket) leaves the PostOffice
	 * @param ticket
	 */
	void exitOnWait(int ticket);
}
