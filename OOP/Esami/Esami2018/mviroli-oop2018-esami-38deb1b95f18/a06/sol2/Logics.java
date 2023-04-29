package a06.sol2;

import java.util.*;

public interface Logics {
	
	int size();
	
	List<Integer> currentValues();
	
	int hit(int elem);
		
	void reset();
}
