package a06.e1;

import java.util.List;
import java.util.function.BiFunction;

public interface BTreeFactory {
	
	/**
	 * @param <L>
	 * @param value
	 * @return a tree holding only value
	 */
	<L> BTree<L> leaf(L value);
	
	/**
	 * @param <L>
	 * @param left
	 * @param right
	 * @return a tree made by left and right trees
	 */
	<L> BTree<L> compose(BTree<L> left, BTree<L> right);
	
	/**
	 * @param <L>
	 * @param leafs
	 * @return a tree whose leafs are exactly those in the argument, orderly.
	 * If input is list [1,2,3,4] it could return (1,(2,(3,4))) or (((1,2),3),4) or ((1,2),(3,4)), at choice
	 */
	<L> BTree<L> nested(List<L> leafs);
}
