package a05.sol1;

public class BatteryDecorator extends AbstractBattery {
	private AbstractBattery base;

	public BatteryDecorator(AbstractBattery base) {
		this.base = base;
	}

	@Override
	public void startUse() {
		this.base.startUse();
	}

	@Override
	public void stopUse(double duration) {
		this.base.stopUse(duration);
	}

	@Override
	public double getEnergy() {
		return this.base.getEnergy();
	}

	@Override
	public void recharge() {
		this.base.recharge();
	}
	
	protected void setEnergy(double energy) {
		this.base.setEnergy(energy);
	}
}
