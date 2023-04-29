package a03a.sol2;

import java.util.*;
import java.util.function.*;

public class LogicsImpl implements Logics {

	private Pair<Integer, Integer> rook;
	private final Set<Pair<Integer, Integer>> enemies = new HashSet<>();
	private final int size;
	
	private enum Direction {
		HORIZONTAL,
		VERTICAL;
	
		public Pair<Integer,Integer> update(Pair<Integer,Integer> position, int delta) {
			return new Pair<>(position.getX() + (this==HORIZONTAL ? delta : 0), position.getY() + (this==VERTICAL ? delta : 0));
		}
	}

	
	public LogicsImpl(int size) {
		this.size = size;
		final Random random = new Random();
		this.rook = new Pair<>(random.nextInt(size),random.nextInt(size));
		while (this.enemies.size() < size) {
			this.enemies.add(new Pair<>(random.nextInt(size), random.nextInt(size)));
		}
	}

	@Override
	public Pair<Integer, Integer> rookPosition() {
		return this.rook;
	}

	@Override
	public Set<Pair<Integer,Integer>> enemyPositions() {
		return Collections.unmodifiableSet(this.enemies);
	}
	
	@Override
	public void hit(Pair<Integer,Integer> position) {
		if (enabledPositions().contains(position)) {
			this.rook = position;
			this.enemies.remove(position);
		}
	}

	@Override
	public boolean isOver() {
		return this.enemyPositions().isEmpty();
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
					var p = direction.update(this.rookPosition(), d*i);
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
