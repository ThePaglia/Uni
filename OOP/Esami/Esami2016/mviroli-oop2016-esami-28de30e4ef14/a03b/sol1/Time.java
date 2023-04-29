package ex2016.a03b.sol1;

/**
 * An interface modelling a time in a day, in between 0:0:0 and 23:59:59
 */
public interface Time {
    
    /**
     * @return hours, in range (0-23)
     */
    int getHours();
    
    /**
     * @return minutes, in range (0-59)
     */
    int getMinutes();
    
    /**
     * @return seconds, in range (0-59)
     */
    int getSeconds();
    
    /**
     * @return a 8-characters string representing the time, e.g.: "00:00:00" or "23:59:59"
     */
    String getLabel24();
    
    /**
     * @return how many seconds elapsed from midnight; note maximum value is 60*60*24-1
     */
    int getSecondsFromMidnight();
}
