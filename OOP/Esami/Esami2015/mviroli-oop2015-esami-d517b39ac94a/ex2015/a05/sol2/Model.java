package ex2015.a05.sol2;

import java.util.*;

public interface Model {
    
    Optional<Pair<Integer,Integer>> hit(int row, int col);
    
    boolean get(int row, int col);
    
    boolean over();
}
