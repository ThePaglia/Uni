package ex2014.a06.sol2;

/**
 * @author mirko
 * 
 * A factory interface for two kinds of lamp
 *
 */
public interface LampFactory {
	
	/**
	 * Creates a lamp that when fails it works only for another remainingDuration number of switch-on commands 
	 * 
	 * @param remainingDuration
	 * @return the lamp (initially off)
	 */
	Lamp createLampWithRemainingDuration(int remainingDuration);
	
	/**
	 * Creates a lamp that when fails stops working until the user tried to switch-on it for failingDuration times
	 * 
	 * @param failingDuration
	 * @return the lamp (initially off)
	 */
	Lamp createLampWithTemporaneousFailure(int failingDuration);

}
