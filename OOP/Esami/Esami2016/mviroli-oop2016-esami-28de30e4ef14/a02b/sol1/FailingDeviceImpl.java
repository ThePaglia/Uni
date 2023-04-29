package ex2016.a02b.sol1;

public class FailingDeviceImpl extends AbstractDevice {

    private int counter;
    
    FailingDeviceImpl(int num){
        this.counter = num;
    }
    
    @Override
    protected boolean tryOn() {
        if (this.counter > 0){
            this.counter--;
        }
        return this.counter > 0;
    }

}
