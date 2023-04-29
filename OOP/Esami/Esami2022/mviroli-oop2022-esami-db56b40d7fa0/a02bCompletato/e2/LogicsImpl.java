package a02b.e2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LogicsImpl implements Logics{

    private final Set<Pair<Integer,Integer>> asterix = new HashSet<>();
    private final Set<Pair<Integer,Integer>> disabled = new HashSet<>();
    private final int size;

    public LogicsImpl(int size) {
        this.size = size;
    }

    @Override
    public boolean hit(Pair<Integer, Integer> pos) {
        if (!this.asterix.contains(pos)) {
            this.asterix.add(pos);
            return true;
        }
        this.asterix.remove(pos);
        return false;
    }

    private boolean checkBounds(Pair<Integer,Integer> pos) {
        return pos.getX() >= 0 && pos.getX() < this.size && pos.getY() >= 0 && pos.getY() < this.size;
    }

    @Override
    public void disableDiagonal() {
        for (var pos : asterix) {            
            Pair<Integer, Integer> next = new Pair<Integer,Integer>(pos.getX() - Math.min(pos.getX(), pos.getY()), pos.getY() - Math.min(pos.getX(), pos.getY()));
            final List<Pair<Integer, Integer>> toDisable = new ArrayList<>();
            int countAs = 0;

            while (checkBounds(next)) {
                if (asterix.contains(next)) {
                    countAs++;
                }
                toDisable.add(next);
                next = new Pair<Integer,Integer>(next.getX() + 1, next.getY() + 1);
            }
            if (countAs == 3) {
                this.disabled.addAll(toDisable);
            }
        }
    }

    @Override
    public boolean checkRestart() {
        return !this.disabled.isEmpty();
    }

    @Override
    public boolean isDisabled(Pair<Integer, Integer> pos) {
        return this.disabled.contains(pos);
    }

    @Override
    public void restart() {
        this.disabled.clear();    
        this.asterix.clear();    
    }
    
}
