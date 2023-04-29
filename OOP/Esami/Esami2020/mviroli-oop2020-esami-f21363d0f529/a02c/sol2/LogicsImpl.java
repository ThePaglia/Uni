package a02c.sol2;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {

	private final List<Pair<Integer, Integer>> hits = new LinkedList<>();
	private final int size;
	
	public LogicsImpl(int size) {
		this.size = size;
	}

	@Override
	public Optional<Integer> hit(int x, int y) {
		final var p = new Pair<>(x,y);
		if (hits.contains(p)) {
			return Optional.empty();
		}
		hits.add(0,p);
		return Optional.of(hits.size());
	}
	
	private boolean check(Function<Pair<Integer,Integer>,Integer> projector) {
		var coords = this.hits.stream().map(projector).limit(4).distinct().collect(Collectors.toList());
		return coords.size()==2 && Math.abs(coords.get(0)-coords.get(1))==1 && 
				Collections.min(coords)>0 && Collections.max(coords)<size-1; 
	}
	
	@Override
	public boolean isOver() {
		if (this.hits.size() <= 3) {
			return false;
		}
		return check(Pair::getX) && check(Pair::getY); 
	}

}
