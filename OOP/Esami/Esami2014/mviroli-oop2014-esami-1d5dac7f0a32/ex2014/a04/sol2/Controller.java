package ex2014.a04.sol2;

import java.util.*;

/*
 * Un controller che genera i valori e li restituisce ad ogni tentativo
 */

public interface Controller {
	
	void reset(int size,int max);
	
	Optional<Integer> tryNumber(int elem);
	
	boolean allFound();

}
