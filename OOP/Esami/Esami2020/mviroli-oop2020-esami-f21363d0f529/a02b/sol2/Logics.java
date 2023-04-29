package a02b.sol2;

import java.util.Set;

public interface Logics{
	
	Set<Pair<Integer,Integer>> itemsPositions();
	
	void up();
	
	void down();
	
	void hit(Pair<Integer,Integer> position);

}
