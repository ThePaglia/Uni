package ex2016.a02a.sol2;

import java.util.*;


public class ModelImpl implements Model {
    
    private static enum Direction { 
        LEFT, RIGHT;
        int delta(){
            return this == RIGHT ? 1 : -1;
        } 
        Direction other(){
            return this == RIGHT ? LEFT : RIGHT;
        }
    }
    
    private final int size;
    private int position;
    private Direction direction;
    private Set<Integer> disabled;
    
    public ModelImpl(int size){
        this.size = size;
        this.reset();
    }
    
    @Override
    public final void reset() {
        this.position = 0;
        this.direction = Direction.RIGHT;
        this.disabled = new HashSet<>();
        this.disabled.add(-1);
        this.disabled.add(this.size);
    }
    
    @Override
    public void move() {
        if (!this.tryAdvance()){
            this.direction = this.direction.other();
            this.tryAdvance();
        } 
    }
    
    private boolean tryAdvance(){
        if (!this.disabled.contains(this.position + this.direction.delta())){
            this.position = this.position + this.direction.delta();
            return true;
        } 
        return false;
    }

    @Override
    public void disable(int position) {
        if (position != this.position){
            this.disabled.add(position);
        }
    }

    @Override
    public int getPosition() {
        return this.position;
    }

    @Override
    public Set<Integer> allDisabled() {
        Set<Integer> set = new HashSet<>(disabled);
        set.remove(-1);
        set.remove(size);
        return Collections.unmodifiableSet(set);
    }
    
    @Override
    public String toString(){
        return this.position + " " + this.direction + " " + this.disabled;
    }
    
}
