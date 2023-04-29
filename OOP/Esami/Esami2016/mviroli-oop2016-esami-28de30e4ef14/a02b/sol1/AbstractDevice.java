package ex2016.a02b.sol1;

public abstract class AbstractDevice implements Device {
    
    private boolean on = false;
    private boolean working = true;

    @Override
    public final void on() {
        if (this.isWorking() && !this.isOn()){
            if (this.tryOn()){
                this.on = true;
            } else {
                this.working = false;
            }
        }
    }
    
    protected abstract boolean tryOn();

    @Override
    public void off() {
        this.on = false;
    }

    @Override
    public boolean isOn() {
        return this.on;
    }

    @Override
    public boolean isWorking() {
        return this.working;
    }
    
    protected void stopWorking(){
        this.working = false;
        this.on = false;
    }
}
