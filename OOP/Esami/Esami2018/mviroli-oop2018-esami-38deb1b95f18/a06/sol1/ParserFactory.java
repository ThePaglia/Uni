package a06.sol1;

import java.util.Set;

/**
 * A factory to create many types of parser
 */
public interface ParserFactory {
	
	/**
	 * @return a parser consuming only one token (and then being completed..)
	 */
	Parser one(String token);
	
	/**
	 * @return a parser consuming exactly 'elemCount' copies of token
	 */
	Parser many(String token, int elemCount);
	
	/**
	 * @return a parser consuming only one element (and then being completed): this element should be in 'set'
	 */
	Parser oneOf(Set<String> set);
	
	/**
	 * @return a parser first consuming token1, then token2, and then being completed
	 */
	Parser sequence(String token1, String token2);
	
	/**
	 * For instance, fullSequence("[",{"0"},";","]",3) should accept sequence: "[" "0" ";" "0" ";" "0" "]"
	 * 
	 * @return a parser consuming 'begin', 'elemCount' copies of any item in 'elem', separated by 'separator', and finishing with 'end'
	 */
	Parser fullSequence(String begin, Set<String> elem, String separator, String end, int elemCount);
}
