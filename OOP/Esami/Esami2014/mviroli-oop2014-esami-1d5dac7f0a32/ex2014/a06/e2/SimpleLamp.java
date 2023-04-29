package ex2014.a06.e2;

public class SimpleLamp implements Lamp {
	
	private boolean on = false;
	
	@Override
	public void on() throws IllegalStateException {
		this.on = true;		
	}

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
	}
	
}
