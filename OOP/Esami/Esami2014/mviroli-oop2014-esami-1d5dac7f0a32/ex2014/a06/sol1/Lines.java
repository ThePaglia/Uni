/**
 * 
 */
package ex2014.a06.sol1;

import java.util.*;

public interface Lines {

	boolean isEmpty();
	
	boolean isTop();
	
	boolean isBottom();
	
	Optional<String> getCurrentString();
	
	void goUp();
	
	void goDown();
	
}
