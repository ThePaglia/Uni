package a05.sol1;

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
	 * - whose energy drops with the rate of 1.0 per minute
	 * - throwing an IllegaleStateException if used when already in use, stop used if not used, used with energy 0.0
	 * (see the tests)
	 */
	Battery createSecureBattery();
	
	/**
	 * @return a battery that:
	 * - the first time recharges to 1.0, then to 0.99, then to 0.98, and so on
	 * - whose energy drops with the rate of 1.0 per minute
	 * - with no control on possible exceptions.
	 */
	Battery createRechargableBattery();
	
	/**
	 * @return a battery that:
	 * - the first time recharges to 1.0, then to 0.99, then to 0.98, and so on
	 * - whose energy drops with the rate of 1.0 per minute
	 * - throwing an IllegaleStateException if used when already in use, stop used if not used, used with energy 0.0
	 * (see the tests)
	 * THIS METHOD IS CONSIDERED OPTIONAL
	 */
	Battery createSecureAndRechargableBattery();

}
