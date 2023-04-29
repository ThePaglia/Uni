package a01b.sol2;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogicImpl implements Logic {

    private Set<Pair<Integer,Integer>> enabled = new HashSet<>();
    private final int size;
    private boolean over = false;

    public LogicImpl(int size) {
        this.size = size;
    }

    private boolean toggle(int x, int y){
        var p = new Pair<>(x,y);
        if (this.enabled.contains(p)){
            this.enabled.remove(p);
            return false;
        } 
        this.enabled.add(p);
        return true;
    }

    @Override
    public void hit(int x, int y) {
        var toggles = Stream.of(x-1,x+1)
                .filter(k -> k >= 0)
                .filter(k -> k < size)
                .flatMap(i -> Stream.of(y-1,y+1)
                        .filter(k -> k >= 0)
                        .filter(k -> k < size)
                        .map(j -> new Pair<>(i, j)))
                .map(p -> this.toggle(p.getX(),p.getY()))
                .collect(Collectors.groupingBy(b->b));
        this.over = toggles.size()==2 && toggles.get(false).size()==3 && toggles.get(true).size()==1;
    }

    @Override
    public boolean isEnabled(int x, int y) {
        return this.enabled.contains(new Pair<>(x,y));
    }

    @Override
    public boolean isOver() {
        return this.over;
    }
}
