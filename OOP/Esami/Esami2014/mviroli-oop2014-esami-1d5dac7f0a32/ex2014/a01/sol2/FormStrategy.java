package ex2014.a01.sol2;

import java.util.*;

public interface FormStrategy {
	
	List<String> fieldNames();
	
	boolean fieldsFilter(Map<String,String> values);
	
	void onResult(Map<String,String> values);
}
