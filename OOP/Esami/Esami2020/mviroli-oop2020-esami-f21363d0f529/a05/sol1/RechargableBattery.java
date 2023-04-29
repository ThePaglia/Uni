package a05.sol1;

public class RechargableBattery extends BatteryDecorator {
	
	private static final double EFFICIENCY_LOSS = 0.01;
	private double rechargeValue = 1.0;
	
	public RechargableBattery(AbstractBattery base) {
		super(base);
	}
	
	@Override
	public void recharge() {
		super.setEnergy(this.rechargeValue);
		this.rechargeValue = this.rechargeValue - EFFICIENCY_LOSS;
	}
}
