package a06.sol2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {
	
	private final int size;
	private final List<Pair<Integer,Integer>> selected = new ArrayList<>();
	private Optional<Iterator<Pair<Integer,Integer>>> toAdvance = Optional.empty();  
	
	public LogicsImpl(int size) {
		this.size = size;
	}
	
	private boolean neighbours(int x1, int y1, int x2, int y2) {
		return Math.abs(x1 - x2) <= 1 && Math.abs(y1 - y2) <= 1;
	}
	
	private boolean selectable(int x, int y) {
		if (x==0 || y==0 || x==size-1 || y==size-1){
			return false;
		}
		return !selected.stream().anyMatch(p -> neighbours(x, y, p.getX(), p.getY()));
	}
	
	@Override
	public boolean select(int x, int y) {
		if (selectable(x,y)) {
			this.selected.add(new Pair<>(x,y));
			return true;
		}
		return false;
	}

	@Override
	public Optional<Pair<Integer, Integer>> advance() {
		if (this.toAdvance.isEmpty()) {
			this.toAdvance = Optional.of(createAdvanceIterator());
		}
		return this.toAdvance.get().hasNext() ? Optional.of(this.toAdvance.get().next()) : Optional.empty();
	}
	
	private Pair<Integer,Integer> up(Pair<Integer,Integer> p){
		return new Pair<>(p.getX(),p.getY()-1);
	}
	
	private Pair<Integer,Integer> down(Pair<Integer,Integer> p){
		return new Pair<>(p.getX(),p.getY()+1);
	}

	private Iterator<Pair<Integer, Integer>> createAdvanceIterator() {
		return this.selected.stream().flatMap(p -> Stream.of(up(p), down(p))).iterator();
	}
}
