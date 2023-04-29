package a02a.e2;

import java.util.Set;

public interface Logics {

    boolean hit(Pair<Integer,Integer> pos);

    boolean isToBeDisabled(Pair<Integer, Integer> pos);

    boolean canDisable();

    void restart();
}
