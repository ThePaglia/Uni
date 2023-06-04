package a03b.sol1;

/**
 * An interface modelling a possibly empty lazy binary tree, that is, a binary tree where the two children 
 * of a node, called left and right, are computed only when/if needed -- in the same way a stream is a 
 * lazy list.
 * As such, a lazy tree could be an infinite one.
 * Note that the fact that it is lazy does not actually affects this interface, but rather its implementation
 */
public interface LazyTree<X> {

    /**
     * @return whether this tree is non empty, that is, at least it has one element, the root
     */
    boolean hasRoot();

    /**
     * @return the root of the tree
     * @throws NoSuchElementException if the tree was empty
     */
    X root();

    /**
     * @return the left child of the tree
     */
    LazyTree<X> left();

    /**
     * @return the right child of the tree
     */
    LazyTree<X> right();
    
}
