package ex2014.a03.sol2;

import java.util.*;

/*
 * A model for SelectorGUI. We suggest using methods to 
 * - return the String that each button should show at a given time,
 * - return the enabling state of each button (true/false)
 * - notify that a button has been pressed
 * - return the final string to show
 */

public interface SelectorModel {
	
	int size();
	
	List<String> currentNames();
	
	List<Boolean> currentEnabling();
	
	void hit(int elem);
		
	String result();
}
