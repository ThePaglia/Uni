package ex2014.a04.sol2_java7;

/*
 * Un controller che genera i valori e li restituisce ad ogni tentativo
 */

public interface Controller {
	
	void reset(int size,int max);
	
	Integer tryNumber(int elem);
	
	boolean allFound();

}
