package a06a.sol2;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {
	
	private final Map<Pair<Integer,Integer>,Integer> values = new HashMap<>();
	private boolean over = false;
	    
    public LogicsImpl(){
    }
    
	@Override
	public Optional<Integer> getElement(int x, int y) {
		return Optional.ofNullable(this.values.get(new Pair<>(x,y)));
	}

	@Override
	public boolean isOver() {
		return this.over;
	}

	@Override
	public void select(int x, int y) {
		final var p = new Pair<>(x,y);
		if (this.values.containsKey(p)) {
			this.over = true;
		}
		if (!this.over) {
			this.values.put(p,0);
			this.updateAll(p);
		}
	}   
	
	private void updateAll(Pair<Integer,Integer> p) {
		this.values.replaceAll((k,v)-> k.getX() != p.getX() && k.getY() != p.getY() ? v+1 : v);
	}
}
