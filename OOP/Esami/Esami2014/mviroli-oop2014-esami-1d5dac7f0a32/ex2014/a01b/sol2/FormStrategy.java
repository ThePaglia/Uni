package ex2014.a01b.sol2;

import java.util.*;

public interface FormStrategy<R> {
	
	List<FormField> fields();
	
	R result(List<String> values);
}
