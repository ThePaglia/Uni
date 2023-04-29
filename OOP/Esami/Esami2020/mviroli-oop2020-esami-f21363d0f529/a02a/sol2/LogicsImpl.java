package a02a.sol2;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {

	private Pair<Integer, Integer> pawn;
	private final Set<Pair<Integer, Integer>> enemies = new HashSet<>();

	
	public LogicsImpl(int size) {
		final Random random = new Random();
		this.pawn = new Pair<>(random.nextInt(size),size-1);
		while (this.enemies.size() < size) {
			this.enemies.add(new Pair<>(random.nextInt(size), random.nextInt(size-1)));
		}
	}

	@Override
	public Pair<Integer, Integer> pawnPosition() {
		return this.pawn;
	}

	@Override
	public Set<Pair<Integer,Integer>> enemyPositions() {
		return Collections.unmodifiableSet(this.enemies);
	}
	
	private Optional<Pair<Integer,Integer>> eatingTarget() {
		return Stream.of(-1,+1).
				map(i -> new Pair<>(this.pawn.getX()+i, this.pawn.getY()-1)).
				filter(this.enemies::contains).
				reduce( (p1,p2) -> Math.random()>0.5 ? p1 : p2);
	}
	
	private Optional<Pair<Integer,Integer>> straightTarget(){
		return Optional.of(this.pawn).
				map(p -> new Pair<>(p.getX(),p.getY()-1)).
				filter(p -> p.getY() >= 0).
				filter(p -> !this.enemies.contains(p));
	}
	
	private Optional<Pair<Integer,Integer>> nextTarget(){
		return eatingTarget().isPresent() ? eatingTarget() : straightTarget();
	}
	
	private void moveTo(Pair<Integer,Integer> target) {
		this.pawn = target;
		this.enemies.remove(target);
	}

	@Override
	public void next() {
		this.nextTarget().ifPresent(this::moveTo);
	}

	@Override
	public boolean isOver() {
		return this.nextTarget().isEmpty();
	}
}
