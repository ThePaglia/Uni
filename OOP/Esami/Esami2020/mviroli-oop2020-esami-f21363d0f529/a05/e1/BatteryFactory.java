package a05.e1;

/**
 * A Factory for various types of Battery
 *
 */
public interface BatteryFactory {
	
	/**
	 * @return a battery that:
	 * - cannot be recharged
	 * - whose energy drops with the rate of 1.0 per minute
	 * - with no control on possible exceptions.
	 */
	Battery createSimpleBattery();
	
	/**
	 * @return a battery that:
	 * - cannot be recharged
	 * - whose energy drops with the rate of energyPerDurationDrop per minute
	 * - with no control on possible exceptions.
	 * THIS METHOD IS CONSIDERED OPTIONAL IN THIS EXAM
	 */
	Battery createSimpleBatteryByDrop(double energyPerDurationDrop);

	/**
	 * @return a battery that:
	 * - cannot be recharged
	 * - whose energy splits in two at each usage (independent of duration),
	 * - with no control on possible exceptions.
	 */
	Battery createSimpleBatteryWithExponentialDrop();

	/**
	 * @return a battery that:
	 * - cannot be recharged
	 * - whose energy drops with the rate of 1.0 per minute (like the SimpleBattery above) 
	 * - throwing an IllegaleStateException if used when already in use, stopped if not used, used with energy 0.0
	 * (see the tests)
	 */
	Battery createSecureBattery();
	
	/**
	 * @return a battery that:
	 * - the first time recharges to 1.0, then to 0.99, then to 0.98, and so on
	 * - whose energy drops with the rate of 1.0 per minute (like the SimpleBattery above) 
	 * - with no control on possible exceptions.
	 */
	Battery createRechargeableBattery();
	
	/**
	 * @return a battery that:
	 * - the first time recharges to 1.0, then to 0.99, then to 0.98, and so on
	 * - whose energy drops with the rate of 1.0 per minute
	 * - throwing an IllegaleStateException if used when already in use, stop used if not used, used with energy 0.0
	 * (see the tests)
	 * THIS METHOD IS CONSIDERED OPTIONAL IN THIS EXAM
	 */
	Battery createSecureAndRechargeableBattery();

}
