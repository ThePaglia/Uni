package pr2016.a06.sol2;

import java.util.*;


public class ModelImpl implements Model {
    
    private final static String EMPTY = "-";
    
    private Optional<Integer> left = Optional.empty();
    private Optional<Integer> right = Optional.empty();
    private final int size;
    
    public ModelImpl(int size){
        this.size = size;
    }
    
    private void checkPosition(int pos) {
        if (pos<0 || pos >= this.size) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void hit(int pos) {
        this.checkPosition(pos);
        if (right.isPresent()) {
        } else if (left.isPresent()) {
            this.right = Optional.of(pos);
        } else {
            this.left = Optional.of(pos);
        }
    }

    @Override
    public boolean isEnabled(int pos) {
        this.checkPosition(pos);
        if (this.left.isPresent() && pos <= this.left.get()) {
            return false;
        }
        if (this.right.isPresent() && pos >= this.right.get()) {
            return false;
        }
        return true;
    }
    
    @Override
    public String text(int pos) {
        return (this.left.isPresent() && this.right.isPresent() && this.isEnabled(pos)) 
                ? String.valueOf(pos-this.left.get())
                : EMPTY;
    }        
}
