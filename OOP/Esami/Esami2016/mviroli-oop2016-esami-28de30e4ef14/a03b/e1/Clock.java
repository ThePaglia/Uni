package ex2016.a03b.e1;

/**
 * An interface modelling a clock, showing a time, having a tick advancing time of one second,
 * and allowing to register observers to be notified at given moments in time
 */
public interface Clock {
    
    /**
     * @return current time
     */
    Time getTime();
    
    /**
     * advances time of one second 
     */
    void tick();
    
    /**
     * @param time, the time at which the alarm triggers
     * @param observer, the observer whose method run has to be called at the alarm
     * Keeps track of (time,observer) and notifies the observer when time is reached 
     */
    void registerAlarmObserver(Time time, Runnable observer);
    
    /**
     * @param hours
     * @param observer
     * Notifies the observer after 'hours' hours 
     */
    void registerHoursDeadlineObserver(int hours, Runnable observer);
    
    /**
     * @param minutes
     * @param observer
     * Notifies the observer after 'minutes' minutes 
     */
    void registerMinutesDeadlineObserver(int minutes, Runnable observer);
    
    /**
     * @param seconds
     * @param observer
     * Notifies the observer after 'seconds' seconds 
     */
    void registerSecondsDeadlineObserver(int seconds, Runnable observer);
}
