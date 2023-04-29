package a05.sol2;

import java.util.*;

public interface Logics{
	
	Map<Pair<Integer,Integer>,Integer> items();
	
	void hit(int x, int y);
	
	boolean isOver();
}
