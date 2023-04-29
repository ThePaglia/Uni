package a03a.sol2;

import java.util.Set;

public interface Logics{
	
	Pair<Integer, Integer> rookPosition();
	
	Set<Pair<Integer,Integer>> enemyPositions();
	
	Set<Pair<Integer,Integer>> enabledPositions();
	
	void hit(Pair<Integer,Integer> position);
	
	boolean isOver();

}
