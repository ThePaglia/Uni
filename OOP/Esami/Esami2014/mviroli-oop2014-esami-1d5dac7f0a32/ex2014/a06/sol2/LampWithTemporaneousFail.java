package ex2014.a06.sol2;

import java.util.Optional;

public class LampWithTemporaneousFail extends AbstractLamp {
	
	protected int failingPeriod;
	protected final int failingDuration;
	
	public LampWithTemporaneousFail(int failingDuration){
		this.failingDuration = failingDuration;
	}
	
	@Override
	protected void checkCanGoOn() throws IllegalStateException {
		if (this.fail){
			if (this.failingPeriod == this.failingDuration-1){
				this.fail = false;
			} else {
				this.failingPeriod++;
			}
			throw new IllegalStateException();
		}
	}

	@Override
	public void fail() {
		if (!this.fail){
			super.fail();
			this.failingPeriod = 0;
			this.on = false;
		}
	}
	
}
