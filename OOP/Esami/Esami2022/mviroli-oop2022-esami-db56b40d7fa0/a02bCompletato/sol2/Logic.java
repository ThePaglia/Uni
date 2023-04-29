package a02b.sol2;

import java.util.Optional;
import java.util.Set;

public interface Logic {
    
    boolean hit(int x, int y);

    Optional<Set<Pair<Integer,Integer>>> checkThree();

    void init();
}
