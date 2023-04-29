package a02a.sol2;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LogicImpl implements Logic {

    private int size;
    private Set<Pair<Integer,Integer>> available;
    private final Set<Pair<Integer,Integer>> bishops = new HashSet<>();

    public LogicImpl(int size){
        this.size = size;
        this.clear();
    }

    private void clear() {
        this.available = IntStream.range(0, size)
                .mapToObj(i -> i)
                .flatMap(x -> IntStream.range(0, size)
                        .mapToObj(y -> new Pair<>(x,y)))
                .collect(Collectors.toCollection(()->new HashSet<>()));
        this.bishops.clear();
    }


    private boolean inLine(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2){
        return p1.getX() - p1.getY() == p2.getX() - p2.getY() || p1.getX() + p1.getY() == p2.getX() + p2.getY();
    }

    @Override
    public void hit(int x, int y) {
        var bishop = new Pair<>(x, y);
        if (this.isOver() && bishops.contains(bishop)){
            this.clear();
            return;
        }
        if (available.contains(bishop) && !bishops.contains(bishop)){
            bishops.add(bishop);
            this.dropUnavailable(bishop);
        }
    }

    private void dropUnavailable(Pair<Integer, Integer> bishop) {
        var iterator = this.available.iterator();
        while (iterator.hasNext()){
            var position = iterator.next();
            if (!isBishop(position.getX(), position.getY()) && inLine(position, bishop)){
                iterator.remove();
            }
        }
    }

    @Override
    public boolean isBishop(int x, int y) {
        return this.bishops.contains(new Pair<>(x, y));
    }

    @Override
    public boolean isAvailable(int x, int y) {
        return this.available.contains(new Pair<>(x, y));
    }

    private boolean isOver() {
        return this.available.size() == this.bishops.size();
    }
}