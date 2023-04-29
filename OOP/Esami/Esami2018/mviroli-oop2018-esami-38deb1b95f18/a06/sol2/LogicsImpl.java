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
	public List<Integer> currentValues() {
		return Collections.unmodifiableList(values);
	}

	private void performHit(int elem) {
		this.values.set(elem,this.values.subList(0, elem+1).stream().mapToInt(i->i).sum());
	}
	
	@Override
	public int hit(int elem) {
		if (elem >= this.values.size()) {
			throw new IllegalArgumentException();
		}
		this.performHit(elem);
		return this.values.get(elem);
	}

	@Override
	public void reset() {
		Collections.fill(this.values,1);
	}
	
}
