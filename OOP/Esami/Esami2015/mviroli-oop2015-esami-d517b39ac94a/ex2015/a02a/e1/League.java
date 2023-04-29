package ex2015.a02a.e1;

import java.util.*;

/**
 * An interface modelling a football league
 *
 */
public interface League {
	
    // Adds a team to the league. This works only if called before the league started!
	void addTeam(String teamName);
	
	// Starts the league
	void start();
	
	// Adds complete results of a day, in the form of a map [TeamA,TeamB] -> [GoalsTeamA,GoalsTeamB]
	void storeResults(Map<Pair<String,String>,Pair<Integer,Integer>> results);
	
	// Gets the table of overall points, mapping each team to its points 
	// Note: winning a game gives 3 points, even (pari) is 1 point, losing a game is 0 points)
    Map<String,Integer> getTable();
	
}