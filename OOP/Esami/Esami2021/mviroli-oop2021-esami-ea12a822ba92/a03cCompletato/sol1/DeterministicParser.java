package a03c.sol1;

import java.util.List;
import java.util.Optional;

/**
 * This interface models a parser, deterministic in the sense that it provides
 * just one result of parsing. A parser takes a list of tokens (short and atomic pieces of strings),
 * and recognises the structure of an initial part of such list, depending on the implementation logic.
 * For instance, the parser of a < non empty sequence of "a" >, would take the list
 * ("a","a","a","a","b","c"), and return ("b","c"). Instead, the same parser
 * with the input ("b","c","a","a","b","c") would fail, since it is not able to parse 
 * what needed. Note that the parser of a < possibly empty sequence of "a" > with the
 * input ("b","c","a","a","b","c") would succeed, returning the same list ("b","c","a","a","b","c")
 * -- since an empty sequence of "a" has been parsed.
 *
 */
@FunctionalInterface
public interface DeterministicParser { 
	
	/**
	 * @param tokens is the list of tokens
	 * @return the rest of the list of tokens after parsing if there was success, or the empty optional if it failed 
	 */
	Optional<List<String>> accepts(List<String> tokens);
}
