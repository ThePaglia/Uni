package a04.sol1;

/**
 * The player of a turnament
 */
public interface Player {
	
	/**
	 * @return a unique ID for the player
	 */
	int getId();
	
	/**
	 * @return the player's name
	 */
	String getName();
}
