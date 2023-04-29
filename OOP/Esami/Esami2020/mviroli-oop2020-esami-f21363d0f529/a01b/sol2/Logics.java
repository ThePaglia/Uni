package a01b.sol2;

import java.util.Set;

public interface Logics{
	
	Set<Pair<Integer,Integer>> pawnsPositions();
	
	Pair<Integer,Integer> kingPosition();
	
	boolean moveKing(int x, int y);
	
	boolean isOver();
    
}
