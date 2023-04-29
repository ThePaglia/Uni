package ex2015.a04.sol2;

import java.util.*;

public interface Model {
    
    int getRemainingAttempts();
    
    Optional<Boolean> hit(int row, int col);
    
    String getSecretPositions();
    
    boolean won();
}
