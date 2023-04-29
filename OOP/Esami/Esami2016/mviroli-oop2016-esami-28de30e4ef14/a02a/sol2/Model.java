package ex2016.a02a.sol2;

import java.util.Set;

public interface Model {
    
    void move();
    
    void disable(int position);
    
    void reset();
    
    int getPosition();
    
    Set<Integer> allDisabled();
}
