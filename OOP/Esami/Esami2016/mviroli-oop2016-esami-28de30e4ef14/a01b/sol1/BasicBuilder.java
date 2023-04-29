package ex2016.a01b.sol1;

import java.util.*;

public class BasicBuilder<X> implements ListBuilder<X> {

    private final List<X> list = new ArrayList<X>();
    private boolean done = false;
    
    private void checkDone(){
        if (this.done){
            throw new IllegalStateException();
        }
    }
    
    @Override
    public void addElement(X x) {
        this.checkDone();
        Objects.requireNonNull(x);
        this.list.add(x);
    }

    @Override
    public List<X> build() {
        this.checkDone();
        this.done = true;
        return Collections.unmodifiableList(this.list);
    } 
}
