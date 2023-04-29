package a03a.sol2;

import java.util.*;

public class LogicsImpl implements Logics {
	
	private final Map<Pair<Integer,Integer>,Integer> marks = new HashMap<>();
	private int size;
	
	public LogicsImpl(int size) {
		this.size = size;
	}

	@Override
	public void hit(int row, int col) {
		for (int i=row-1; i<=row+1; i++) {
			for (int j=col-1; j<=col+1; j++) {
				if (i>=0 && j>=0 && i<size && j<size) {
					marks.merge(new Pair<>(i,j), 1, (k,v)->k+v);
				}
			}
		}
	}
	
	@Override
    public int getMark(int x, int y) {
		return marks.getOrDefault(new Pair<>(x,y), 0);
	}

	@Override
	public boolean isDone() {
		return marks.size()==this.size*this.size;
	}
	
}
