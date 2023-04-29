package a06a.e1;

/**
 * An interface to model a factory for various kinds of SRService
 */
public interface SRServiceFactory {
	
	/**
	 * @return a new service, which always allows a client to access the resource (send or receive)
	 */
	SRService createMaximumAccess();
	
	/**
	 * @return a new service, which always allows just one client to access the service (send or receive)
	 */
	SRService createWithNoConcurrentAccess();
	
	/**
	 * @return a new service, which always allows many clients to receive and at most two to send concurrently
	 */
	SRService createManyReceiveAndMaxTwoSend();
	
}
