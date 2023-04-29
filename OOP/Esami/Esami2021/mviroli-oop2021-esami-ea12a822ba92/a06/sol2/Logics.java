package a06.sol2;

import java.util.Optional;

public interface Logics{

	boolean select(int x, int y);
	
	Optional<Pair<Integer,Integer>> advance();
	
}
