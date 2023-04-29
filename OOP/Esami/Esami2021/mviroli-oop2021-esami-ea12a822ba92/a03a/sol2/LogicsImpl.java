package a03a.sol2;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {

	private final int size;
	private Set<Pair<Integer,Integer>> selected = new HashSet<>();
	private Pair<Integer,Integer> lastSelected = new Pair<>(-1,-1);
	
	public LogicsImpl(int size) {
		this.size = size;
	}

	@Override
	public void hit(int x, int y) {
		var p = new Pair<>(x,y);
		if (this.inBox(p)) {
			this.lastSelected = p;
			this.updateSelected(p);
		}
	}

	private boolean inBox(Pair<Integer, Integer> p) {
		return p.getX()<=this.size/2 && p.getY()<=this.size/2 && p.getX()>this.lastSelected.getX() && p.getY()>this.lastSelected.getY();
	}

	private void updateSelected(Pair<Integer,Integer> p1) {
		var p2 = new Pair<>(this.size-p1.getX()-1, this.size-p1.getY()-1);
		for (var x: range(p1.getX(),p2.getX())){
			for (var y: range(p1.getY(),p2.getY())){
				if (x == p1.getX() || x == p2.getX() || y == p1.getY() || y == p2.getY()) {
					this.selected.add(new Pair<>(x,y));
				}
			}
		}
	}
	
	private Iterable<Integer> range(int x, int x2) {
		return x<=x2 ? ()->IntStream.rangeClosed(x,x2).iterator() : ()->IntStream.rangeClosed(x2,x).iterator();
	}

	@Override
	public boolean isSelected(int x, int y) {
		return this.selected.contains(new Pair<>(x,y));
	}

	@Override
	public boolean isOver() {
		return this.lastSelected.getX() >= size/2-1 || this.lastSelected.getY() >= size/2-1;
	}
}
