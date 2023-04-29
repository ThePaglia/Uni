package it.unibo.apice.oop.p15gui.mvc.model;

public interface DrawNumber {
	
	void reset();
	
	DrawResult attempt(int n) throws AttemptsLimitReachedException;
}
