package ex2015.a06.sol1;

public class PowerCountdownImpl extends AbstractCountdown implements PowerCountdown {
    
    private int finalValue;
    private int delta = 1;
    private int initialValue;
    
    public PowerCountdownImpl(int value, int finalValue){
        super(value);
        this.initialValue = value;
        this.finalValue = finalValue;
    }

    @Override
    protected void actualDecrease() {
        this.value -= this.delta;
    }

    @Override
    public boolean isOver() {
        return this.value <= this.finalValue;
    }

    @Override
    public void reset() {
        this.delta = 1;
        this.value = this.initialValue; 
    }

    @Override
    public void speedup() {
        this.delta++;
    }

}
