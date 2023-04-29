package ex2016.a02b.sol1;

public class DeviceFactoryImpl implements DeviceFactory {

    @Override
    public Device createDeviceNeverFailing() {
        return new AbstractDevice(){

            @Override
            protected boolean tryOn() {
                return true;
            }
            
        };
    }

    @Override
    public Device createDeviceFailingAtSwitchOn(int count) {
        return new FailingDeviceImpl(count);
    }

    @Override
    public LuminousDevice createLuminousDeviceFailingAtMaxIntensity(int maxIntensity) {
        return new LuminousDeviceImpl(maxIntensity);
    }

}
