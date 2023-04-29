package a04.sol1;

import java.util.List;
import java.util.Set;

/**
 * A turnament of 2, 4, 8, 16, 32 or 64 players (or 128..), namely, with final, semifinals, quarterfinals,..
 * Players are first created (makePlayer), then registered (registerPlayer).
 * The turnament can then start (startTurnament).
 * At any given point one can know what are the matches still to play (getPendingGames)
 * Note that semifinals can start only when all quarterfinals are over, quarterfinals can start only when
 * eight-finals are over, and so on.
 * When a match is over we only need to know who won the match.
 */
public interface Turnament {
	
	/**
	 * @return a player (typically just instantiating PlayerImpl)
	 */
	Player makePlayer(int id, String name);
	
	/**
	 * @return a match (typically just instantiating MatchImpl).. used only for testing purpose
	 */
	Match makeMatch(Player p1, Player p2);
	
	/**
	 * @param player is the player added (from top to bottom) to the turnament table
	 */
	void registerPlayer(Player player);
	
	/**
	 * The turnament can start. Note that:
	 * - we assume the number of register players is one of 2,4,8,16,32,..
	 * - after the turnament is started methods getPengingGames/playMatch are ready to work!
	 */
	void startTurnament();
	
	/**
	 * @return all registered players
	 */
	List<Player> getPlayers();
	
	/**
	 * @return all games that are planned at this stage and still to be concluded
	 * For instance, at the beginning of quarterfinals, we have to 4 matches.
	 * When all 4 have been played, pending matches should be 2 since we automatically entered semifinals
	 */
	List<Match> getPendingGames();
	
	/**
	 * @param match is the match just finished
	 * @param winner is the winner
	 */
	void playMatch(Match match, Player winner);
	
	/**
	 * @return whether the final has finished
	 * This method is "optional"
	 */
	boolean isTurnamentOver();
	
	/**
	 * @return the winner
	 * This method is "optional"
	 */
	Player winner();
	
	/**
	 * @param player
	 * @return the set of opponents of a player so far in this turnament, with no order
	 * This method is "optional"
	 */
	Set<Player> opponents(Player player);

}
