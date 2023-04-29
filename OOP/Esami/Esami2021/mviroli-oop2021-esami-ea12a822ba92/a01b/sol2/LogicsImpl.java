package a01b.sol2;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {

	private final List<Pair<Integer,Integer>> hit = new LinkedList<>();
	
	@Override
	public HitType hit(int x, int y) {
		var p = new Pair<>(x,y);
		if (this.hit.size()==3) {
			this.hit.clear();
		}
		if (!this.ok(p)) {
			return HitType.WRONG;
		}
		this.hit.add(p);
		switch (this.hit.size()) {
			case 1: return HitType.FIRST;
			case 2: return HitType.SECOND;
			case 3: return HitType.THIRD;
			default: throw new IllegalStateException();
		}
	}

	private boolean ok(Pair<Integer, Integer> p) {
		switch (this.hit.size()) {
		case 0: return true;
		case 1: return this.hit.get(0).getX() == p.getX() || this.hit.get(0).getY() == p.getY();
		case 2: return this.hit.get(0).getX() == this.hit.get(1).getX() 
				? p.getY() == this.hit.get(0).getY()
				: p.getX() == this.hit.get(0).getX();
		default: throw new IllegalStateException();
		}
	}
	
	@Override
	public boolean isSelected(int x, int y) {
		return this.hit.size()==3 && (this.inInterval(new Pair<>(x,y),hit.get(0),hit.get(1)) ||
				this.inInterval(new Pair<>(x,y),hit.get(0),hit.get(2)));
	}

	private boolean inInterval(Pair<Integer, Integer> p, Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
		return (p.getX() == p1.getX() && p1.getX() == p2.getX() && rangeExcluded(p1.getY(),p2.getY()).contains(p.getY())) |
				(p.getY() == p1.getY() && p1.getY() == p2.getY() && rangeExcluded(p1.getX(),p2.getX()).contains(p.getX()));
	}
	
	private Set<Integer> rangeExcluded(int x, int x2) {
		return (x<=x2 ? IntStream.rangeClosed(x+1,x2-1) : IntStream.rangeClosed(x2+1,x-1)).boxed().collect(Collectors.toSet());
	}
}