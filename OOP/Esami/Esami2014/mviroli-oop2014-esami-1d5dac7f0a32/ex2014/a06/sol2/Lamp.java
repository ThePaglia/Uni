package ex2014.a06.sol2;


/**
 * @author mirko
 * 
 * An interface modelling a Lamp that can fail
 *
 */
public interface Lamp {
	
	
	/**
	 * Switches the lamp on
	 * 
	 * @throws IllegalStateException if it can't turn on because it fails
	 */
	void on() throws IllegalStateException;
	
	/**
	 * Switches the lamp off
	 */
	void off();
	
	/**
	 * @return whether the lamp in on
	 */
	boolean isOn();
	
	/**
	 * an external command to make the lamp fail
	 */
	void fail();

}
