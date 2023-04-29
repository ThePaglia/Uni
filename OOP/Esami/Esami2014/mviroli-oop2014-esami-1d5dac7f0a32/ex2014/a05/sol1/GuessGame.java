/**
 * 
 */
package ex2014.a05.sol1;



public interface GuessGame {

	/**
	 * @param g the number you want to guess
	 * @return an {@link Answer}
	 */
	Answer guess(int g);
	
	/**
	 * @return the number of attempts made until now.
	 */
	int getGuesses();
	
	/**
	 * @return whether the game is over, either because we won or lost due to too many tries
	 */
	boolean isFinished();
	
	
}
