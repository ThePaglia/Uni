package pr2016.a05.sol1;

import java.util.*;

public abstract class AbstractElevator implements Elevator {
    
    protected final NavigableSet<Integer> pending;
    protected boolean up = true;
    protected int floor = 0;
    
    public AbstractElevator() {
        super();
        this.pending = new TreeSet<>();
    }
    
    @Override
    public int getCurrentFloor() {
        return this.floor;
    }

    @Override
    public boolean isMoving() {
        return !this.pending.isEmpty();
    }

    @Override
    public boolean isMovingUp() {
        return this.isMoving() && this.up;
    }

    @Override
    public boolean isMovingDown() {
        return this.isMoving() && !this.up;    
    }

    @Override
    public void call(int floor) {
        if (this.floor == floor){
            return;
        }
        this.pending.add(floor);
        this.update();
    }

    @Override
    public void moveToNext() {
        if (!this.isMoving()){
            return;
        }
        Integer next = this.next();
        this.pending.remove(this.next());
        this.floor = next;
        this.update();
    }
    
    protected Integer next(){
        return this.isMovingUp() ? this.pending.higher(this.floor) : this.pending.lower(this.floor);
    }
    
    protected abstract void update();
 
    @Override
    public Set<Integer> pendingCalls() {
        return Collections.unmodifiableSet(this.pending);
    }
}
