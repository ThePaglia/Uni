package a03b.e1;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * An interface modelling a factory of LazyTree. As in most factories,
 * the call of a method leaves no side-effect.
 */
public interface LazyTreeFactory {

    /**
     * @param <X>
     * @param value
     * @return an infinite tree in all directions, having value in all the nodes
     */
    <X> LazyTree<X> constantInfinite(X value);
    
    /**
     * @param <X>
     * @param root
     * @param map
     * @return a tree created recursively, with given root, and with left and right children
     * obtained by getting a pair from the map.
     * E.g. with root 10 and Map [10->(0,20), 20->(15,25)], we model a tree with 10 as root, left child
     * 0, right child 20, left child of the right child 15, and so on.
     */
    <X> LazyTree<X> fromMap(X root, Map<X,Pair<X,X>> map);

    /**
     * @param <X>
     * @param root
     * @param leftSupp
     * @param rightSupp
     * @return a tree built using a very general and reusable construction: 
     * - if root is empty the tree is empty
     * - if root is present it creates a tree with that root, and with left and right subtrees
     * obtained by the suppliers provided as argument
     */
    <X> LazyTree<X> cons(Optional<X> root, Supplier<LazyTree<X>> leftSupp, Supplier<LazyTree<X>> rightSupp);

    /**
     * @param <X>
     * @param root
     * @param leftOp
     * @param rightOp
     * @return a tree created recursively, with given root, and with left and right children
     * obtained by applying the unary operators to the root.
     */
    <X> LazyTree<X> fromTwoIterations(X root, UnaryOperator<X> leftOp, UnaryOperator<X> rightOp);

    /**
     * @param <X>
     * @param tree
     * @param bound
     * @return a version of the input tree that is cut so that the tree depth is at most bound,
     * namely, no node has depth greater than bound. For instance, cutting with bound 0 gives an empty
     * tree, cutting with bound 1 gives a tree with (at most) just the root, cutting with bound 2 gives 
     * a tree with (at most) just the root and two children, and so on.
     */
    <X> LazyTree<X> fromTreeWithBound(LazyTree<X> tree, int bound);

}
