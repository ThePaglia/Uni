package a01a.sol2;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogicImpl implements Logic {

    private static final List<Integer> ASCENDING = List.of(0,1,2);
    private static final List<Integer> DESCENDING = List.of(2,1,0);
    
    private final List<Pair<Integer,Integer>> line = new LinkedList<>();
    private final Set<Pair<Integer,Integer>> enabled = new HashSet<>();

    @Override
    public boolean toggle(int x, int y) {
        var p = new Pair<>(x,y);
        if (this.enabled.contains(p)){
            this.enabled.remove(p);
            this.line.clear();
        } else {
            this.enabled.add(p);
            this.line.add(p);
            if (this.line.size()>3){
                this.line.remove(0);
            }
        }
        return this.enabled.contains(p);
    }

    private boolean inDiagonal(List<Integer> list){
        return list.equals(ASCENDING) || list.equals(DESCENDING);
    }

    @Override
    public boolean isOver() {
        System.out.println(line);
        if (this.line.size() < 3){
            return false;
        }
        int minx = this.line.stream().map(Pair::getX).mapToInt(i -> i).min().getAsInt();
        int miny = this.line.stream().map(Pair::getY).mapToInt(i -> i).min().getAsInt();
        List<Integer> sx = this.line.stream().map(Pair::getX).map(x -> x - minx).collect(Collectors.toList());
        List<Integer> sy = this.line.stream().map(Pair::getY).map(y -> y - miny).collect(Collectors.toList());
        return inDiagonal(sx) && inDiagonal(sy);
    }           
}
