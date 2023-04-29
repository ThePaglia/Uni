package a04.sol2;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {
	
	private static Map<Piece, Predicate<Pair<Integer,Integer>>> predicateMap = Map.of(
				Piece.KING,  p -> p.getX()<=1 && p.getY()<=1 && (p.getX()+p.getY()>0),
				Piece.KNIGHT,  p -> p.getX()+p.getY()==3 && p.getX()>0 && p.getY()>0
			);

	private final Set<Pair<Integer, Integer>> pawns = new HashSet<>();
	private Pair<Integer, Integer> hero;
	private Piece turn = Piece.KING;
	

	public LogicsImpl(int size) {
		final Random random = new Random();
		final Supplier<Integer> rnd = ()->random.nextInt(size);
		while (this.pawns.size() < 3) {
			this.pawns.add(new Pair<>(rnd.get(), rnd.get()));
		}
		do {
			this.hero = new Pair<>(rnd.get(), rnd.get());
		} while (this.pawns.contains(this.hero));
		
	}

	@Override
	public Set<Pair<Integer, Integer>> pawnsPositions() {
		return Collections.unmodifiableSet(this.pawns);
	}

	@Override
	public Pair<Integer, Integer> heroPosition() {
		return this.hero;
	}
	
	private void turnPiece() {
		this.turn = this.turn == Piece.KING ? Piece.KNIGHT : Piece.KING; 
	}

	@Override
	public boolean moveHero(int x, int y) {
		var delta = new Pair<>(Math.abs(this.hero.getX()-x), Math.abs(this.hero.getY()-y));
		if (predicateMap.get(this.turn).test(delta)) {
			this.hero = new Pair<>(x, y);
			if (this.pawns.remove(this.hero)) {
				this.turnPiece();
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean isOver() {
		return this.pawns.isEmpty();
	}

	@Override
	public Piece currentPiece() {
		return this.turn;
	}

}
