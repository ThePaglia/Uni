package ex2016.a03b.sol2;

import java.util.*;
import java.util.stream.*;

public class ModelImpl implements Model {
    
    private final int size;
    private int pos;
    private boolean direction;
    
    
    public ModelImpl(int size){
        this.size = size;
        this.reset();
    }

    @Override
    public void advance() {
        if (direction){
            this.pos++;
            if (this.pos==size-1){
                direction = false;
            }
        } else {
            this.pos--;
            if (this.pos==0){
                direction = true;
            }
        }
    }
    
    private void reset(){
        this.direction = true;
        this.pos = 0;
    }

    @Override
    public boolean hit() {
        return pos==0;
    }

    @Override
    public int getPosition() {
        return pos;
    }
}
