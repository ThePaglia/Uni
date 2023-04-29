package a06.sol2;

import java.util.*;
import java.util.stream.*;

public class LogicsImpl implements Logics {
	
	private final List<Integer> values;
	
	public LogicsImpl(int size) {
		this.values = new ArrayList<>(Collections.nCopies(size,1));
	}
	
	@Override
	public int size() {
		return this.values.size();
	}

	@Override
	public List<String> currentNames() {
		return values.stream().map(String::valueOf).collect(Collectors.toList());
	}

	@Override
	public String hit(int elem) {
		int n = this.values.get(elem) + 
				(elem > 0 ? this.values.get(elem-1) : 0) +
				(elem < size()-1 ? this.values.get(elem+1) : 0);
		this.values.set(elem,n);
		return String.valueOf(n);
	}

	@Override
	public void reset() {
		Collections.fill(this.values,1);
	}
	
}
