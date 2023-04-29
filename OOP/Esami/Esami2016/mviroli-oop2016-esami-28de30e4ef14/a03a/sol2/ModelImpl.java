package ex2016.a03a.sol2;

public class ModelImpl implements Model {
    
    private int posA = 0;
    private int posB = 1;
    private final int size;
    
    public ModelImpl(int size) {
        super();
        this.size = size;
    }
   
    @Override
    public void moveA() {
        if (posA < posB-1){
            posA++;
        }
    }

    @Override
    public void moveB() {
        if (posB < size-1){
            posB++;
        }
    }

    @Override
    public void reset() {
        posA = 0;
        posB = 1;
    }

    @Override
    public boolean over() {
        return posA == size-2 && posB == size-1;
    }

    @Override
    public int getAPosition() {
        return posA;
    }

    @Override
    public int getBPosition() {
        return posB;
    }
        
}
