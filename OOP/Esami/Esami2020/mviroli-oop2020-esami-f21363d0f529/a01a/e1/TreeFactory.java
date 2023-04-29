package a01a.e1;

import java.util.List;
import java.util.Set;

public interface TreeFactory {

	/**
	 * @param <E>
	 * @param root
	 * @return a tree with root and no children, represented with "root[]"
	 */
	<E> Tree<E> singleValue(E root);
	
	/**
	 * @param <E>
	 * @param root
	 * @param child1
	 * @param child2
	 * @return a tree with root and exactly those two children
	 */
	<E> Tree<E> twoChildren(E root, Tree<E> child1, Tree<E> child2);
	
	/**
	 * @param <E>
	 * @param root
	 * @param children, e.g. list [c1,c2,c3]
	 * @return a tree with root and single-valued children, represented with "root[c1[], c2[], c3[]]"
	 */
	<E> Tree<E> oneLevel(E root, List<E> children);
	
	/**
	 * @param <E>
	 * @param root
	 * @param list, e.g. list [e1,e2,e3]
	 * @return a tree with root that is actually a chain (each node has a single child)
	 * represented with "root[e1[e2[e3[]]]]"
	 * THIS IS OPTIONAL IN THIS EXAM!
	 */
	<E> Tree<E> chain(E root, List<E> list);
	
}
