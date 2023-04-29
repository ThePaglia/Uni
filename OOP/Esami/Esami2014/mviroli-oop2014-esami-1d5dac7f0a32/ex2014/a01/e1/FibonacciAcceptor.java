package ex2014.a01.e1;

import java.util.*;

/**
 * Questa interfaccia è usata per controllare una GUI, GUI che chiede ad un utente una o più sequenze di Fibonacci, e che fornisce operazioni per
 * estrarre successivamente tali sequenze.
 * Una sequenza è di Fibonacci se comincia con qualunque coppia di numeri, e poi ogni altro numero è la somma dei due precedenti.
 * Ad esempio, una sequenza è: 1 3 4 7 11 18 29.., un'altra è: 1 1 2 3 5 8..
 * Nell'implementare questa interfaccia, oltre che considerare i commenti ai metodi qui sotto, fare attenzione che ogni struttura dati
 * ritornata non violi l'incapsulamento (ossia modificandola non si influisca negativamente sull'oggetto di FibonacciAcceptor che l'ha ritornata),
 * ad esempio, si creino "copie difensive"! 
 */

public interface FibonacciAcceptor {

	/**
	 * Initiates a new sequence
	 * 
	 * @param sequenceName, the name of the sequence
	 * @throws a IllegalArgumentException if sequenceName was already used in a previous sequence
	 */
	void reset(String sequenceName);
	
	/**
	 * Consumes the next element of the current sequence.
	 * 
	 * @param l, the next element
	 * @return false if that element is not correct
	 * @throws a IllegalStateException if no reset was initially done
	 */
	boolean consumeNext(long l);
	
	/**
	 * @return the current sequence
	 * @throws a IllegalStateException if no reset was initially done
	 */
	List<Long> getCurrentSequence();
	
	/**
	 * @return all sequences produced so far, as a map from names to sequences
	 */
	Map<String,List<Long>> getAllSequences();
}
