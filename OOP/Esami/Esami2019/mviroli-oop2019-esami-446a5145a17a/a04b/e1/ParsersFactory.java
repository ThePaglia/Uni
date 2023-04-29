package a04b.e1;

/**
 * An interface to model a factory for various kinds of SequencesProvider
 */
public interface ParsersFactory {
	
	/**
	 * A parser for sequences of zero, one or many tokens t
	 * @param t
	 * @return a parser producing the length of the sequence
	 */
	Parser<Integer> createSequenceParserToCount(String t);
	
	/**
	 * A parser for one or many tokens t
	 * NOTE: this is optional for this exam!
	 * @param t
	 * @return a parser producing the concatenation of all tokens
	 */
	Parser<String> createNonEmptySequenceParserToString(String t);
	
	/**
	 * A parser for expressions made by addition ("+") or subtraction ("-") of numbers ("0" or "1")
	 * @return a parser producing the result of evaluating the expression
	 */
	Parser<Integer> createExpressionParserToResult();
}
