package a06.sol1;

import java.util.NoSuchElementException;
import java.util.function.BinaryOperator;
import java.util.function.Function;

class Leaf<L> implements BTree<L>{
	private L value;
	
	public Leaf(L value) {
		this.value = value;
	}

	@Override
	public boolean isLeaf() {
		return true;
	}

	@Override
	public L getLeaf() {
		return this.value;
	}

	@Override
	public BTree<L> getLeft() {
		throw new NoSuchElementException();
	}

	@Override
	public BTree<L> getRight() {
		throw new NoSuchElementException();
	}

	@Override
	public L compute(BinaryOperator<L> function) {
		return this.value;
	}

	@Override
	public <O> BTree<O> map(Function<L,O> mapper) {
		return new Leaf<>(mapper.apply(this.value));
	}
	
	@Override
	public BTree<L> symmetric() {
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Leaf other = (Leaf) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "'" + value;
	}	
}