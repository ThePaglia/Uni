package a03a.e1;

/**
 * An interface to model a factory for various kinds of RWController
 */
public interface RWControllersFactory {
	
	/**
	 * @return a new controller, which always allows a client to access the resource (read or write)
	 */
	RWController createPermissive();
	
	/**
	 * @return a new controller, which always allows a client to read, but never to write
	 */
	RWController createOnlyRead();
	
	/**
	 * @return a new controller, which always allows just one client to access the resource (read or write)
	 */
	RWController createMutualExclusion();
	
	/**
	 * @return a new controller, which always allows many clients to read or just one to write
	 * THIS IS OPTIONAL IN THIS EXAM!
	 */
	RWController createManyReadersOrOneWriter();

}
