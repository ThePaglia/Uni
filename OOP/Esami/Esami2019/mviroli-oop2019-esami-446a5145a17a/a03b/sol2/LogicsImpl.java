package a03b.sol2;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {
	
	private final int gridSize;
	private Pair<Integer,Integer> pos;
	private Set<Pair<Integer,Integer>> stay = new HashSet<>();
	private Random random = new Random();
	    
    public LogicsImpl(int gridSize){
    	this.gridSize = gridSize;
    	this.pos = newElement();
    }
    
    private Pair<Integer,Integer> newElement() {
    	return new Pair<>(random.nextInt(this.gridSize),0);
    }

	@Override
	public void tick() {
		var pos2 = new Pair<>(this.pos.getX(),this.pos.getY()+1);
		if (pos2.getY() == this.gridSize || this.stay.contains(pos2)) {
			this.stay.add(this.pos);
			this.pos = newElement();
		} else {
			this.pos = pos2;
		}
	}

	@Override
	public Set<Pair<Integer, Integer>> getPositions() {
		var set = new HashSet<>(stay);
		set.add(pos);
		return set;
	}

	@Override
	public boolean isOver() {
		return this.stay.stream().anyMatch(p -> p.getY()==0);
	}   
}
