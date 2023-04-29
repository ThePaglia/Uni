package ex2014.a02b.sol1;

import java.util.*;

public interface TicketDispenser<A> {
	
	/**
	 * @return how many CashDesk this system has (it is the argument passed to the constructor)
	 */
	int getCashDeskSize();
	
	/**
	 * @return the incremental number of the ticket one can take
	 */
	int getNextAvailableTicket();
	
	/**
	 * It gives a ticket to a newly arrived agent
	 * 
	 * @param agent, the agent asking for the ticket
	 * @return the incremental number of the ticket assigned to the agent
	 * @throws a IllegalArgumentException if agent is already waiting or served
	 */
	int giveNextTicketToAgent(A agent);
	
	/**
	 * A free CashDesk is accepting a new agent: the agent holding the ticket
	 * with smallest number is served
	 * 
	 * @param desk, the id of the desk (from 0 to N-1)
	 * @throws a IllegalStateException if the desk is not free, or no agent is waiting
	 */
	void useCashDesk(int desk);
	
	/**
	 * A serving CashDesk is stopped serving its current agent: that agent quits the system
	 * and the desk is again free
	 * 
	 * @param desk, the id of the desk (from 0 to N-1)
	 */
	void releaseCashDesk(int desk);
	
	/**
	 * @param desk, the id of the desk (from 0 to N-1)
	 * @return true if that desk is currently serving an agent
	 */
	boolean isCashDeskServing(int desk);
	
	/**
	 * @param desk, the id of the desk (from 0 to N-1)
	 * @return the agent using this desk
	 * @throws a NoSuchElementException if the desk is currently free
	 */
	A getAgentUsingCashDesk(int desk);
	
	/**
	 * @return the number of the next ticket served
	 */
	int getNextServingTicket();
	
	/**
	 * @return the set of all agent waiting to be served (this should be a defensive copy!)
	 */
	Set<A> allWaiting();
	
	/**
	 * @return the set of all agent that are currently served (this should be a defensive copy!)
	 */
	Set<A> allCurrentlyServed();

}
