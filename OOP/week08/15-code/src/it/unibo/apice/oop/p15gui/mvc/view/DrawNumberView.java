package it.unibo.apice.oop.p15gui.mvc.view;

import it.unibo.apice.oop.p15gui.mvc.model.DrawResult;

public interface DrawNumberView {
	
	void setObserver(DrawNumberViewObserver observer);
	
	void start(); 

	void numberIncorrect();

	void limitsReached();

	void result(DrawResult res);

}
