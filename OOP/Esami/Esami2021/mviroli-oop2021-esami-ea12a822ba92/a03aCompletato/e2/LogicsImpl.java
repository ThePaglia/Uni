package a03a.e2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class LogicsImpl implements Logics{

    private final int size;
    private Optional<Pair<Integer, Integer>> lastHit = Optional.empty();
    private List<Pair<Integer, Integer>> selected = new ArrayList<>();

    public LogicsImpl(int size) {
        this.size = size;
    }

    @Override
    public boolean hit(Pair<Integer, Integer> pos) {
        if (lastHit.isEmpty() || (pos.getX() > this.lastHit.get().getX() && pos.getY() > this.lastHit.get().getY())) {
            lastHit = Optional.of(pos);
            return true;
        }
        return false;
    }

    private Iterable<Integer> range(int x1, int x2) {
        return () -> IntStream.rangeClosed(x1, x2).iterator();
    }

    public void updateSelected(Pair<Integer, Integer> pos) {

        Pair<Integer, Integer> oppositePos = new Pair<Integer,Integer>(this.size - 1 - pos.getX(), this.size - 1 - pos.getY());

        for (var next : this.range(pos.getX(), oppositePos.getX())) {
            this.selected.add(new Pair<Integer,Integer>(next, pos.getY()));
            this.selected.add(new Pair<Integer,Integer>(next, oppositePos.getY()));
        }
        for (var next : this.range(pos.getY(), oppositePos.getY())) {
            this.selected.add(new Pair<Integer,Integer>(pos.getX(), next));
            this.selected.add(new Pair<Integer,Integer>(oppositePos.getX(), next));
        }
    }

    @Override
    public boolean isSelected(Pair<Integer, Integer> pos) {
        return this.selected.contains(pos);
    }

    @Override
    public boolean isOver() {
        return this.lastHit.get().getX() >= this.size/2-1 || this.lastHit.get().getY() >= this.size/2-1;
    }
}
