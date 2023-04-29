package a02b.e1;

import java.util.Map;
import java.util.Set;

/**
 * The interface for a factory of Tournaments
 */
public interface TournamentFactory {
	
	/**
	 * @return a tournament
	 */
	Tournament make(String name, int year, int week, Set<String> players, Map<String,Integer> points);
}
