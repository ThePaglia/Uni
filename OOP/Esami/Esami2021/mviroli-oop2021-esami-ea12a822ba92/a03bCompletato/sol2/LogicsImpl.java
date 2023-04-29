package a03b.sol2;

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
			obstacles.add(new Pair<>(2+random.nextInt(size-4),2+random.nextInt(size-4)));
			return Optional.of(obstacles.getFirst());
		}
		var last = obstacles.getLast();
		var directions = List.of(this.direction.next(),this.direction);
		for (var dir: directions) {
			var next = new Pair<>(last.getX()+dir.x, last.getY()+dir.y);
			if (!obstacles.contains(next) && next.getX()>=0 && next.getY()>=0 && next.getX()<size && next.getY()<size) {
				obstacles.addLast(next);
				this.direction = dir;
				return Optional.of(next);
			}
		} 
		return Optional.empty();
	}
}