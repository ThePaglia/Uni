package a02b.sol1;

import java.util.*;

/**
 * An interface to model the results of a tennis tournament.
 * Check TournamentFactory to see how a Tournament should be created
 */
public interface Tournament {
	
	/**
	 * @return the unique name of the tournament
	 */
	String getName();
	
	/**
	 * @return the year in which the tournament was hosted
	 */
	int getYear();
		
	/**
	 * @return the week of the year in which the tournament was hosted (in range: 0..51)
	 */
	int getWeek();

	/**
	 * @return the players at the tournament
	 */
	Set<String> getPlayers();
	
	/**
	 * @param player
	 * @return the points won by a given player, or 'empty' if he/she did not participate
	 */
	Optional<Integer> getResult(String player);
	
	/**
	 * @return the winner of the tournament, namely, the player who won the most points
	 */
	String winner();
	
}
