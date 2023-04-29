package a01b.e1;

/**
 * An immutable representation of an event happened at a given discrete time, carrying a value 
 */
public interface Event<X> {
	
	int getTime();
	
	X getValue();
}
