package a03c.e2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class LogicsImpl implements Logics{

    private final Map<Pair<Integer, Integer>, Boolean> field = new HashMap<>();
    private final int size;
    private final Random rnd = new Random();
    private final Map<Pair<Integer, Integer>, Directions> asterix = new HashMap<>();

    private enum Directions {
        UP(-1, 0),
        DOWN(1, 0);
        
        int x;
        int y;

        private Directions(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

        public LogicsImpl(int size) {
        this.size = size;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j <size; j++) {
                this.field.put(new Pair<Integer,Integer>(i, j), false);
                if (i == this.size-1) {
                    this.asterix.put(new Pair<Integer,Integer>(i, j), Directions.UP);
                }
            }
        }
        Pair<Integer, Integer> nextPos;
        for (int i = 0; i < size; i++) {
            do {
                nextPos = new Pair<Integer,Integer>(rnd.nextInt(size-2), rnd.nextInt(size));
            } while (this.field.get(nextPos));
            this.field.put(nextPos, true);
        }
    }

    @Override
    public boolean hit() {
        Pair<Integer, Integer> next;
        Directions dir;
        for (var key : asterix.keySet()) {
            next = new Pair<Integer,Integer>(key.getX() + this.asterix.get(key).x, key.getY() + this.asterix.get(key).y);
            dir = this.asterix.get(key);
            if (next.getX() == -1) {
                return false;
            }
            if (this.field.get(next)) {
                dir = Directions.DOWN;
                this.field.put(next, false);
            }
            if (next.getX() == size-1) {
                dir = Directions.UP;
            }            
            //non va per questo perché provo a cambiare key mentre le scorro
            this.asterix.remove(key);
            this.asterix.put(next, dir);

        }
        return true;
    }

    @Override
    public Optional<Boolean> getValue(Pair<Integer, Integer> pos) {
        if (this.asterix.containsKey(pos)) {
            return Optional.of(true);
        } else if (this.field.get(pos)){
            return Optional.of(false);
        }
        return Optional.empty();
    }
}