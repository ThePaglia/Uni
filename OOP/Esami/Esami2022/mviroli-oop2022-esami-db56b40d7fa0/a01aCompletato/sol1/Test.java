package a01a.sol1;

import static org.junit.Assert.*;
import java.util.*;

/**
 * Si consulti la documentazione della interfaccia SubsequenceCombinerFactory, che modella
 * una factory per SubsequenceCombiner, che a sua volta modella un trasformatore da liste a
 * liste, che funziona spezzando la lista in input in vari pezzi, ottenendo un elemento 
 * in uscita per ognuno di essi.
 * 
 * Il commento alle interfacce fornite, e i metodi di test qui sotto costituiscono la necessaria 
 * spiegazione del problema.
 * 
 * Sono considerati opzionali ai fini della possibilità di correggere
 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
 * punteggio: 
 * - implementazione del quinto metodo della factory (ossia, a scelta se ne realizzino 4, il quinto è opzionale)
 * - concisione del codice e rimozione di tutte le ripetizioni con eventuale uso appropriato di pattern
 *
 * Si tolga il commento dal metodo init.
 * 
 * Indicazioni di punteggio:
 * - correttezza della parte obbligatoria: 10 punti
 * - correttezza della parte opzionale: 3 punti  
 * - qualità della soluzione (eliminando ripetizioni di codice): 4 punti
 * - errori di programmazione comportano decurtamento del punteggio complessivo
 */

public class Test {

	private SubsequenceCombinerFactory factory;

	@org.junit.Before
	public void init() {
		this.factory = new SubsequenceCombinerFactoryImpl();
	}

	@org.junit.Test
	public void testTripletsToSum() {
		SubsequenceCombiner<Integer,Integer> sc = this.factory.tripletsToSum();
		// Si isolano triple, e se ne fornisce la somma: una parte finale di 1 o 2 elementi è comunque sommata
		assertEquals(List.of(30,300,3000,30),
			sc.combine(List.of(10, 10, 10, 100, 100, 100, 1000, 1000, 1000, 10, 20))
		);
		assertEquals(List.of(18,300),
			sc.combine(List.of(5, 6, 7, 100, 100, 100))
		);
	}

	@org.junit.Test
	public void testTripletsToList() {
		SubsequenceCombiner<Integer,List<Integer>> sc = this.factory.tripletsToList();
		// Come nel caso precedente, ma si formino liste
		assertEquals(List.of(List.of(10,10,10), List.of(100,100,100), List.of(1000,1000,1000), List.of(10,20)),
			sc.combine(List.of(10, 10, 10, 100, 100, 100, 1000, 1000, 1000, 10, 20))
		);
		assertEquals(List.of(List.of(10,10,10), List.of(100,100,100)),
			sc.combine(List.of(10, 10, 10, 100, 100, 100))
		);
	}

	@org.junit.Test
	public void testCountUntilZero() {
		SubsequenceCombiner<Integer,Integer> sc = this.factory.countUntilZero();
		// Trovato uno zero (o fine lista), si produca il numero di elementi fin qui
		assertEquals(List.of(3,2,4,2),
			sc.combine(List.of(1,1,1,0,2,2,0,3,3,3,3,0,5,6))
		);
		assertEquals(List.of(3,2),
			sc.combine(List.of(10,10,10,0,2,3,0))
		);
	}

	@org.junit.Test
	public void testSingleReplacer(){
		// la combine in questo caso è come la map degli stream
		SubsequenceCombiner<String,String> sc = this.factory.singleReplacer(s -> s + s);
		assertEquals(List.of("aa", "bb", "cc"), 
			sc.combine(List.of("a", "b", "c")));
		SubsequenceCombiner<String,Integer> sc2 = this.factory.singleReplacer(s -> s.length());
		assertEquals(List.of(1, 3, 2), 
			sc2.combine(List.of("a", "bbb", "cc")));
	}

	@org.junit.Test
	public void testCumulativeToList(){
		SubsequenceCombiner<Integer,List<Integer>> sc = this.factory.cumulateToList(100);
		// Soglia 100: non appena la somma degli elementi trovati diventa >=100 (o c'è fine lista)
		// la sottosequenza viene data in uscita
		assertEquals(List.of(List.of(10,50,70), List.of(80,20), List.of(30,30,39,30), List.of(40)), 
			sc.combine(List.of(10,50,70,80,20,30,30,39,30,40)));
	}		
}
