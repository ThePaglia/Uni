package ex2016.a02b.e1;

/**
 * An interface for a kind of device with an intensity that can be increased or decreased
 * It is assumed that intensity should always be in an interval [0,N] where N is implementation-dependent
 */
public interface Luminous {
    
    /**
     * Decreases intensity of one 
     */
    void dim();
    
    /**
     * Increases intensity of one 
     */
    void brighten();
    
    /**
     * @return current value of intensity
     */
    int getIntesity();
}
