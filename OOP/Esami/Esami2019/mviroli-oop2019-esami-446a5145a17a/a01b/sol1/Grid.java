package a01b.sol1;

import java.util.Iterator;

public interface Grid<E> {

	/**
	 * @return the number of rows
	 */
	int getRows();
	
	/**
	 * @return the number of columns
	 */
	int getColumns();
	
	/**
	 * @param row
	 * @param column
	 * @return gets the value of the corresponding cell, or null if none is defined
	 */
	E getValue(int row, int column);
	
	/**
	 * sets all values of the column with index 'column' to 'value'
	 */
	void setColumn(int column, E value);
	
	/**
	 * sets all values of the row with index 'row' to 'value'
	 */
	void setRow(int row, E value);
	
	/**
	 * sets all values of the border of the grid to 'value'
	 */
	void setBorder(E value);
	
	/**
	 * sets all values of the principal diagonal to 'value'
	 */
	void setDiagonal(E value);
	
	/**
	 * @param onlyNonNull
	 * @return iterates row-by-row the grid, excluding empty cells if the argument is true
	 * IT IS IN THE OPTIONAL PART OF THE EXAM!
	 */
	Iterator<Cell<E>> iterator(boolean onlyNonNull);
}
