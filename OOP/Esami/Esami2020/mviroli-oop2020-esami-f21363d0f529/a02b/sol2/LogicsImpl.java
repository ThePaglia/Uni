package a02b.sol2;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {

	private final Set<Pair<Integer, Integer>> items = new HashSet<>();
	private final int size;

	
	public LogicsImpl(int size) {
		this.size = size;
	}

	@Override
	public Set<Pair<Integer,Integer>> itemsPositions() {
		return Collections.unmodifiableSet(this.items);
	}
	
	private List<Integer> getCounters(){
		return Stream.iterate(0,x->x+1).
				limit(size).
				map(i -> items.stream().filter(p -> p.getX()==i).count()).
				map(Long::intValue).
				collect(Collectors.toList());
	}
	
	private void moveAll(int initial, UnaryOperator<Integer> operator) {
		var counters = this.getCounters(); 
		this.items.clear();
		IntStream.range(0, size).forEach(x->
			Stream.iterate(initial, operator).limit(counters.get(x)).map(y->new Pair<>(x,y)).forEach(this.items::add)
		);
	}

	@Override
	public void up() {
		this.moveAll(0, i->i+1);
	}

	@Override
	public void down() {
		this.moveAll(size-1, i->i-1);
	}

	@Override
	public void hit(Pair<Integer, Integer> position) {
		this.items.add(position);
	}
	
}
