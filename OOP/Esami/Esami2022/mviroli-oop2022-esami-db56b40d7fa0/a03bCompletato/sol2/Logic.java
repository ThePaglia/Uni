package a03b.sol2;

public interface Logic {

    enum Player { HUMAN, COMPUTER }

    boolean humanMove(int x, int y);

    boolean over();

    boolean hasPiece(Player player, int x, int y);

    void reset();
    
}
