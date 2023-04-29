package ex2014.a06.sol2;

public abstract class AbstractLamp implements Lamp {
	
	protected boolean on = false;
	protected boolean fail = false;
	
	@Override
	public void on() throws IllegalStateException {
		this.checkCanGoOn();
		this.on = true;		
	}
	
	protected abstract void checkCanGoOn() throws IllegalStateException;

	@Override
	public void off() {
		this.on = false;
	}

	@Override
	public boolean isOn() {
		return this.on;
	}

	@Override
	public void fail(){
		this.fail = true;
	}
	
}
