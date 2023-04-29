package a02a.sol2;

import java.util.*;

public interface Logics{
    
    void select(int position);
    
    int getSelected();
    
    List<Integer> getNumbers();
    
    void left();
    
    void right();
    
    boolean isDone();
}
