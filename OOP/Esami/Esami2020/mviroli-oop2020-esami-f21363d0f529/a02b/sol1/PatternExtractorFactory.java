package a02b.sol1;

import java.util.function.Predicate;

/**
 * An interface modelling a factory of various kind of PatternExtractors,
 * all having in common the fact that non-empty subsequences (patterns) are formed by
 * consecutive elements with a similar characteristic (e.g., all being 0, all being >10, all not being ";")
 * and so on.
 */
public interface PatternExtractorFactory {
	
	/**
	 * @return a PatternExtractor of sizes of subsequences of zeros 
	 * e.g.: it will take sequence (10,0,0,20,30,0,0,0,40,0) and return sequence (2,3,1)
	 */
	PatternExtractor<Integer,Integer> countConsecutiveZeros();
	
	/**
	 * @param min
	 * @param max
	 * @return a PatternExtractor of average values of subsequences of elements in [min,max]
	 * Assume min=0, max=10, then, e.g.:  (1,2,-1,3,4,5,-1,6) --> (1.5,4,6) 
	 */
	PatternExtractor<Double,Double> averageConsecutiveInRange(double min, double max);
	
	/**
	 * @param separator
	 * @return a PatternExtractor of concatenations of subsequences of strings not being the separator
	 * Assume sep = ":", then, e.g.:  ("a","b",":","c","d","e",":","f") -> ("ab","cde","f")  
	 */
	PatternExtractor<String,String> concatenateBySeparator(String separator);
	
	/**
	 * @return a PatternExtractor of sums of all non-empty subsequences of strings that can be converted to double
	 * Recall that Double.parseDouble(s:String) converts a string to a double, or raises an exception if it can't be done
	 * e.g.:  ("10","20","a","40","b","40","50") -> (30,40,90)  
	 * THIS IS OPTIONAL IN THIS EXAM!
	 */
	PatternExtractor<String,Double> sumNumericStrings();

}
