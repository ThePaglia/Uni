package a01b.sol1;

import java.util.*;
import java.util.stream.IntStream;

public class GridImpl<X> implements Grid<X> {
	
	private Map<Pair<Integer,Integer>,X> map = new HashMap<>();
	private int rows;
	private int columns;
	
	public GridImpl(int rows, int columns) {
		super();
		this.rows = rows;
		this.columns = columns;
	}

	@Override
	public int getRows() {
		return this.rows;
	}

	@Override
	public int getColumns() {
		return this.columns;
	}

	@Override
	public void setColumn(int column, X value) {
		for (int row=0; row<this.rows; row++) {
			this.map.put(new Pair<>(row,column),value);
		}
	}

	@Override
	public void setRow(int row, X value) {
		for (int column=0; column<this.columns; column++) {
			this.map.put(new Pair<>(row,column),value);
		}
	}
	
	@Override
	public X getValue(int row, int column) {
		return this.map.get(new Pair<>(row,column));
	}

	@Override
	public void setBorder(X value) {
		this.setRow(0, value);
		this.setRow(this.rows-1, value);
		this.setColumn(0, value);
		this.setColumn(this.columns-1, value);
	}

	@Override
	public void setDiagonal(X value) {
		for (int i=0; i<this.rows && i<this.columns; i++) {
			this.map.put(new Pair<>(i,i),value);
		}
	}

	@Override
	public Iterator<Cell<X>> iterator(boolean onlyNonNull) {
		return IntStream.range(0,this.rows)
						.boxed()
				        .flatMap(r-> IntStream.range(0,this.columns).boxed().map(c->new Pair<>(r,c)))
				        .map(p -> new Cell<>(p.getX(),p.getY(),map.get(p)))
				        .filter(p -> !onlyNonNull || p.getValue()!=null)
				        .iterator();
	}
}