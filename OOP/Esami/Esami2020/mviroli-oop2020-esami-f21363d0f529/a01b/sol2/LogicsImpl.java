package a01b.sol2;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {

	private final Set<Pair<Integer, Integer>> pawns = new HashSet<>();
	private Pair<Integer, Integer> king;
	

	public LogicsImpl(int size) {
		final Random random = new Random();
		this.king = new Pair<>(size-1, size-1);
		while (this.pawns.size() < 3) {
			this.pawns.add(new Pair<>(random.nextInt(size), random.nextInt(size-2)));
		}
	}

	@Override
	public Set<Pair<Integer, Integer>> pawnsPositions() {
		return Collections.unmodifiableSet(this.pawns);
	}

	@Override
	public Pair<Integer, Integer> kingPosition() {
		return this.king;
	}

	@Override
	public boolean moveKing(int x, int y) {
		var p = new Pair<>(x, y);
		if (p.equals(king) || Math.abs(x - king.getX()) > 1 || Math.abs(y - king.getY()) > 1 ||
				pawns.contains(new Pair<>(x-1,y-1)) || pawns.contains(new Pair<>(x+1,y-1))) {
			return false;
		}
		this.king = p;
		this.pawns.remove(this.king);
		return true;
	}

	@Override
	public boolean isOver() {
		return this.pawns.isEmpty();
	}

}
