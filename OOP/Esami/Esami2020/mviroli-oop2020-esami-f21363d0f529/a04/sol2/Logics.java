package a04.sol2;

import java.util.Set;
import java.util.function.Predicate;

public interface Logics{
	
	public static enum Piece {
		KING,KNIGHT;
	}

	
	Set<Pair<Integer,Integer>> pawnsPositions();
	
	Pair<Integer,Integer> heroPosition();
	
	boolean moveHero(int x, int y);
	
	Piece currentPiece();
	
	boolean isOver();
    
}
