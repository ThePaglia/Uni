package a03b.e1;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class LazyTreeFactoryImpl implements LazyTreeFactory {

    @Override
    public <X> LazyTree<X> constantInfinite(X value) {
        return new LazyTree<X>() {
            Optional<X> x = Optional.of(value);
            @Override
            public boolean hasRoot() {
                return x.isPresent();
            }

            @Override
            public X root() {
                return x.get();
            }

            @Override
            public LazyTree<X> left() {
                return this;
            }

            @Override
            public LazyTree<X> right() {
                return this;
            }

        };
    }

    @Override
    public <X> LazyTree<X> fromMap(X root, Map<X, Pair<X, X>> map) {
        return new LazyTree<X>() {
            Optional<X> x = root != null ? Optional.of(root) : Optional.empty();
            @Override
            public boolean hasRoot() {
                return x.isPresent();
            }

            @Override
            public X root() {
                return x.get();
            }

            @Override
            public LazyTree<X> left() {
                if (!map.containsKey(x.get())) {
                    return fromMap(null, map);
                }
                return fromMap(map.get(x.get()).getX(), map);
            }

            @Override
            public LazyTree<X> right() {
                if (!map.containsKey(x.get())) {
                    return fromMap(null, map);
                }
                return fromMap(map.get(x.get()).getY(), map);
            }

        };
    }

    @Override
    public <X> LazyTree<X> cons(Optional<X> root, Supplier<LazyTree<X>> leftSupp, Supplier<LazyTree<X>> rightSupp) {
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
                return leftSupp.get();
            }

            @Override
            public LazyTree<X> right() {
                return rightSupp.get();
            }
            
        };
    }

    @Override
    public <X> LazyTree<X> fromTwoIterations(X root, UnaryOperator<X> leftOp, UnaryOperator<X> rightOp) {
        return new LazyTree<X>() {
            @Override
            public boolean hasRoot() {
                return true;
            }

            @Override
            public X root() {
                return root;
            }

            @Override
            public LazyTree<X> left() {
                return fromTwoIterations(leftOp.apply(root), leftOp, rightOp);
            }

            @Override
            public LazyTree<X> right() {
                return fromTwoIterations(rightOp.apply(root), leftOp, rightOp);
            }
            
        };
    }

    @Override
    public <X> LazyTree<X> fromTreeWithBound(LazyTree<X> tree, int bound) {
        return new LazyTree<X>() {
            
            @Override
            public boolean hasRoot() {
                return tree.hasRoot() && bound != 0;
            }

            @Override
            public X root() {
                return tree.root();
            }

            @Override
            public LazyTree<X> left() {

                return fromTreeWithBound(tree.left(), bound - 1);
            }

            @Override
            public LazyTree<X> right() {

                return fromTreeWithBound(tree.right(), bound - 1);
            }
        };
    }

}
