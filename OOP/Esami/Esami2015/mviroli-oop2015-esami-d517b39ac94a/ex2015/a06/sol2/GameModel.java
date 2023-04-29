package ex2015.a06.sol2;

public interface GameModel {
    
    void advance(int draw);
    
    int getPosition();
    
    boolean won();
    
    boolean lost();

}
