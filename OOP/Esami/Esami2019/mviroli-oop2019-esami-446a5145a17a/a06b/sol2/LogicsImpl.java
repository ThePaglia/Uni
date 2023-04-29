package a06b.sol2;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {
	
	private final int gridSize;
	private final Set<Pair<Integer,Integer>> items = new HashSet<>();
	private Optional<Pair<Integer,Integer>> last = Optional.empty();
	private Random random = new Random();
	
    public LogicsImpl(int gridSize){
    	this.gridSize = gridSize;
    }
    
    private List<Pair<Integer,Integer>> possibleNext(Pair<Integer,Integer> p){
    	var l = List.of(new Pair<>(0,-1), new Pair<>(0,1), new Pair<>(1,0), new Pair<>(-1,0));
    	return l.stream()
    			.map(q -> new Pair<>(q.getX()+p.getX(),q.getY()+p.getY()))
    			.filter(q -> !this.items.contains(q))
    			.filter(q -> q.getX()>=0)
    			.filter(q -> q.getY()>=0)
    			.filter(q -> q.getX()<gridSize)
    			.filter(q -> q.getY()<gridSize)
    			.collect(Collectors.toList());
    }
    
    @Override
	public Optional<Pair<Integer,Integer>> next(){
    	if (this.last.isEmpty()) {
    		this.last = Optional.of(new Pair<>((this.gridSize-1)/2,(this.gridSize-1)/2));
    		this.items.add(this.last.get());
    		return this.last;
    	}
    	var l = possibleNext(this.last.get());
    	if (l.isEmpty()) {
    		return Optional.empty();
    	}
    	this.last = Optional.of(l.get(random.nextInt(l.size())));
		this.items.add(this.last.get());
		return this.last;    	
	}

}
