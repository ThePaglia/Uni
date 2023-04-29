package a02b.sol1;

import java.util.Iterator;
import java.util.List;

/**
 * An interface modelling a pattern extractor, detecting certain subsequences into a sequence,
 * returning some information from each -- depending on the concrete implementation. 
 * 
 * @param <X>, the type of elements in the input sequence
 * @param <Y>, the type of elements returned for each subsequence found
 */
public interface PatternExtractor<X,Y>{
	
	/**
	 * @param input
	 * @return the list of elements corresponding to each pattern found 
	 */
	List<Y> extract(List<X> input);
}
