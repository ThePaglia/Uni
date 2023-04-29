package ex2014.a06.sol2;

public class LampFactoryImpl implements LampFactory{
	
	public Lamp createLampWithRemainingDuration(int remainingDuration){
		return new LampWithRemainingDuration(remainingDuration);
	}
	
	public Lamp createLampWithTemporaneousFailure(int failingDuration){
		return new LampWithTemporaneousFail(failingDuration);
	}

}
