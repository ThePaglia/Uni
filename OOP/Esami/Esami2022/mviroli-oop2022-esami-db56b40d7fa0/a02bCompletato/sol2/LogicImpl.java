package a02b.sol2;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LogicImpl implements Logic {
    
    private final int size;
    private Set<Pair<Integer,Integer>> selected = new HashSet<>();

    public LogicImpl(int size) {
        this.size = size;
    }

    @Override
    public boolean hit(int x, int y) {
        var position = new Pair<>(x, y);
        if (this.selected.contains(position)){
            this.selected.remove(position);
        } else {
            this.selected.add(position);
        }
        return this.selected.contains(position);
    }

    private Stream<Pair<Integer,Integer>> all(){
        return IntStream.range(0, size)
            .mapToObj(i -> i)
            .flatMap(x -> IntStream.range(0, size)
                .mapToObj(y -> new Pair<>(x,y)));
    }

    private Optional<Set<Pair<Integer, Integer>>> checkThree(Function<Pair<Integer,Integer>,Integer> func) {
        return this.selected
                .stream()
                .collect(Collectors.groupingBy(func::apply, Collectors.counting())) // maps invariants to how many cells share it
                .entrySet()
                .stream()
                .filter(e -> e.getValue() == 3L) // selects a diagonal with 3 cells 
                .map(e -> e.getKey())
                .findAny()
                .map(v -> this.all().filter(p -> func.apply(p) == v).collect(Collectors.toSet()));
    }

    @Override
    public Optional<Set<Pair<Integer, Integer>>> checkThree() {
        return checkThree(p -> p.getX()-p.getY()); // X-Y is an invariant for cells in the same diagonal
    }

    @Override
    public void init() {
        this.selected.clear();        
    }
}
