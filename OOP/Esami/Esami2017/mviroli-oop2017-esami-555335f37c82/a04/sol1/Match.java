package a04.sol1;

/**
 * A match of a turnament, based on the two players
 */
public interface Match {

	/**
	 * @return a player of the match, namely,  the one who registered first
	 */
	Player getFirstPlayer();

	/**
	 * @return the other player
	 */
	Player getSecondPlayer();
}
