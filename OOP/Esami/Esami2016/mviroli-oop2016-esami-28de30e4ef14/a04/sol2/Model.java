package ex2016.a04.sol2;

import java.util.List;

public interface Model {
    
    List<String> choices();
    
    void choose(String s);
    
    boolean hitLeft();
    
    boolean hitRight();
    
    boolean shouldQuit();
    
}
