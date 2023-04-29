package a02a.e2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class LogicsImpl implements Logics{

    private final Map<Pair<Integer, Integer>, Integer> grid = new HashMap<>();
    private Random rnd = new Random();
    private final int size;
    private int count;
    private Direction dir = Direction.UP;
    private Pair<Integer, Integer> currentPos;

    public LogicsImpl(int size) {
        this.size = size;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.grid.put(new Pair<Integer,Integer>(i, j), null);
            }
        }
        
    }

    private static enum Direction {
        UP(-1, 0),
        DOWN(1, 0),
        LEFT(0, -1),
        RIGHT(0, 1);

        private int x;
        private int y;

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    

    @Override
    public Optional<Pair<Pair<Integer, Integer>, Integer>> hit() {

        if (this.count == 0) {
            this.currentPos = new Pair<Integer,Integer>(rnd.nextInt(this.size), rnd.nextInt(this.size));
            this.grid.put(this.currentPos, count); 
            this.count++;
            return Optional.of(new Pair<>(this.currentPos, this.grid.get(currentPos)));
        }      
        Pair<Integer, Integer> nextPos = new Pair<Integer,Integer>(this.currentPos.getX() + dir.x, this.currentPos.getY() + dir.y);
        if (this.grid.containsKey(nextPos) && this.grid.get(nextPos) == null) {
            this.grid.put(nextPos, count); 
            this.count++;
            this.currentPos = nextPos;
            return Optional.of(new Pair<>(this.currentPos, this.grid.get(currentPos)));
        }
        for (var d : Direction.values()) {
            nextPos = new Pair<Integer,Integer>(this.currentPos.getX() + d.x, this.currentPos.getY() + d.y);
            if (this.grid.containsKey(nextPos) && this.grid.get(nextPos) == null) {
                this.grid.put(nextPos, count); 
                this.count++;
                this.currentPos = nextPos;
                this.dir = d;
                return Optional.of(new Pair<>(this.currentPos, this.grid.get(currentPos)));
            }
        }
        return Optional.empty();
    }
}
