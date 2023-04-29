package a01a.e1;

import java.util.List;
import java.util.Set;

/**
 * An interface for immutable n-ary trees, each node holding a value of type E 
 * @param <E>
 */
public interface Tree<E> {
	
	/**
	 * @return the root of the tree
	 */
	E getRoot();
	
	/**
	 * @return a list of children of the root element
	 */
	List<Tree<E>> getChildren();
	
	/**
	 * @return a set (namely we do not care of order) of all leafs
	 * recall that a leaf is an element with no children
	 * recall that the only leaf of a tree with no children is the root itself
	 * recall that the leafs of a tree with children is the set of leafs of all children
	 */
	Set<E> getLeafs();
	
	/**
	 * @return a set (namely we do not care of order) of all elements in the tree
	 * recall that the elements of a tree are the root, plus the elements of all children
	 */
	Set<E> getAll();
	
	/**
	 * @return a string representation of the kind "root[...]", where "..." is
	 * a ", "-separated list of representations of children. For instance: a[b[], c[], d[e[], f[]]]
	 * note that this toString is very similar to what Eclipse would create 
	 * THIS IS OPTIONAL IN THIS EXAM!
	 */
	@Override 
	String toString();
	
}
