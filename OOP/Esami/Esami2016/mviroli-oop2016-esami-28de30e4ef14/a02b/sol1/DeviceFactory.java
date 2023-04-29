package ex2016.a02b.sol1;

/**
 * A factory for various kinds of devices
 */
public interface DeviceFactory {
    
    /**
     * Creates a device which always work
     * @return the created device
     */
    Device createDeviceNeverFailing();
    
    /**
     * Creates a device which stops working if successfully switched on count times
     * @param count
     * @return the created device
     */
    Device createDeviceFailingAtSwitchOn(int count);
    
    /**
     * Creates a luminous device which stops working when it is switched on and at maximum intensity
     * @param maxIntensity, is the maximum intensity of this luminous device
     * @return the created device
     */
    LuminousDevice createLuminousDeviceFailingAtMaxIntensity(int maxIntensity);

}
