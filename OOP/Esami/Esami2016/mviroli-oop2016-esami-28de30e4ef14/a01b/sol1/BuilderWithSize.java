package ex2016.a01b.sol1;

import java.util.List;

public class BuilderWithSize<X> implements ListBuilder<X> {
    
    private final ListBuilder<X> builder;
    private int remainingSize;
    
    BuilderWithSize(ListBuilder<X> builder, int size) {
        super();
        this.builder = builder;
        this.remainingSize = size;
    }

    public void addElement(X x) {
        builder.addElement(x);
        this.remainingSize--;
    }

    public List<X> build() {
        if (this.remainingSize != 0){
            throw new IllegalStateException();
        }
        return builder.build();
    }
}
