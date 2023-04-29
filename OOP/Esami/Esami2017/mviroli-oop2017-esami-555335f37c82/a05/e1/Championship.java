package a05.e1;

import java.util.Map;
import java.util.Set;

/**
 * A turnament of N teams, split into (N-1)*2 days. Days are organised following the ordering that one can
 * obtain from the algorithm in class Combinations.
 * Teams are first registered (registerTeam), then the championship can start (startChampionship).
 * Each day of the championship is to be explicitly started (newDay)
 * At any given point one can know what are the matches still to play in this day (pendingMatches).
 * A match can be given a result (matchPlay).
 * A classification can be extracted at any point (3 points for the winner, 0 for the loser, 1 for even games).
 * We can recognise that the championship is over by championshipOver.
 */

public interface Championship {

	/**
	 * @param name of the team to be registered
	 */
	void registerTeam(String name);
	
	/**
	 * starts the championship, teams can no longer register
	 */
	void startChampionship();
	
	/**
	 * a new day (giornata) is started (the previous one, if any, is assumed to be completed) 
	 */
	void newDay();

	/**
	 * @return the matches that have to be completed in this day
	 */
	Set<Match> pendingMatches();
	
	/**
	 * Ends a match, giving the result
	 * 
	 * @param match
	 * @param homeGoals (goal della squadra in casa)
	 * @param awayGoals (goal della squadra in trasferta)
	 */
	void matchPlay(Match match, int homeGoals, int awayGoals);
		
	/**
	 * @return a map from teams to their points in the classification
	 */
	Map<String,Integer> getClassification();
	
	/**
	 * @return whether this championship is concluded 
	 */
	boolean championshipOver();
	
}
