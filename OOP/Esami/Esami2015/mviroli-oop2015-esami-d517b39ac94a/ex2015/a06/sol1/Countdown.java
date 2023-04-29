package ex2015.a06.sol1;

/**
 * An interface modelling a counter, from an initial value back to a final value.
 * When the final value is reached, it can no longer be decreased, otherwise an
 * exception is thrown.
 */
public interface Countdown {

    /**
     * Decreases the current value of the counter
     *  @throws IllegalStateException if cannot decrease because the counter is over
     */
    void decrease(); // can throw a IllegalStateException
    
    /**
     * @return the current value of the counter
     */
    int getValue();
    
    /**
     * @return whether the counter is currently over
     */
    boolean isOver(); 
    
}
