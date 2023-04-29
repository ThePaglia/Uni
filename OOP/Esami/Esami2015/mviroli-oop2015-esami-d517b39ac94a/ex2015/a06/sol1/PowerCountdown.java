package ex2015.a06.sol1;

/**
 * An interface modelling a counter with additional features. 
 * It can be reset at any time, and the velocity of decrease can be augmented.
 */
public interface PowerCountdown extends Countdown {
    
    /**
     * Used to reset the counter at its initial conditions (the same we had at construction time)
     */
    void reset();
    
    /**
     * Increases the speed of decrease, depending of implementation details
     */
    void speedup();

}
