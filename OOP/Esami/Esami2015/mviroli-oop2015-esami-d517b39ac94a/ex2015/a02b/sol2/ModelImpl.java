package ex2015.a02b.sol2;

import java.util.*;

public class ModelImpl implements Model {

    private List<Integer> values = new ArrayList<>();
    private List<Integer> drawn = new ArrayList<>();
    private int countUpRow = 0;

    public ModelImpl() {
        final Random random = new Random();
        while (this.values.size() < this.getNumCells()) {
            int i = random.nextInt(this.getMaxNum()) + 1;
            if (!this.values.contains(i)) {
                this.values.add(i);
            }
        }
    }

    @Override
    public List<Integer> getValues() {
        return this.values;
    }

    @Override
    public Result numberDrawn(int i) {
        if (this.drawn.contains(i)) {
            throw new IllegalStateException();
        }
        if (!this.values.contains(i)) {
            throw new IllegalArgumentException();
        }
        this.drawn.add(i);
        if (this.values.indexOf(i) < this.getNumCells() / 2) {
            this.countUpRow++;
            if (this.countUpRow == 2) {
                return Result.AMBO;
            }
        } else {
            if (this.drawn.size() - this.countUpRow == 2) {
                return Result.AMBO;
            }
        }
        if (this.drawn.size() == this.getNumCells()) {
            return Result.TOMBOLINA;
        }
        return Result.NO;
    }

    @Override
    public int getNumCells() {
        return 10;
    }

    @Override
    public int getMaxNum() {
        return 20;
    }

}
