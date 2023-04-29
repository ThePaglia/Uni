package a05.sol2;

import java.util.*;

/*
 * A model for 1-to-1 risiko game
 * - when the object is created, getDices already provides 5 correctly-drawn dices
 */

public interface Logics {
	
	static enum Player {
		A, B
	}
	
	int getPoints(Player player);
	
	EnumMap<Player,Integer> draw();
	
	Optional<Player> winner();
}
