package a06.e1;

import java.util.List;

/**
 * An interface modelling a parser, namely, an object with the goal of accepting
 * one-by-one certain tokens (strings), until an end is reached. For instance:
 * a parser could accept sequences of 5 strings "0"; another a sequence starting
 * with "[", ending with "]", and having only "0" and "1" in between; and so on;
 */
public interface Parser {
	
	/**
	 * @param token, is the token we will make the parser accept
	 * @return true if this is accepted. If the answer is false, the parser
	 * remains in an unknown state.
	 */
	boolean acceptToken(String token);
	
	/**
	 * @return whether the parser has received all the expected tokens
	 */
	boolean inputCompleted();
	
	/**
	 * Brings the parser back to its initial state: it will start accepting
	 * tokens from the beginning, as if it were just being created. 
	 */
	void reset();

	
	/**
	 * A facility (template) method, already implemented
	 * @param list
	 * @return whether the parser can accept all tokens in the list, up to completion
	 */
	default boolean acceptAllToEnd(List<String> list) {
		return list.stream().allMatch(s -> this.acceptToken(s)) && this.inputCompleted();
	}

}
