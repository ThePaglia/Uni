package ex2015.a04.sol1;

import java.util.*;
import java.util.function.Supplier;

public class TreeImpl<X> implements Tree<X>{
    
    private X root;
    private List<Tree<X>> sons;
    
    public TreeImpl(X root, List<Tree<X>> sons){
        this.root = root;
        this.sons = sons;
        Objects.requireNonNull(sons);
    }
    
    @Override
    public int size() {
        return 1 + sons.stream().mapToInt(Tree::size).sum(); 
    }

    @Override
    public X getRoot() {
        return this.root;
    }

    @Override
    public List<Tree<X>> getSons() {
        return new ArrayList<>(this.sons);
    }

    @Override
    public boolean contains(X x) {
        return this.root.equals(x) || this.sons.stream().anyMatch(t -> t.contains(x));
    }
    
    @Override
    public Tree<X> getSubTree(X node){
        if (this.root.equals(node)){
            return this;
        }
        if (this.sons.isEmpty()){
            return null;
        }
        return this.sons.stream().map(t->t.getSubTree(node)).filter(t->t!=null).findAny().orElse(null);
    }

    @Override
    public List<X> toList() {
        final List<X> l = new ArrayList<>();
        l.add(this.getRoot());
        this.getSons().forEach(l2 -> l.addAll(l2.toList()));
        return l;
    }
}
