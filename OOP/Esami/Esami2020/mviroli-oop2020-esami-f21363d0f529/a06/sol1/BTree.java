package a06.sol1;

import java.util.function.BinaryOperator;
import java.util.function.Function;

/**
 * the interface for a binary tree, where each leafs holds a value of type L
 * note a BTree is hence either a leaf, or a node with a left and right BTree
 */
public interface BTree<L> {

	/**
	 * @return if this node is just a leaf
	 */
	boolean isLeaf();
	
	/**
	 * @return the value held by this tree, if this is a leaf
	 * @throws NoSuchElementException if this tree is not a leaf
	 */
	L getLeaf();
	
	/**
	 * @return the left tree of this tree, if this is not a leaf
	 * @throws NoSuchElementException if this tree is a leaf
	 */
	BTree<L> getLeft();
	
	/**
	 * @return the right tree of this tree, if this is not a leaf
	 * @throws NoSuchElementException if this tree is a leaf
	 */
	BTree<L> getRight();
	
	/**
	 * @return the value held by this tree if this is a leaf,
	 * if it is not, it returns the result of applying compute to left and right,
	 * combined with the operator.
	 * For instance, applying operator + to tree ((1,2),3) gives 6.
	 * Applying - to tree ((1,2),3) gives -4
	 */
	L compute(BinaryOperator<L> function);
	
	/**
	 * @param <O>
	 * @param mapper
	 * @return a tree identical to this, but where each leaf is transformed by mapper
	 * For instance, applying i->i+1 to tree ((1,2),3) gives ((2,3),4)
	 */
	<O> BTree<O> map(Function<L,O> mapper);
	
	/**
	 * @return a tree obtained by swapping left with right recursively.
	 * For instance, the symmetric of ((1,2),3) is (3,(2,1))
	 */
	BTree<L> symmetric();
}
