package ex2014.a03.e1;

import java.io.*;
import java.util.*;

/**
 * An interface to generate long series of random numbers in [0,1],
 * with some methods to extract information from this series. Such
 * methods should not put all elements into a collection, but 
 * compute the result while reading all elements.
 * 
 */

public interface RandomStatistics{

	/**
	 * @param fileName, the name of the file used to store data
	 */	
	void setFileName(String fileName);
	
	/**
	 * Generates a series of random numbers, stores it into
	 * the file, and then close the file.
	 * 
	 * @param size, the number of random numbers to generate
	 */	
	void storeSeries(long size) throws IOException;
	
	/**
	 * Opens the file, reads all its numbers, closes the file, and returns their maximum.
	 * 
	 * @return the maximum element in the series
	 */	
	double loadToMax() throws IOException;
	
	/**
	 * Opens the file, reads all its numbers, closes the file, and returns the
	 * number of element which was smaller then the argument.
	 * 
	 * @param threshold, a number in between 0 and 1
	 * @return the number of elements smaller than threshold
	 */	
	long loadToCountSmaller(double threshold) throws IOException;
	
	/**
	 * Opens the file, reads all its numbers, closes the file, and returns a list as follows:
	 * - its number of elements is the argument r
	 * - consider the interval [0,1] split in r equal intervals 
	 *   (e.g. with r=4 they are [0,0.25],[0.25,0.5],[0.5,0.75],[0.75,1])
	 * - the i-th element in the list counts how many random numbers are in the i-th interval
	 * 
	 * @param r, the number of intervals to consider
	 * @return the number of elements smaller than threshold
	 */	
	List<Long> loadToList(int r) throws IOException;
	
	
}
