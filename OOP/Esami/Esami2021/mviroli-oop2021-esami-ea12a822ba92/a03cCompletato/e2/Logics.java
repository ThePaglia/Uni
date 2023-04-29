package a03c.e2;

import java.util.Optional;

public interface Logics {
    
    boolean hit();

    Optional<Boolean> getValue(Pair<Integer, Integer> pos);
}
