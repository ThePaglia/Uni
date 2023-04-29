package a05.sol1;

import java.util.function.BiFunction;

public class SimpleBattery extends AbstractBattery {
	
	private final BiFunction<Double, Double, Double> dropFunction;
	
	protected SimpleBattery(BiFunction<Double, Double, Double> dropFunction) {
		this.dropFunction = dropFunction;
		this.setEnergy(1.0);
	}
	
	@Override
	public void startUse() {
	}

	@Override
	public void stopUse(double duration) {
		this.setEnergy(this.dropFunction.apply(this.getEnergy(), duration));
		if (this.getEnergy()<= 0.0) {
			this.setEnergy(0.0);
		}
	}
		
	@Override
	public void recharge() {
	}
}
