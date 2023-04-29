package ex2016.a02b.sol2;

import java.util.*;
import java.util.stream.*;

public class ModelImpl implements Model {
    
    private final int size;
    private Set<Integer> positions;
    
    public ModelImpl(int size){
        this.size = size;
        this.reset();
    }

    @Override
    public void advance() {
        this.positions = this.positions.stream().map(i->(i+1)% size).collect(Collectors.toSet());
    }

    @Override
    public Set<Integer> positions() {
        return Collections.unmodifiableSet(this.positions);
    }
    
    private void reset(){
        this.positions = new HashSet<Integer>(Arrays.asList(0,1,2));
    }

}
