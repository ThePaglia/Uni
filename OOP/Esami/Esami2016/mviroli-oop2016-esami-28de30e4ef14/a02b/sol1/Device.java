package ex2016.a02b.sol1;

/**
 * An interface modelling a device that may stop working when one tries to switch it on.
 * When not working, it remains off even if one tries to switch it on.
 */
public interface Device {
    
    /**
     * Switches the device on: it may cause the device to stop working forever
     * It has no effect if the device is already switched on
     */
    void on();
    
    /**
     * Switches the device off
     */
    void off();
    
    /**
     * @return whether this device is on
     */
    boolean isOn();
    
    /**
     * @return whether this device is working
     */
    boolean isWorking();
}
