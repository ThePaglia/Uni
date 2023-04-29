package a03c.e1;

/**
 * An interface to model several factories of Parsers, with many opportunities of reuse, 
 * especially if one understands how to compose parsers.
 *
 */
public interface DeterministicParserFactory {

	/**
	 * @param s
	 * @return a parser consuming exactly one token equals to s
	 */
	DeterministicParser oneSymbol(String s);
	
	/**
	 * @param s1
	 * @param s2
	 * @return a parser consuming exactly one token equals to s1 and then one equals to s2
	 */
	DeterministicParser twoSymbols(String s1, String s2);
	
	/**
	 * @param s1
	 * @param s2
	 * @return a parser consuming a sequence of strings representing a (possibly empty) sequence of positive numbers, strictly increasing,
	 * e.g.: "10","20","30"
	 * Recall that conversion from String to int can be done with Integer.parseInt() method.
	 */
	DeterministicParser possiblyEmptyIncreasingSequenceOfPositiveNumbers();
	
	/**
	 * @param start
	 * @param stop
	 * @param delimiter
	 * @param element
	 * @return a parser consuming a sequence with start + element + delimiter + element + delimiter + ... + element + stop
	 * where at least one element is present
	 * e.g., by this method we could parse the tokenized string "[a;a;a;a]" (see the test)
	 */
	DeterministicParser sequenceOfParsersWithDelimiter(String start, String stop, String delimiter, DeterministicParser element);
	
	/**
	 * @param first
	 * @param second
	 * @return a parser consuming first and then second
	 */
	DeterministicParser sequence(DeterministicParser first, DeterministicParser second);
}
