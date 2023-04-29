package a02c.sol2;

import java.util.*;

public class LogicsImpl implements Logics {
	
	private final int size;
	private final Set<Pair<Integer,Integer>> obstacles = new HashSet<>();
	private Pair<Integer,Integer> position;
	private final Random random = new Random();
	
	public LogicsImpl(int size) {
		this.size = size;
		this.position = this.relocateOnTop();
		while (this.obstacles.size()<20) {
			this.obstacles.add(new Pair<>(this.random.nextInt(size),1+this.random.nextInt(size-1)));
		}
	}
	
	private Pair<Integer,Integer> relocateOnTop() {
		return new Pair<>(this.random.nextInt(size),0);
	}
	
	private boolean obstacleAt(int x, int y) {
		return this.obstacles.contains(new Pair<>(x,y));
	}

	@Override
	public boolean next() {
		int x = this.position.getX();
		int y = this.position.getY();
		List<Pair<Integer,Integer>> possibilities = new ArrayList<>();
		if (y == size-1) {
			possibilities.add(this.relocateOnTop());
		} else if (!this.obstacleAt(x,y+1)){
			possibilities.add(new Pair<>(x,y+1));
		} else {
			if (x > 0 && !this.obstacleAt(x-1, y)) {
				possibilities.add(new Pair<>(x-1,y));
			}
			if (x < size-1 && !this.obstacleAt(x+1, y)) {
				possibilities.add(new Pair<>(x+1,y));
			}
		}
		if (possibilities.size()==0) {
			return false;
		}
		this.position = possibilities.get(this.random.nextInt(possibilities.size()));
		return true;
	}
	

	@Override
	public Pair<Integer, Integer> getNext() {
		return this.position;
	}

	@Override
	public boolean isObstacle(int x, int y) {
		return this.obstacles.contains(new Pair<>(x,y));
	}
}