package a03a.sol2;

public interface Logic {

    enum Player { HUMAN, COMPUTER }

    boolean humanMove(int x, int y);

    void computerMove();
    
    boolean over();

    Pair<Integer, Integer> getPosition(Player player);

    void reset();
    
}
