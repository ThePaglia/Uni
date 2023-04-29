package ex2014.a02.sol2;

/*
 * Si noti che questo Controller è più generale del necessario, gestendo ad esempio anche l'abilitazione dei pulsanti
 * Lo studente noti però l'uso notevole della enumerazione
 */

public interface Controller {
	
	static enum Button {A, B, C, D, E}
	
	boolean enabled(Button b);
	
	void hit(Button b);
	
	boolean enabledWrite();
	
	boolean enabledQuit();
	
	void hitWrite();
	
	void hitQuit();

}
