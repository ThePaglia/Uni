package a02a.e2;

import java.util.Set;

public interface Logics {
    boolean hit();

    boolean isOver();

    Pair<Integer, Integer> getPawn();

    Set<Pair<Integer, Integer>> getEnemies();
}
