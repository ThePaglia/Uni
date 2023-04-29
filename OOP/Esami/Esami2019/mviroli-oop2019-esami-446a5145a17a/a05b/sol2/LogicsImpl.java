package a05b.sol2;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {
	
	private final int gridSize;
	private Pair<Integer,Integer> initial;
	private int tickCount = 0;
	private Random random = new Random();
	
    public LogicsImpl(int gridSize){
    	this.gridSize = gridSize;
    	this.initial = new Pair<>(random.nextInt(gridSize-2)+1,random.nextInt(gridSize-2)+1);
    }
    
    @Override
	public void tick() {
		this.tickCount = this.tickCount+1;
	}

	@Override
	public boolean hasElement(int x, int y){
		return (x == initial.getX() && Math.abs(y-initial.getY()) <= tickCount) || 
				(y == initial.getY() && Math.abs(x-initial.getX()) <= tickCount) ||
				(x-y == initial.getX()-initial.getY() && Math.abs(x-initial.getX()) <= tickCount) || 
				(x+y == initial.getX()+initial.getY() && Math.abs(x-initial.getX()) <= tickCount);
	}

	@Override
	public boolean isOver() {
		return initial.getY()-tickCount < 0 || initial.getY()+tickCount >= this.gridSize ||
				initial.getX()-tickCount < 0 || initial.getX()+tickCount >= this.gridSize; 
	}   
}
