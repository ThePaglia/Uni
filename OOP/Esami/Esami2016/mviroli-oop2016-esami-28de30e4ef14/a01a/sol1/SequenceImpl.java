package ex2016.a01a.sol1;

import java.util.*;

public class SequenceImpl<X> implements Sequence<X> {
    
    private final List<X> list;
    
    SequenceImpl(List<X> list){
        this.list = Collections.unmodifiableList(list);
    }

    @Override
    public Optional<X> getAtPosition(int position) {
        return position >=0 && position < this.size() ? Optional.of(this.list.get(position)) : Optional.empty();
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public List<X> asList(){
        return this.list;
    }
    
    @Override
    public void executeOnAllElements(Executor<X> executor){
        this.list.forEach(executor::execute);
    }
}
