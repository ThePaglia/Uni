package a02b.sol2;

public interface Logics{
    
    void hit1(int row, int col);
    
    boolean hit2(int row, int col);
    
    Iterable<Pair<Integer,Integer>> marksIterator();
   
}
