package a03b.sol2;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogicImpl implements Logic {

    private final int size;
    private final Map<Pair<Integer,Integer>, Player> positions = new HashMap<>();
    private Random random = new Random();

    public LogicImpl(int size) {
        this.size = size;
        this.reset();
    }

    private boolean validPosition(Pair<Integer, Integer> position){
        return position.getX() >= 0 && position.getY() >= 0 && position.getX() < this.size && position.getY() < this.size;
    }

    private List<Pair<Integer,Integer>> moves(Pair<Integer,Integer> position){
        List<Pair<Integer,Integer>> moves = Stream.of(position)
            .flatMap(p -> Stream.of(new Pair<>(1,-1), new Pair<>(-1,-1))
                        .map(pp -> new Pair<>(pp.getX()+p.getX(), pp.getY()+p.getY())))
            .filter(this::validPosition)
            .filter(this.positions::containsKey)
            .filter(p -> !this.positions.get(p).equals(Player.HUMAN))
            .collect(Collectors.toList());
        if (!moves.isEmpty()){
            return moves;
        }
        return Stream.of(new Pair<>(position.getX(), position.getY()-1))
                .filter(this::validPosition)
                .filter(p -> !this.positions.containsKey(p))
                .collect(Collectors.toList());
    }

    @Override
    public final void reset() {
        this.positions.clear();
        for (int i=0; i<this.size; i++){
            this.positions.put(new Pair<>(i,this.random.nextInt(2)), Player.COMPUTER);
            this.positions.put(new Pair<>(i,size-1), Player.HUMAN);
        }
    }

    private boolean doMove(Pair<Integer, Integer> position){
        var player = this.positions.get(position);
        var moves = new LinkedList<>(this.moves(position));
        if (moves.isEmpty()){
            return false;
        }
        Collections.shuffle(moves);
        this.positions.remove(position);
        this.positions.put(moves.get(0), player);
        return true;
    }

    @Override
    public boolean humanMove(int x, int y) {
        return doMove(new Pair<>(x,y));
    }

    @Override
    public boolean over() {
        return this.positions.values().stream().distinct().count() == 1;
    }

    @Override
    public boolean hasPiece(Player player, int x, int y) {
        return Optional.ofNullable(this.positions.get(new Pair<>(x,y))).filter(pl -> pl == player).isPresent();
    }

}
