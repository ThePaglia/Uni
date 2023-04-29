package a04b.sol1;

import java.util.List;

/**
 * An interface of a parser that consumes a finite sequence of tokens (strings) and, 
 * if its syntax/structure is ok, gives a result of type R.
 * For instance, we could implement a parser that takes sequences of kind "a","a","a",..,"a", and 
 * returns the length of the sequence (an int), or that takes a sequence of kind "1","+","1","+","0" and
 * returns the result of the evaluation of the described expression (an int, 2 in that case).  
 */
public interface Parser<R> {
	
	/**
	 * Note: if the token is not accepted, this parser becomes "broken"
	 * and should not be used further.. raising an exception if you want to extract a result
	 * @param token
	 * @return true if the token was expected, false otherwise 
	 */
	boolean getNext(String token);
	
	/**
	 * @param tokens
	 * @return true if all tokens are correctly consumed one after the other 
	 */
	boolean getAllInList(List<String> tokens);
		
	/**
	 * to be called when there are no more tokens to consider
	 * @return the result of parsing
	 * @throws an IllegalStateException if parsing cannot be completed, or if some token 
	 * was not accepted at some point 
	 */
	R completeAndCreateResult();
}
