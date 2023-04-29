package a02b.sol1;

import java.util.*;

/**
 * An interface to model all informations concerning past tournaments
 * Whenever timing of tournaments is not correct an IllegalStateException is to be raised
 * Note: implementing the management of Exceptions and the last two methods are optional in this exam 
 */
public interface Ranking {

	/**
	 * @param tournament
	 * Gets all information from tournament
	 * Note this tournament should not be in the past of an existing tournament
	 */
	void loadTournament(Tournament tournament);
	
	/**
	 * @return the week of the last tournament loaded
	 */
	int getCurrentWeek();
	
	/**
	 * @return the year of the last tournament loaded
	 */
	int getCurrentYear();
	
	/**
	 * @param player
	 * @return the overall points won by player considering only the tournaments in last year
	 * For instance, if the last tournament was on week 20 of year 2019, we will only consider
	 * tournaments happened in week 21 of 2018 or later.
	 * If player did not play, it returns 0
	 */
	Integer pointsFromPlayer(String player);
	
	/**
	 * @return a list of players, ordered by the point they won in last year
	 */
	List<String> ranking();
	
	/**
	 * @return a map associating the name of a tournament to its winner (only in the last year)
	 */
	Map<String,String> winnersFromTournamentInLastYear();
	
	/**
	 * @return a map associating the name of a tournament to the points won there by a player who participated (ever)
	 */
	Map<String,Integer> pointsAtEachTournamentFromPlayer(String player);
	
	/**
	 * @return a list with the same info as of method pointsAtEachTournamentFromPlayer, but ordered chronologically
	 */
	List<Pair<String,Integer>> pointsAtEachTournamentFromPlayerSorted(String player);
	
}
