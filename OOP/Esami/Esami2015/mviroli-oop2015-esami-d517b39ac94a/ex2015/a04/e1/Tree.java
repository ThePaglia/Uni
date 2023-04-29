package ex2015.a04.e1;

import java.util.List;

/* 
 * An immutable Tree of elements of type X, with no repetitions, in which each node has an ordered list of sons 
 */
public interface Tree<X> {
    
    /**
     * @return the number of elements in the tree
     */
    int size();
    
    /**
     * @return the root element of the tree
     * @throws IllegalStateException if the tree is empty
     */
    X getRoot();
    
    /**
     * @return the ordered list of sons (a defensive copy!)
     * @throws IllegalStateException if the tree is empty
     */    
    List<Tree<X>> getSons();
    
    /**
     * @return whether an element equal to x is contained somewhere in the tree
     */    
    boolean contains(X x);
    
    /**
     * @return the subtree (at any level of depth) having argument node as root, or null if none exists
     * @throws IllegalStateException if the tree is empty
     */    
    Tree<X> getSubTree(X node);
    
    /**
     * @return the list of elements contained in the tree (in any order)
     */    
    List<X> toList();
}
