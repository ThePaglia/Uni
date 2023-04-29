package a04.sol2;

import java.util.*;

/*
 * A model for Yahtzee
 * - when the object is created, getDices already provides 5 correctly-drawn dices
 */

public interface Logics {
	
	static enum Result {
		NOTHING,THREE,FOUR,FULL,YAHTZEE,STRAIGHT
	}
	
	void redraw(List<Boolean> b);
	
	List<Integer> getDices();
	
	Result getResult();
	
}
