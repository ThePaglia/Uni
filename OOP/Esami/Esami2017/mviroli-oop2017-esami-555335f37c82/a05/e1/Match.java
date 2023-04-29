package a05.e1;

/**
 * A match of a championship, based on two teams (home and away)
 */
public interface Match {

	/**
	 * @return the home team (squadra in casa)
	 */
	String getHomeTeam();

	/**
	 * @return the away team (squadra fuori casa)
	 */
	String getAwayTeam();
}
