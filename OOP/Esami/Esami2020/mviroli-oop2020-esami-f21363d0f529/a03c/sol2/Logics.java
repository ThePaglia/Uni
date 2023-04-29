package a03c.sol2;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface Logics{
	
	List<Pair<Integer,Integer>> getPositions();
	
	boolean hit(Pair<Integer,Integer> position);
	
	boolean next();
	
}
