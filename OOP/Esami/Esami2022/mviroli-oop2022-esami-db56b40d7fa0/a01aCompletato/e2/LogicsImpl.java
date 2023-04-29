package a01a.e2;

import java.util.HashSet;
import java.util.Set;

public class LogicsImpl implements Logics{

    private final Set<Pair<Integer, Integer>> set = new HashSet<>();

    private enum Directions {
        UPRIGHT(-1, 1), UPLEFT(-1, -1), DOWNRIGHT(1, 1), DOWNLEFT(1, -1);

        int x;
        int y;

        private Directions(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    @Override
    public boolean hit(Pair<Integer, Integer> pos) {
        if (this.set.contains(pos)) {
            this.set.remove(pos);
            return false;
        }
        this.set.add(pos);
        return true;
    }

    @Override
    public boolean isOver(Pair<Integer, Integer> p) {
        if (!this.set.contains(p)) {
            return false;
        }
        for (Directions d : Directions.values()) {
            if ((this.set.contains(new Pair<>(p.getX() + d.x, p.getY() + d.y)) && this.set.contains(new Pair<>(p.getX() + 2 * d.x, p.getY() + 2 * d.y)))
                || (this.set.contains(new Pair<>(p.getX() + d.x, p.getY() + d.y)) && this.set.contains(new Pair<>(p.getX() - d.x, p.getY() - d.y)))) {
                return true;
            }
        }
        return false;
    }
}
