package a01b.sol2;

import java.util.*;

/*
 * Un controller che genera i valori e li restituisce ad ogni tentativo
 */

public interface Logics {
	
	void reset(int size,int max);
	
	Optional<Integer> tryNumber(int elem);
	
}
