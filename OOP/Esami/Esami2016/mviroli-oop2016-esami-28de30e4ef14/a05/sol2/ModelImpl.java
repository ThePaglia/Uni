package pr2016.a05.sol2;

import java.util.*;


public class ModelImpl implements Model {
    
    private static enum Direction { 
        LEFT, RIGHT;
        int delta(){
            return this == RIGHT ? 1 : -1;
        } 
    }
    
    private final int size;
    private int position = 0;
    private Direction direction = Direction.RIGHT;
    
    public ModelImpl(int size){
        this.size = size;
    }
        
    @Override
    public void move() {
        this.position = this.position + this.direction.delta();
        if (this.position < 0){
            this.position = 0;
        }
        if (this.position == this.size){
            this.position = this.size-1;
        }
    }
    
    @Override
    public int getPosition() {
        return this.position;
    }
    
    @Override
    public void direction(boolean right) {
        this.direction = right ? Direction.RIGHT : Direction.LEFT;
    }
    
}
