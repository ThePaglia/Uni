package a02b.sol2;

import java.util.*;

public class LogicsImpl implements Logics {
	
	private final int size;
	private final Map<Pair<Integer,Integer>, Direction> obstacles = new HashMap<>();
	private Pair<Integer,Integer> position;
	private Direction direction = Direction.UP;
	
	enum Direction {
		LEFT, RIGHT, UP;
	}

	
	private static Map<Direction,Pair<Integer,Integer>> deltas = Map.of(
			Direction.LEFT, new Pair<>(-1,0),
			Direction.RIGHT, new Pair<>(1,0),
			Direction.UP, new Pair<>(0,-1)
	);
	
	public LogicsImpl(int size) {
		this.size = size;
		final var random = new Random();
		this.position = new Pair<>(random.nextInt(size),size-1);
		while (this.obstacles.size()<20) {
			var position = new Pair<>(random.nextInt(size),random.nextInt(size));
			this.obstacles.put(position, Direction.values()[random.nextInt(2)]);
		}
	}
	
	private Pair<Integer,Integer> nextOnDirection(){
		return new Pair<>(
				this.position.getX()+deltas.get(this.direction).getX(),
				this.position.getY()+deltas.get(this.direction).getY());
	}
	
	@Override
	public void toNext() {
		if (this.obstacles.containsKey(this.position)) {
			this.direction = this.obstacles.get(this.position); 
		}
		this.position = nextOnDirection();
	}

	@Override
	public Pair<Integer, Integer> getNext() {
		return this.position;
	}

	@Override
	public Optional<Boolean> changeDirectionToLeftAt(int x, int y) {
		return Optional.ofNullable(obstacles.get(new Pair<>(x,y))).map(d -> d==Direction.LEFT);
	}

	@Override
	public boolean isOver() {
		return this.position.getX()<0 || this.position.getY()<0 || this.position.getX()>=size || this.position.getY()>=size;
	}
}