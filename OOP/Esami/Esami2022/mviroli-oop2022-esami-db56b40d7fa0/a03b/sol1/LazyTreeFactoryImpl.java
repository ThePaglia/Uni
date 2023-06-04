package a03b.sol1;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class LazyTreeFactoryImpl implements LazyTreeFactory {

    @Override
    public <X> LazyTree<X> cons(Optional<X> root, Supplier<LazyTree<X>> left, Supplier<LazyTree<X>> right) {
        return new LazyTree<X>() {

            @Override
            public boolean hasRoot() {
                return root.isPresent();
            }

            @Override
            public X root() {
                return root.get();
            }

            @Override
            public LazyTree<X> left() {
                if (!hasRoot()){
                    throw new NoSuchElementException();
                }
                return left.get();
            }

            @Override
            public LazyTree<X> right() {
                if (!hasRoot()){
                    throw new NoSuchElementException();
                }
                return right.get();
            }
            
        };
    }

    private <X> LazyTree<X> empty() {
        return cons(Optional.empty(),null,null);
    }

    @Override
    public <X> LazyTree<X> fromMap(X root, Map<X, Pair<X, X>> map) {
        var p = map.get(root);
        return this.cons(
            Optional.of(root), 
            () -> p == null ? empty() : fromMap(p.getX(),map),
            () -> p == null ? empty() : fromMap(p.getY(),map));
    }

    @Override
    public <X> LazyTree<X> fromTwoIterations(X root, UnaryOperator<X> left, UnaryOperator<X> right) {
        return cons(Optional.of(root), 
            () -> fromTwoIterations(left.apply(root), left, right),
            () -> fromTwoIterations(right.apply(root), left, right)
        );
    }

    @Override
    public <X> LazyTree<X> constantInfinite(X value) {
        return this.fromTwoIterations(value, v -> v, v -> v);
    }

    @Override
    public <X> LazyTree<X> fromTreeWithBound(LazyTree<X> tree, int bound) {
        if (bound == 0 || !tree.hasRoot()){
            return this.empty();
        }
        return cons(Optional.of(tree.root()), () -> fromTreeWithBound(tree.left(), bound - 1), () -> fromTreeWithBound(tree.right(),bound-1));
    }    
}
