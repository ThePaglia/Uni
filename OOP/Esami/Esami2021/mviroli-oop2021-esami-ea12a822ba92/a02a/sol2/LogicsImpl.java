package a02a.sol2;

import java.util.*;

public class LogicsImpl implements Logics {
	
	private static enum Direction {
		UP(0,-1), RIGHT(1,0), DOWN(0,1), LEFT(-1,0);
		
		int x;
		int y;
		Direction(int x, int y){
			this.x = x;
			this.y = y;
		}
		
		Direction next() {
			return Direction.values()[(this.ordinal()+1) % Direction.values().length];
		}
	}
	
	private final int size;
	private final Deque<Pair<Integer,Integer>> obstacles = new LinkedList<>();
	private Direction direction = Direction.UP;
	
	public LogicsImpl(int size) {
		this.size = size;
	}

	@Override
	public Optional<Pair<Integer,Integer>> next() {
		if (obstacles.isEmpty()) {
			var random = new Random();
			obstacles.add(new Pair<>(random.nextInt(size),random.nextInt(size)));
			return Optional.of(obstacles.getFirst());
		}
		var last = obstacles.getLast();
		for (int i=0; i<4; i++) {
			var next = new Pair<>(last.getX()+this.direction.x, last.getY()+this.direction.y);
			if (!obstacles.contains(next) && next.getX()>=0 && next.getY()>=0 && next.getX()<size && next.getY()<size) {
				obstacles.addLast(next);
				return Optional.of(next);
			} 
			direction = direction.next();
		}
		return Optional.empty();
	}
}