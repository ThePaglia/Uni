package ex2016.a01b.sol1;

import java.util.*;

public class BuilderOf<X> implements ListBuilder<X> {
    
    private final ListBuilder<X> builder;
    private final Collection<X> from;
    
    BuilderOf(ListBuilder<X> builder, Collection<X> from) {
        super();
        this.builder = builder;
        this.from = from;
    }

    public void addElement(X x) {
        if (!from.contains(x)){
            throw new IllegalArgumentException();
        }
        builder.addElement(x);
    }

    public List<X> build() {
        return builder.build();
    }
}
