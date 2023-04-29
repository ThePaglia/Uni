package a01b.sol1;


/**
 * A simple class to represent position and value of a cell.
 * Namely, a record of a row, a column and a value.
 *
 * @param <X> is the type of element of a cell
 */
public class Cell<X> {
	
	private int row;
	private int column;
	private X value;
	
	public Cell(int row, int column, X value) {
		super();
		this.row = row;
		this.column = column;
		this.value = value;
	}

	public int getRow() {
		return this.row;
	}

	public int getColumn() {
		return this.column;
	}

	public X getValue() {
		return this.value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Cell)) {
			return false;
		}
		Cell other = (Cell) obj;
		if (column != other.column) {
			return false;
		}
		if (row != other.row) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Cell [row=" + row + ", column=" + column + ", value=" + value + "]";
	}

	
}
