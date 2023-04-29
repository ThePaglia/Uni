package ex2015.a04.sol1;

import java.util.*;
import java.util.function.Supplier;

public class TreeFactoryImpl<X> implements TreeFactory<X> {

    @Override
    public Tree<X> emptyTree() {
        return new EmptyTree<>();
    }

    @Override
    public Tree<X> consTree(X root, List<Tree<X>> sons) {
        return new TreeImpl<>(root, sons == null ? Collections.emptyList() : new ArrayList<>(sons));
    }
    
    private static class EmptyTree<X> implements Tree<X>{

        private static Supplier<RuntimeException> EXC_EMPTY = ()->new IllegalStateException();
              
        @Override
        public int size() {
            return 0; 
        }

        @Override
        public X getRoot() {
            throw EXC_EMPTY.get();
        }

        @Override
        public List<Tree<X>> getSons() {
            throw EXC_EMPTY.get();
        }

        @Override
        public boolean contains(X x) {
            return false;
        }
        
        @Override
        public Tree<X> getSubTree(X node){
            throw EXC_EMPTY.get();
        }

        @Override
        public List<X> toList() {
            return Collections.emptyList();
        }
        
    }

}
