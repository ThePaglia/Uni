package ex2016.a01b.sol1;

import java.util.*;


public class BuildersImpl implements Builders {

    @Override
    public <X> ListBuilder<X> makeBasicBuilder() {
        return new BasicBuilder<>();
    }

    @Override
    public <X> ListBuilder<X> makeBuilderWithSize(int size) {
        return new BuilderWithSize<>(this.makeBasicBuilder(), size);
    }

    @Override
    public <X> ListBuilder<X> makeBuilderFromElements(Collection<X> from) {
        return new BuilderOf<>(this.makeBasicBuilder(), from);
    }
    
    public <X> ListBuilder<X> makeBuilderFromElementsAndWithSize(Collection<X> from, int size){
        return new BuilderOf<>(this.makeBuilderWithSize(size), from);
    }

}
