package a02b.e2;

public interface Logics {

    boolean hit(Pair<Integer,Integer> pos);        
    
    void disableDiagonal();

    boolean isDisabled(Pair<Integer,Integer> pos);

    boolean checkRestart();

    void restart();

}
