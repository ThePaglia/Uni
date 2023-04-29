package ex2015.a06.sol1;

public abstract class AbstractCountdown implements Countdown {
    
    protected int value;
    
    public AbstractCountdown(int value) {
        this.value = value;
    }
    
    protected abstract void actualDecrease();

    @Override
    public final void decrease() {
        if (this.isOver()){
            throw new IllegalStateException();
        }
        this.actualDecrease();

    }

    @Override
    public int getValue() {
        return this.value;
    }

}
