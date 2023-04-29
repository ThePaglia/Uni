package a04a.sol2;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {
	
	private final int gridSize;
	private Pair<Integer,Integer> pos;
	private Random random = new Random();
	    
    public LogicsImpl(int gridSize){
    	this.gridSize = gridSize;
    	this.pos = new Pair<>(gridSize/2,gridSize/2);
    }
    
    // Could be cached to avoid to call it twice, from tick and isOver..
    private List<Pair<Integer,Integer>> next() {
    	return Stream.of(new Pair<>(0,1),new Pair<>(1,0),new Pair<>(0,-1),new Pair<>(-1,0))
				.map(p -> new Pair<>(p.getX()+pos.getX(),p.getY()+pos.getY()))
				.filter(p -> p.getX()>=0)
				.filter(p -> p.getY()>=0)
				.filter(p -> p.getX()<this.gridSize)
				.filter(p -> p.getY()<this.gridSize)
				.collect(Collectors.toList());
    }
    
	@Override
	public void tick() {
		var l = this.next();
		this.pos = l.get(random.nextInt(l.size()));				
	}

	@Override
	public Pair<Integer, Integer> getPosition() {
		return this.pos;
	}

	@Override
	public boolean isOver() {
		return this.next().size()==2;
	}   
}
