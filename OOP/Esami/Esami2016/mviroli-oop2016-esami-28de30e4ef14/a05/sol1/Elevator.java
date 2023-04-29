package pr2016.a05.sol1;

/**
 * An interface modelling an elevator, replying to messages only when
 * waiting at a floor
 */
public interface Elevator {
        
    /**
     * @return the current floor (0,1,2,..)
     */
    int getCurrentFloor();
    
    /**
     * @return whether it will move as doors close
     */
    boolean isMoving();
    
    /**
     * @return whether it will move up as doors close
     */
    boolean isMovingUp();
    
    /**
     * @return whether it will move down as doors close
     */
    boolean isMovingDown();
    
    /**
     * @param floor, is the floor at which somebody directs the elevator to
     * (pressing the button at the floor, or equivalent, inside the elevator)
     */
    void call(int floor);
    
    /**
     * moves the elevator to the next floor where it should stop to, if any
     */
    void moveToNext();
    
    /**
     * @return the set of floors having a pending call
     */
    java.util.Set<Integer> pendingCalls();

}
