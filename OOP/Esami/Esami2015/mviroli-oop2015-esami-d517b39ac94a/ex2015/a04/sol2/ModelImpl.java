package ex2015.a04.sol2;

import java.util.*;

public class ModelImpl implements Model {
    
    private int rows;
    private int cols;
    private int remainingAttempts;
    private Set<Pair<Integer,Integer>> positions = new HashSet<>();
    
    public ModelImpl(int rows, int cols, int attempts, int toHit){
        this.rows = rows;
        this.cols = cols;
        this.remainingAttempts = attempts;
        final Random r = new Random();
        while (positions.size() < toHit){
            positions.add(new Pair<>(r.nextInt(rows),r.nextInt(cols)));
        }
    }

    @Override
    public int getRemainingAttempts() {
        return this.remainingAttempts;
    }

    @Override
    public Optional<Boolean> hit(int row, int col) {
        if (this.getRemainingAttempts()==0 || this.positions.isEmpty()){
            return Optional.empty();
        }
        this.remainingAttempts--;
        return Optional.of(positions.remove(new Pair<>(row,col)));
    }

    @Override
    public String getSecretPositions() {
        return this.positions.toString();
    }

    @Override
    public boolean won() {
        return this.positions.isEmpty();
    }
    
}
