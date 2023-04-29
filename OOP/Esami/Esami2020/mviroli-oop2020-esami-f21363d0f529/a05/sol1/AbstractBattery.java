package a05.sol1;

public abstract class AbstractBattery implements Battery {
	
	private double energy;
	
	@Override
	public double getEnergy() {
		return this.energy;
	}

	protected void setEnergy(double energy) {
		this.energy = energy;
	}
}
