package ex2015.a01a.sol2;

import java.util.*;

public interface Model {
	
	List<String> getValues();
	
	List<String> getOperations();
	
	void hitValue(String s);
	
	String execOperation(String s);
	
	void resetValues();
}
