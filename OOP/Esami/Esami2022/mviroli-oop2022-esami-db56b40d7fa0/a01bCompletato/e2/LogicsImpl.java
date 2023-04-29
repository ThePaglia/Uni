package a01b.e2;

import java.util.HashSet;
import java.util.Set;

public class LogicsImpl implements Logics{

    private static enum Direction {
        UPLEFT(-1, -1), DOWNRIGHT(1, 1),
        UPRIGHT(-1, 1), DOWNLEFT(1, -1);

        private int x;
        private int y;

        private Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    private final int size;
    private Set<Pair<Integer, Integer>> set = new HashSet<>();
    private int count = 0;

    public LogicsImpl(int size) {
        this.size = size;
    }
    
    private boolean checkBound(Pair<Integer, Integer> pos) {
        return pos.getX() >= 0  && pos.getY() >= 0 && pos.getX() < this.size && pos.getY() < this.size;
    }

    @Override
    public void hit(Pair<Integer, Integer> pos) {
        Pair<Integer, Integer> diag;
        count = 0;
        for (var dir : Direction.values()) {
            diag = new Pair<Integer,Integer>(pos.getX() + dir.x, pos.getY() + dir.y);
            if (checkBound(pos)) {
                if (this.set.contains(diag)) {
                    this.set.remove(diag);
                    count++;
                } else {
                    this.set.add(diag);
                }
            }
        }
    }

    @Override
    public boolean isOver() {
        return this.count == 3;
    }

    @Override
    public Set<Pair<Integer, Integer>> getBitches() {
        return this.set;
    }
}
