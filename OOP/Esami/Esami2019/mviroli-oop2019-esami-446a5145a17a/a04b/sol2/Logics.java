package a04b.sol2;

import java.util.Set;

public interface Logics{
	
	void tick();
	
	boolean isOver();
	
	Set<Pair<Integer,Integer>> getPositions();
    
}
