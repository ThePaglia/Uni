package a02a.e2;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class LogicsImpl implements Logics{

    private final int size;
    private final Set<Pair<Integer, Integer>> enemies = new HashSet<>();
    private final Random r = new Random();
    private Pair<Integer, Integer> pawn;
    
    private enum Diagonals {
        TOPLEFT(-1, -1), TOPRIGHT(-1, 1);

        private int x, y;

        private Diagonals(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public LogicsImpl(int size) {
        this.size = size;
        do {
            enemies.add(new Pair<Integer, Integer>(r.nextInt(size - 2), r.nextInt(size)));
        } while (enemies.size() < size);

        pawn = new Pair<Integer, Integer>(size - 1, r.nextInt(size));
    }

    @Override
    public boolean hit() {
        Pair<Integer, Integer> nextPos = new Pair<Integer,Integer>(pawn.getX() - 1 , pawn.getY());
        for (var d : Diagonals.values()) {
            var diag = new Pair<Integer, Integer>(pawn.getX() + d.x, pawn.getY() + d.y);
            if (enemies.contains(diag)) {
                enemies.remove(diag);
                pawn = diag;
                return true;
            }
        }
        if (!enemies.contains(nextPos)) {
            pawn = nextPos;
            return true;
        }
        return false;
    }

    @Override
    public boolean isOver() {
        if (pawn.getX() == 0) {
            return true;
        }
        return false;
    }
    @Override
    public Pair<Integer, Integer> getPawn() {
        return this.pawn;
    }
    @Override
    public Set<Pair<Integer, Integer>> getEnemies() {
        return this.enemies;
    }

    
}
