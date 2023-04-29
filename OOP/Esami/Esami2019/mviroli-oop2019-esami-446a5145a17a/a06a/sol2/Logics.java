package a06a.sol2;

import java.util.Optional;

public interface Logics{
	
	void select(int x, int y);
	
	boolean isOver();
	
	Optional<Integer> getElement(int x, int y);
    
}
