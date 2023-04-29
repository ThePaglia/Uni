package a02a.sol1;

import java.util.*;

/**
 * An interface to model the results of a tennis tournament, and the overall points a player
 * has after participating to the tournament.
 * Check TournamentBuilder to see how a Tournament should be created
 */
public interface Tournament {
	
	/**
	 * The result for a player: each result will give a percentage of the maximum points available
	 */
	enum Result {
		WINNER, // take 100% of points 
		FINALIST, // take 50% of points 
		SEMIFINALIST, // take 20% of points
		QUARTERFINALIST, // take 10% of points
		PARTICIPANT // take no points
	}
	
	/**
	 * The type of tournament, that is, the maximum number of points one can win
	 */
	enum Type {
		MAJOR, // winner takes 2500 
		ATP1000,  // winner takes 1000
		ATP500, // winner takes 500
		ATP250 // winner takes 250
	}
	
	/**
	 * @return the type of the tournament
	 */
	Type getType();
	
	/**
	 * @return the name of the tournament
	 */
	String getName();
		
	/**
	 * @param player
	 * @return the result of a given player, or 'empty' if he/she did not participate
	 */
	Optional<Result> getResult(String player);
	
	/**
	 * @return the winner of the tournament
	 */
	String winner();
	
	/**
	 * @return a map associating to each player the number of overall points he/she has before the tournament
	 */
	Map<String,Integer> initialRanking();
	
	/**
	 * @return a map associating to each player the number of overall points he/she has after the tournament
	 */
	Map<String,Integer> resultingRanking();
	
	/**
	 * @return the ordered list of players, from the top player down to the last one
	 */
	List<String> rank();
}
