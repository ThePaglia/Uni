package a04b.sol2;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {
	
	private final int gridSize;
	private Set<Pair<Integer,Integer>> set = new HashSet<>();
	private Random random = new Random();
	
    public LogicsImpl(int gridSize){
    	this.gridSize = gridSize;
    	while (set.size()<3) {
    		this.set.add(new Pair<>(random.nextInt(gridSize),random.nextInt(gridSize)));
    	}    	
    }
    
    private boolean neighbours(Pair<Integer,Integer> p1, Pair<Integer,Integer> p2) {
    	return Math.abs(p1.getX()-p2.getX())<=1 && 
    			Math.abs(p1.getY()-p2.getY())<=1 && 
    			!p1.equals(p2); 
    }
    
	@Override
	public void tick() {
		Set<Pair<Integer,Integer>> s = new HashSet<>();
		for (int x=0; x<gridSize; x++) {
			for (int y=0; y<gridSize; y++) {
				var p = new Pair<>(x,y);
				if (set.stream().anyMatch(pp -> neighbours(p,pp))) {
					s.add(p);
				}
			}
		}
		set.addAll(s);
	}

	@Override
	public Set<Pair<Integer,Integer>> getPositions(){
		return Collections.unmodifiableSet(this.set);
	}

	@Override
	public boolean isOver() {
		return this.set.size() == this.gridSize * this.gridSize;
	}   
}
