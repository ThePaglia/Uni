package a03b.sol2;

import java.util.Set;

public interface Logics{
	
	void tick();
	
	boolean isOver();
	
	Set<Pair<Integer,Integer>> getPositions();
    
}
