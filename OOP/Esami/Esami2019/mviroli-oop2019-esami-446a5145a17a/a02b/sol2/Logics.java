package a02b.sol2;

import java.util.Set;

public interface Logics{
	
	void addItem(Pair<Integer,Integer> position);

	void tick();
	
	Set<Pair<Integer,Integer>> getPositions();
    
}
