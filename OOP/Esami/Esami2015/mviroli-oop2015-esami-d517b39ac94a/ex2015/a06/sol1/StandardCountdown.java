package ex2015.a06.sol1;

public class StandardCountdown extends AbstractCountdown {
    
    public StandardCountdown(int value) {
        super(value);
    }

    @Override
    protected void actualDecrease() {
        this.value--;
    }

    @Override
    public boolean isOver() {
        return this.value == 0;
    }

}
