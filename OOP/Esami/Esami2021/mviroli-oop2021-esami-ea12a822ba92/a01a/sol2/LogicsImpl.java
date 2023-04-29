package a01a.sol2;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {

	private final int size;
	private Optional<Pair<Integer,Integer>> first = Optional.empty();
	private Set<Pair<Integer,Integer>> selected = new HashSet<>();
	
	public LogicsImpl(int size) {
		this.size = size;
	}

	@Override
	public HitType hit(int x, int y) {
		var p = new Pair<>(x,y);
		if (this.first.isEmpty()) {
			this.first = Optional.of(p);
			return HitType.FIRST;
		}
		this.updateSelected(this.first.get(),p);
		this.first = Optional.empty();
		return HitType.SECOND;
	}

	private void updateSelected(Pair<Integer,Integer> p1, Pair<Integer,Integer> p2) {
		for (var x: range(p1.getX(),p2.getX())){
			for (var y: range(p1.getY(),p2.getY())){
				this.selected.add(new Pair<>(x,y));
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
		return this.selected.size() == size*size;
	}
}
