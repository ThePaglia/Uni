package a05.sol1;

public class SecureBattery extends BatteryDecorator {

	public SecureBattery(AbstractBattery base) {
		super(base);
	}
	
	boolean inUse = false;
	
	@Override
	public void startUse() {
		if (inUse || this.getEnergy() <= 0.0) {
			throw new IllegalStateException();
		}
		inUse = true;
		super.startUse();
	}

	@Override
	public void stopUse(double duration) {
		if (!inUse) {
			throw new IllegalStateException();
		}
		inUse = false;
		super.stopUse(duration);
	}
}
