package a02b.sol2;

import java.util.*;

public class LogicsImpl implements Logics {
	
	private Optional<Pair<Integer,Integer>> firstSelected = Optional.empty();
	private Set<Pair<Integer,Integer>> marks = new HashSet();

	@Override
	public void hit1(int row, int col) {
		if (this.firstSelected.isPresent()) {
			throw new IllegalStateException("first mark already selected");
		}
		this.firstSelected = Optional.of(new Pair<>(row,col));
	}
	@Override
	public boolean hit2(int row, int col) {
		if (!this.firstSelected.isPresent()) {
			throw new IllegalStateException("first mark not selected");
		}
		int r2 = firstSelected.get().getX();
		int c2 = firstSelected.get().getY();
		this.firstSelected = Optional.empty();
		return this.draw(Math.min(row,r2),Math.min(col, c2),Math.max(row, r2),Math.max(col,c2));
	}
	
	private boolean draw(int r1, int c1, int r2, int c2) {
		boolean changed = false;
		for (int r=r1; r<=r2; r++) {
			changed = changed | this.marks.add(new Pair<>(r,c1));
			changed = changed | this.marks.add(new Pair<>(r,c2));
		}
		for (int c=c1; c<=c2; c++) {
			changed = changed | this.marks.add(new Pair<>(r1,c));
			changed = changed | this.marks.add(new Pair<>(r2,c));
		}
		return !changed;
	}
	
	@Override
	public Iterable<Pair<Integer,Integer>> marksIterator(){
		return Collections.unmodifiableCollection(this.marks);
	}
}
