package a02b.sol2;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class LogicsImpl implements Logics {
	
	// A very general solution: an easier one, more ad-hoc, could be developed
	
	private enum Movement {
		UP(new Pair<>(0,-1), (p,size) -> p.getY()==0), 
		DOWN(new Pair<>(0,1), (p,size) -> p.getY()==size-1);
		
		private Pair<Integer,Integer> delta; 
		private BiPredicate<Pair<Integer,Integer>,Integer> limit;
		
		private Movement(Pair<Integer,Integer> delta, BiPredicate<Pair<Integer,Integer>,Integer> limit) {
			this.delta = delta;
			this.limit = limit;
		}
		
		public Pair<Integer,Integer> newPosition(Pair<Integer,Integer> oldPosition) {
			return new Pair<>(oldPosition.getX()+this.delta.getX(), oldPosition.getY()+this.delta.getY());
		}
		
		public Movement changed() {
			return (this == UP) ? DOWN : UP; 
		}
		
		public boolean limitReached(Pair<Integer, Integer> position, int gridSize) {
			return this.limit.test(position, gridSize);
		}
	}
    
	private final int gridSize;
	private Map<Pair<Integer, Integer>,Movement> items = new HashMap<>();
	    
    public LogicsImpl(int gridSize){
    	this.gridSize = gridSize;
    }

	@Override
	public void addItem(Pair<Integer, Integer> position) {
		if (!this.items.containsKey(position)) {
			this.items.put(position,Movement.DOWN);
		}
	}

	@Override
	public void tick() {
		this.items = this.items.entrySet()
				               .stream()
				               .peek(e -> {
				            	   if (e.getValue().limitReached(e.getKey(), this.gridSize)) {
				            		   e.setValue(e.getValue().changed());
				            	   }})
				               .map(e -> new Pair<>(e.getValue().newPosition(e.getKey()),e.getValue()))
				               .collect(Collectors.toMap(Pair::getX,Pair::getY));
	}

	@Override
	public Set<Pair<Integer, Integer>> getPositions() {
		return this.items.keySet();
	}   
}
