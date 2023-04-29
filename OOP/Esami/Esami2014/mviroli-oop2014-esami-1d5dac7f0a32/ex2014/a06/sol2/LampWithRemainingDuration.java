package ex2014.a06.sol2;

import java.util.Optional;

public class LampWithRemainingDuration extends AbstractLamp {
	
	private int remainingDuration;
	
	public LampWithRemainingDuration(int remainingDuration){
		this.remainingDuration = remainingDuration;
	}

	@Override
	protected void checkCanGoOn() throws IllegalStateException {
		if (this.on){ 
			return; 
		}
		if (this.fail && this.remainingDuration == 0){
			throw new IllegalStateException();
		}
		if (this.fail){
			this.remainingDuration--;
		}
	}
	
	@Override
	public void fail() {
		super.fail();
	}
	
}
