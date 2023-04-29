package a03b.sol2;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {

	private Pair<Integer, Integer> bishop;
	private final Set<Pair<Integer, Integer>> enemies = new HashSet<>();
	private final int size;
	
	private enum Direction {
		MAIN_DIAGONAL,
		SECONDARY_DIAGONAL;
		
		public Pair<Integer,Integer> update(Pair<Integer,Integer> position, int delta) {
			return new Pair<>(position.getX() + delta, position.getY() + (this==SECONDARY_DIAGONAL ? -delta : delta));
		}
	}

	
	public LogicsImpl(int size) {
		this.size = size;
		final Random random = new Random();
		this.bishop = new Pair<>(random.nextInt(size),random.nextInt(size));
		while (this.enemies.size() < size) {
			this.enemies.add(new Pair<>(random.nextInt(size), random.nextInt(size)));
		}
	}

	@Override
	public Pair<Integer, Integer> bishopPosition() {
		return this.bishop;
	}

	@Override
	public Set<Pair<Integer,Integer>> enemyPositions() {
		return Collections.unmodifiableSet(this.enemies);
	}
	
	@Override
	public void hit(Pair<Integer,Integer> position) {
		if (enabledPositions().contains(position)) {
			this.bishop = position;
			this.enemies.remove(position);
		}
	}

	private boolean positionOk(Pair<Integer,Integer> p) {
		return p.getX()>=0 && p.getX()<size && p.getY()>=0 && p.getY()<size;
	}

	@Override
	public Set<Pair<Integer, Integer>> enabledPositions() {
		final Set<Pair<Integer, Integer>> enabled = new HashSet<>();
		for (Direction direction: Direction.values()) {
			for (int d: Set.of(-1,1)) {
				for (int i=1; i<size; i++) {
					var p = direction.update(this.bishopPosition(), d*i);
					if (!positionOk(p)) {
						break;
					}
					enabled.add(p);
					if (this.enemies.contains(p)) {
						break;						
					}
				}
			}
		}
		return enabled;
	}
}
