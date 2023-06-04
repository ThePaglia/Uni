package a03a.sol2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LogicImpl implements Logic {

    private final int size;
    private final Random random = new Random();
    private final Map<Player, Pair<Integer,Integer>> positions = new HashMap<>();

    public LogicImpl(int size) {
        this.size = size;
        this.reset();
    }

    private Pair<Integer,Integer> randomPosition(){
        return new Pair<>(this.random.nextInt(this.size), this.random.nextInt(this.size));
    }

    private List<Pair<Integer,Integer>> allAttacked(Pair<Integer,Integer> position){
        return IntStream.range(0, this.size)
                .mapToObj(i->i)
                .flatMap(x -> IntStream.range(0, this.size)
                        .mapToObj(y -> new Pair<>(x,y)))
                .filter(p -> attacks(p, position))
                .collect(Collectors.toList());
    }

    private boolean attacks(Pair<Integer,Integer> p1, Pair<Integer,Integer> p2){
        return !p1.equals(p2) && (p1.getX() == p2.getX() || p1.getY() == p2.getY());
    }

    @Override
    public final void reset() {
        this.positions.put(Player.HUMAN, this.randomPosition());
        do {
            this.setPosition(Player.COMPUTER, this.randomPosition());
        } while (this.attacks(this.getPosition(Player.COMPUTER),this.getPosition(Player.HUMAN)));
    }

    @Override
    public boolean humanMove(int x, int y) {
        var position = new Pair<>(x,y);
        if (this.attacks(this.getPosition(Player.HUMAN),position)){
            this.setPosition(Player.HUMAN, position);
            return true;
        }
        return false;
    }

    @Override
    public boolean over() {
        return this.getPosition(Player.COMPUTER).equals(this.getPosition(Player.HUMAN));
    }

    @Override
    public void computerMove() {
        var valid = this.allAttacked(this.getPosition(Player.COMPUTER));
        if (valid.contains(this.getPosition(Player.HUMAN))){
            this.setPosition(Player.COMPUTER, this.getPosition(Player.HUMAN));
        } else {
            this.setPosition(Player.COMPUTER, valid.get(this.random.nextInt(valid.size())));
        }
    }

    @Override
    public Pair<Integer, Integer> getPosition(Player player) {
        return this.positions.get(player);
    }

    private void setPosition(Player player, Pair<Integer,Integer> position){
        this.positions.put(player,position);
    }
}
