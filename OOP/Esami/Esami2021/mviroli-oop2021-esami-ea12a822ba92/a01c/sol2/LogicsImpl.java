package a01c.sol2;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {

	private Optional<Pair<Integer,Integer>> lastHit = Optional.empty();
	private Optional<Boolean> lastDirectionIsHorizontal = Optional.empty();
	private Set<Pair<Integer,Integer>> selected = new HashSet<>();
	
	@Override
	public boolean hit(int x, int y) {
		var p = new Pair<>(x,y);
		if (this.lastHit.isEmpty()) {
			this.lastHit = Optional.of(p);
			this.selected.add(p);
			return true;
		}
		var last = lastHit.get();
		if (!inLine(p,last)) {
			return false;
		}
		System.out.println(p+" "+last+" "+this.lastDirectionIsHorizontal);
		if (this.lastDirectionIsHorizontal.isEmpty()) {
			this.lastDirectionIsHorizontal = Optional.of(p.getY() == last.getY());
		} else if (p.getY() == last.getY() && this.lastDirectionIsHorizontal.get()) {
			return false;
		} else if (p.getX() == last.getX() && !this.lastDirectionIsHorizontal.get()) {
			return false;
		} else {
			this.lastDirectionIsHorizontal = this.lastDirectionIsHorizontal.map(b -> !b);
		}
		this.lastHit = Optional.of(p);
		this.addSelection(p,last);
		return true;
	}
	
	private IntStream range(int i, int j) {
		return IntStream.rangeClosed(Math.min(i,j), Math.max(i,j));
	}

	private void addSelection(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
		var s = (p1.getY()==p2.getY())
				? range(p1.getX(), p2.getX()).boxed().map(x -> new Pair<>(x,p1.getY()))
				: range(p1.getY(), p2.getY()).boxed().map(y -> new Pair<>(p1.getX(),y));
		s.peek(System.out::println).forEach(this.selected::add);
	}
	
	private boolean inLine(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
		return p1.getX() == p2.getX() || p1.getY() == p2.getY();
	}

	@Override
	public boolean isSelected(int x, int y) {
		return this.selected.contains(new Pair<>(x,y));
	}
}