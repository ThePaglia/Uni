package a05a.sol2;

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
    	while (set.size() < 10) {
    		this.set.add(new Pair<>(random.nextInt(gridSize),random.nextInt(gridSize)));
    	}
    }
    
	@Override
	public boolean hasElement(int x, int y) {
		return this.set.contains(new Pair<>(x,y));
	}

	@Override
	public boolean isOver() {
		return this.set.size()==0;
	}

	@Override
	public void select(int x, int y) {
		for (int i=x-1;i<=x+1;i++) {
			for (int j=y-1;j<=y+1;j++) {
				if (this.set.remove(new Pair<>(i,j))) {
					this.select(i, j);
				}
			}
		}
	}   
}
