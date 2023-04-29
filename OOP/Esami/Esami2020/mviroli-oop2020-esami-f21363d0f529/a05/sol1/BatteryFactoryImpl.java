package a05.sol1;

import java.util.function.BiFunction;

public class BatteryFactoryImpl implements BatteryFactory {

	private static class AbstractBatteryFactory {
		
		public static AbstractBattery defaulBattery() {
			return simple((e, d) -> e - d);
		}

		public static AbstractBattery simple(BiFunction<Double, Double, Double> function) {
			return new SimpleBattery(function);
		}

		public static AbstractBattery secure(AbstractBattery base) {
			return new SecureBattery(base);
		}

		public static AbstractBattery rechargable(AbstractBattery base) {
			return new RechargableBattery(base);
		}
	}
	
	@Override
	public Battery createSimpleBattery() {
		return AbstractBatteryFactory.defaulBattery();
	}

	@Override
	public Battery createSimpleBatteryByDrop(double energyPerDurationDrop) {
		return AbstractBatteryFactory.simple((e, d) -> e - d * energyPerDurationDrop);
	}

	@Override
	public Battery createSimpleBatteryWithExponentialDrop() {
		return AbstractBatteryFactory.simple((e, d) -> e / 2);
	}

	@Override
	public Battery createSecureBattery() {
		return AbstractBatteryFactory.secure(AbstractBatteryFactory.defaulBattery());
	}

	@Override
	public Battery createRechargableBattery() {
		return AbstractBatteryFactory.rechargable(AbstractBatteryFactory.defaulBattery());
	}

	@Override
	public Battery createSecureAndRechargableBattery() {
		return AbstractBatteryFactory.rechargable(AbstractBatteryFactory.secure(AbstractBatteryFactory.defaulBattery()));
	}

}
