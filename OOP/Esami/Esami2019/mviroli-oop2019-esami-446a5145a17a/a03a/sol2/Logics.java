package a03a.sol2;

import java.util.Set;

public interface Logics {
	
	void addPit(Pair<Integer,Integer> pit);
	
	void tick();
	
	Pair<Integer,Integer> getItem();
	
	Set<Pair<Integer,Integer>> getPits();
	
	boolean isOver();
	
}
