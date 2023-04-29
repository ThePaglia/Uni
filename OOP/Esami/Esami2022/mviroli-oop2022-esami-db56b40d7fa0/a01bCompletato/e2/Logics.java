package a01b.e2;

import java.util.Set;

public interface Logics {
    
    void hit(Pair<Integer, Integer> pos);

    boolean isOver();

    Set<Pair<Integer, Integer>> getBitches();

}
