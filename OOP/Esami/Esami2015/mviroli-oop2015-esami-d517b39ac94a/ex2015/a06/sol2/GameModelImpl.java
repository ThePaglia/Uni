package ex2015.a06.sol2;

public class GameModelImpl implements GameModel {

    private final int size;
    private final int pitfall;
    private int pos = 0;
    
    public GameModelImpl(int size, int pitfall){
        this.size = size;
        this.pitfall = pitfall;
    }
    
    @Override
    public void advance(int draw) {
        this.pos = Math.min(this.pos + draw, size);
        System.out.println(this.pos);
    }

    @Override
    public int getPosition() {
        return this.pos;
    }

    @Override
    public boolean won() {
        return this.pos == this.size;
    }

    @Override
    public boolean lost() {
        return this.pos == this.pitfall;
    }
}
