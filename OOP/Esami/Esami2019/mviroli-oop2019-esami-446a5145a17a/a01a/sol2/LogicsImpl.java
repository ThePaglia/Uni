package a01a.sol2;

import java.util.*;
import java.util.stream.IntStream;

public class LogicsImpl implements Logics {
    
	private final static int FAILURES = 5;
    private Set<Pair<Integer,Integer>> hit = new HashSet<>();
    private int boatRow;
    private int boatLeftCol; 
    private int boatSize;
    private int failures = 0;
    
    public LogicsImpl(int gridSize, int boatSize){
    	this.boatSize = boatSize;
        final Random r = new Random();
        this.boatRow = r.nextInt(gridSize);
        this.boatLeftCol = r.nextInt(gridSize-boatSize+1);
        System.out.println("x = "+this.boatLeftCol+" y = "+this.boatRow);
    }
    
    @Override
	public Result hit(int row, int col) {
    	if (row == this.boatRow && col >= this.boatLeftCol && col < this.boatLeftCol+boatSize) {
    		this.hit.add(new Pair<>(row,col));
    		return this.hit.size() == this.boatSize ? Result.WON : Result.HIT;
    	}
    	this.failures++;
    	return this.failures == FAILURES ? Result.LOST: Result.MISS;
	}
}
