package ex2016.a02b.sol1;

public class LuminousDeviceImpl extends AbstractDevice implements LuminousDevice {
    
    private final int maxIntensity;
    private int intensity = 0;
    
    public LuminousDeviceImpl(int maxIntensity) {
        this.maxIntensity = maxIntensity;
    }

    @Override
    protected boolean tryOn() {
        return this.intensity != this.maxIntensity;
    }

    @Override
    public void dim() {
        if (this.intensity > 0){
            this.intensity--;
        }
    }

    @Override
    public void brighten() {
        if (this.intensity < this.maxIntensity){
            this.intensity++;
        } 
        if (this.intensity == this.maxIntensity && this.isOn()) {
            this.stopWorking();
        }
    }

    @Override
    public int getIntesity() {
        return this.intensity;
    }

}
