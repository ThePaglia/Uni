package a02a.sol2;

import java.util.Set;

public interface Logics{
	
	Pair<Integer, Integer> pawnPosition();
	
	Set<Pair<Integer,Integer>> enemyPositions();
	
	void next();
	
	boolean isOver();

}
