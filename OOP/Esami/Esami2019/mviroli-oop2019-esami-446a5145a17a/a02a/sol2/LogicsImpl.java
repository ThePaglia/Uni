package a02a.sol2;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {
	
	private final int gridSize;
	private int pos = 0;
	private boolean increase = true;
	    
    public LogicsImpl(int gridSize){
    	this.gridSize = gridSize;
    }

	@Override
	public void tick() {
		if (this.pos == 0 && !this.increase) {
			this.increase = true;
		} else if (this.pos == this.gridSize/2 && this.increase){
			this.increase = false;
		}
		this.pos = this.pos + (this.increase ? 1 : -1);
	}

	@Override
	public Set<Pair<Integer, Integer>> getPositions() {
		return Stream.of(new Pair<>(this.pos,this.pos), new Pair<>(this.gridSize/2,this.pos))
				        .flatMap(p -> Stream.of(p,new Pair<>(this.gridSize-p.getX()-1,this.gridSize-p.getY()-1))) // aggiunge rotazione di 180
				        .flatMap(p -> Stream.of(p,new Pair<>(this.gridSize-p.getY()-1,p.getX()))) // aggiunge rotazione di 90 gradi
				        .collect(Collectors.toSet());
	}   
}
