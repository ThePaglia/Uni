package a03a.e2;

public interface Logics {
    
    boolean hit(Pair<Integer, Integer> pos);

    void updateSelected(Pair<Integer, Integer> pos);

    boolean isSelected(Pair<Integer, Integer> pos);

    boolean isOver();
}
