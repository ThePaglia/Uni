package a02a.e1;

import java.util.Map;

/**
 * The interface for a builder of Tournament, with fluid interface
 * If something goes wrong in the ways the methods are called, IllegalStateException is to be called.
 * Note: managing of some of such Exceptions are optional in this exam
 */
public interface TournamentBuilder {
	
	/**
	 * Sets the type, which is mandatory and can't be null!
	 */
	TournamentBuilder setType(Tournament.Type type);
	
	/**
	 * Sets the name, which is mandatory and can't be null!
	 */
	TournamentBuilder setName(String name);
		
	/**
	 * Sets the ranking before the tournament starts, this is mandatory and can't be null!
	 * It should be called only after setType is called
	 */
	TournamentBuilder setPriorRanking(Map<String,Integer> ranking);
	
	/**
	 * Adds the result of a player at this tournament.
	 * It should be called only after ranking has been set.
	 * Note that a player can have only a single result, and one player should be the winner.
	 */
	TournamentBuilder addResult(String player, Tournament.Result result);
	
	/**
	 * If everything is ok, it creates the Tournament, and from then this object is useless 
	 */
	Tournament build();
}
