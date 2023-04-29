/**
 * 
 */
package ex2014.a05.sol1;

import java.util.Random;


public class GuessGameImpl implements GuessGame {
	
	private static final int MAX_GUESSES = 8;
	private final Random rnd = new Random();
	private final int expected;
	private boolean won;
	private int guesses;
	
	/**
	 * Creates a new {@link GuessGame} where the number to guess lies between min (included) and max (included).
	 * 
	 * @param min minimum value
	 * @param max maximum value
	 */
	public GuessGameImpl(final int min, final int max) {
		this.expected = min + this.rnd.nextInt(max-min+1);
		System.out.println("Expected: "+this.expected);
	}

	@Override
	public Answer guess(final int g) {
		guesses++;
		if (g == expected) {
			won = true;
			return Answer.CORRECT;
		}
		return g > expected ? Answer.TOO_HIGH : Answer.TOO_LOW;
	}


	@Override
	public int getGuesses() {
		return guesses;
	}

	@Override
	public boolean isFinished() {
		return won || guesses>=MAX_GUESSES;
	}

}
