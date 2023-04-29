package a03a.sol2;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class LogicsImpl implements Logics {
	
	// A very general solution: an easier one, more ad-hoc, could be developed
	
	private enum Movement {
		RIGHT(new Pair<>(1,0), (p,size) -> p.getX()==size-1),
		DOWN(new Pair<>(0,1), (p,size) -> p.getY()==size-1),
		LEFT(new Pair<>(-1,0), (p,size) -> p.getX()==0),
		UP(new Pair<>(0,-1), (p,size) -> p.getY()==0); 
		
		
		private Pair<Integer,Integer> delta; 
		private BiPredicate<Pair<Integer,Integer>,Integer> limit;
		
		private Movement(Pair<Integer,Integer> delta, BiPredicate<Pair<Integer,Integer>,Integer> limit) {
			this.delta = delta;
			this.limit = limit;
		}
		
		public Pair<Integer,Integer> newPosition(Pair<Integer,Integer> oldPosition) {
			return new Pair<>(oldPosition.getX()+this.delta.getX(), oldPosition.getY()+this.delta.getY());
		}
		
		public Movement next() {
			return values()[(this.ordinal()+1)%4]; 
		}
		
		public boolean limitReached(Pair<Integer, Integer> position, int gridSize) {
			return this.limit.test(position, gridSize);
		}
	}
    
	private final int gridSize;
	private Pair<Integer, Integer> item = new Pair<>(0,0);
	private Set<Pair<Integer, Integer>> pits = new HashSet<>();
	private Movement movement = Movement.RIGHT;
	    
    public LogicsImpl(int gridSize){
    	this.gridSize = gridSize;
    }

	@Override
	public void tick() {
		if (this.movement.limitReached(this.item, this.gridSize)) {
 		   this.movement = this.movement.next();
 	   	}
		this.item = this.movement.newPosition(this.item);
		
	}

	@Override
	public Pair<Integer, Integer> getItem() {
		return this.item;
	}

	@Override
	public void addPit(Pair<Integer, Integer> pit) {
		this.pits.add(pit);
	}

	@Override
	public Set<Pair<Integer, Integer>> getPits() {
		return Collections.unmodifiableSet(this.pits);
	}

	@Override
	public boolean isOver() {
		return this.pits.contains(this.item);
	}
    
    

}
