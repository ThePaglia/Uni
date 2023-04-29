package ex2015.a04.e1;

import java.util.List;

/**
 * A factory for immutable trees with element type X
 */
public interface TreeFactory<X> {
    
    
    /**
     * @return an empty tree (that is, with 0 elements)
     */
    Tree<X> emptyTree();
    
    /**
     * @return a tree with given root and list of sons
     * Note that it is not needed to check that all sons have different elements
     * sons can be null, meaning an empty list of sons
     */
    Tree<X> consTree(X root, List<Tree<X>> sons);

}
