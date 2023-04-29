package a06.sol1;

import java.util.NoSuchElementException;
import java.util.function.BinaryOperator;
import java.util.function.Function;

class Cons<L> implements BTree<L>{
	private final BTree<L> left;
	private final BTree<L> right;
	
	public Cons(BTree<L> left, BTree<L> right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public L getLeaf() {
		throw new NoSuchElementException();
	}

	@Override
	public BTree<L> getLeft() {
		return left;
	}

	@Override
	public BTree<L> getRight() {
		return right;
	}

	@Override
	public L compute(BinaryOperator<L> function) {
		return function.apply(left.compute(function),right.compute(function));
	}

	@Override
	public <O> BTree<O> map(Function<L,O> mapper) {
		return new Cons<>(left.map(mapper),right.map(mapper));
	}

	@Override
	public BTree<L> symmetric() {
		return new Cons<>(right.symmetric(),left.symmetric());
	}

	@Override
	public String toString() {
		return "(" + left + ", " + right + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
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
		Cons other = (Cons) obj;
		if (left == null) {
			if (other.left != null)
				return false;
		} else if (!left.equals(other.left))
			return false;
		if (right == null) {
			if (other.right != null)
				return false;
		} else if (!right.equals(other.right))
			return false;
		return true;
	}

	
}