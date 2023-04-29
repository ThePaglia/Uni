package a05.sol2;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {
	
	private final Map<Pair<Integer, Integer>, Integer> items = new HashMap<>();
	private final int size;
	private final Random random = new Random();
	
	public LogicsImpl(int size) {
		this.size = size;
		for (int i=0; i<size; i++) {
			for (int j=0; j<size;j++) {
				items.put(new Pair<>(i,j), this.random.nextInt(10));
			}
		}
	}	

	@Override
	public Map<Pair<Integer, Integer>, Integer> items() {
		return Collections.unmodifiableMap(this.items);
	}

	@Override
	public void hit(int x, int y) {
		if (items.containsKey(new Pair<>(x,y))) {
			this.spread(x,y);
		} else {
			this.collect(x,y);
		}
	}
	
	private void spread(int x, int y) {
		int v = this.items.remove(new Pair<>(x,y));
		this.neighbours(x,y).filter(this.items::containsKey).forEach(p -> this.items.put(p, v));
	}
	
	private void collect(int x, int y) {
		int elem = this.neighbours(x,y)
				.filter(this.items::containsKey)
				.map(this.items::remove)
				.reduce(0, Integer::sum);
		this.items.put(new Pair<>(x,y),elem);
	}
	
	private Stream<Pair<Integer,Integer>> neighbours(int x , int y){
		return IntStream.range(-1, 2)
				.boxed()
				.flatMap(i -> IntStream.range(-1, 2).boxed().map(j -> new Pair<>(i,j)))
				.filter(p -> !p.equals(new Pair<>(0,0)))
				.map(p -> new Pair<>(p.getX()+x,p.getY()+y))
				.filter(p -> p.getX()>=0)
				.filter(p -> p.getY()>=0)
				.filter(p -> p.getX()<size)
				.filter(p -> p.getY()<size);
	}

	@Override
	public boolean isOver() {
		return this.items.size()==0;
	}
	
}
