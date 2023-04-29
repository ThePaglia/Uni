package ex2015.a03b.sol2;

import java.util.*;

public interface Model {
    
    List<String> getCurrentLines();
    
    boolean hasNext();
    
    boolean hasPrev();
    
    void next();
    
    void prev();

}
