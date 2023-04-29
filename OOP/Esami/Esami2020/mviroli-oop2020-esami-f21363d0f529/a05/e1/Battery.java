package a05.e1;

/**
 * An interface modelling a battery, with initially 1.0 energy
 */
public interface Battery {
	
	/**
	 * A method by which a client starts using the battery 
	 */
	void startUse();
	
	/**
	 * A method by which a client stops using the battery
	 * @param duration expresses how much time (e.g. in minutes) the battery has been used
	 */
	void stopUse(double duration);
	
	/**
	 * @return the current energy in the battery, as a double in [0.0,1.0]
	 */
	double getEnergy();
	
	/**
	 * Some battery could be recharged, in which case its energy could increase,
	 * but whether this works and how depends on the implementation. 
	 */
	void recharge();
}
