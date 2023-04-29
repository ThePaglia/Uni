package ex2014.a03b.e1;

import java.io.*;
import java.util.*;

/**
 * An interface to generate long series of results of throwing N dice,
 * with some methods to extract information from this series. Such
 * methods should not put all elements into a collection, but 
 * compute the result while reading all elements.
 */

public interface DiceStatistics{

	/**
	 * @param fileName, the name of the file used to store data
	 */	
	void setFileName(String fileName);
	
	/**
	 * Generates a series of results of throwing diceNumber dice, stores it into
	 * the file, and then close the file. 
	 * For instance, the result of throwing 3 dice, is obtained by summing
	 * three random integers in between 1 and 6.
	 * 
	 * @param size, the number of random numbers to generate
	 * @param diceNumber, the number of dice to throw each time
	 */	
	void storeSeries(long size,int diceNumber) throws IOException;
	
	/**
	 * Opens the file, reads all its numbers, closes the file, and returns the number
	 * of those which correspond to all dice giving 6.
	 * 
	 * @return the number of elements corresponding to all dice giving 6
	 */	
	long loadToLuckyCount()  throws IOException;
	
	/**
	 * Opens the file, reads all its numbers, closes the file, and returns the number
	 * of those which are odd (dispari)
	 * 
	 * @return the number of odd elements
	 */	
	long loadToOddCount() throws IOException;
	
	/**
	 * Opens the file, reads all its numbers, closes the file, and returns a map 
	 * associating to each result the number of elements which are equal to the result. 
	 * 
	 * @return the map
	 */	
	Map<Integer,Long> loadToMap() throws IOException;
	
}
