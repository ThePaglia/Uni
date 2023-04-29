package a02a.e2;

import java.util.HashSet;
import java.util.Set;

public class LogicsImpl implements Logics{

    private final Set<Pair<Integer,Integer>> church = new HashSet<>();
    private final Set<Pair<Integer,Integer>> disabled = new HashSet<>();
    private final int size;

    private enum Diagonals {
        UPLEFT(-1, -1), UPRIGHT(-1, 1), DOWNLEFT(1, -1), DOWNRIGHT(1, 1);

        private final int x;
        private final int y;

        private Diagonals(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public LogicsImpl(int size) {
        this.size = size;
    }
    
    private boolean checkBounds(Pair<Integer,Integer> pos) {
        return pos.getX() < this.size && pos.getY() < this.size && pos.getX() >= 0 && pos.getY() >= 0;
    }

    private void setDiagonal(Pair<Integer, Integer> pos) {
        Pair<Integer,Integer> next;
        for (var diag : Diagonals.values()) {
            next = new Pair<Integer,Integer>(pos.getX() + diag.x, pos.getY() + diag.y);
            while (checkBounds(next)) {
                this.disabled.add(next);
                next = new Pair<Integer,Integer>(next.getX() + diag.x, next.getY() + diag.y);
            }
        }
    }

    @Override
    public boolean hit(Pair<Integer, Integer> pos) {
        if (!church.contains(pos)) {
            church.add(pos);
            setDiagonal(pos);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean canDisable() {
        return this.disabled.size() == ((this.size*this.size) - this.church.size());
    }

    @Override
    public boolean isToBeDisabled(Pair<Integer, Integer> pos) {
        return this.disabled.contains(pos);
    }
        
    @Override
    public void restart() {
        church.clear();
        disabled.clear();
    }

}
