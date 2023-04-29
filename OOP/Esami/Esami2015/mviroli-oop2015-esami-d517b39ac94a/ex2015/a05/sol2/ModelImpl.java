package ex2015.a05.sol2;

import java.util.*;

public class ModelImpl implements Model {
    
    private int rows;
    private List<Integer> positions;
    
    public ModelImpl(int rows, int cols){
        this.rows = rows;
        this.positions = new ArrayList<>();
        for (int i=0;i<cols;i++){
            this.positions.add(0);
        }
    }

    @Override
    public Optional<Pair<Integer,Integer>> hit(int row, int col) {
        int v = this.positions.get(col);
        if (v == row && row != this.rows-1){
            v = v + (row == 0 ? 2 : 1);
            this.positions.set(col,v);
            return Optional.of(new Pair<>(v,col));
        }
        return Optional.empty();
    }

    @Override
    public boolean over() {
        return this.positions.stream().allMatch(x -> x == this.rows-1);
    }

    @Override
    public boolean get(int row, int col) {
        return this.positions.get(col)==row;
    }
    
}
