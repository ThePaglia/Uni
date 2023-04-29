package a02a.e1;

import static org.junit.Assert.*;
import java.util.*;

/**
 * Si consulti la documentazione della interfaccia ScannerFactory, che modella
 * una factory per Scanner, che a sua volta modella un oggetto che presa una lista 
 * ne estrae qualche informazione. 
 * 
 * Il commento alle interfacce fornite, e i metodi di test qui sotto costituiscono la necessaria 
 * spiegazione del problema.
 * 
 * Sono considerati opzionali ai fini della possibilità di correggere
 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
 * punteggio: 
 * - implementazione dei test opzionali (denominati 'optionalTestXYZ', ossia quelli relativi a 
 *   ScannerFactory.cumulativeSums) 
 * - concisione del codice e rimozione di tutte le ripetizioni
 *
 * Si tolga il commento dal metodo init.
 * 
 * Indicazioni di punteggio:
 * - correttezza della parte obbligatoria: 10 punti
 * - correttezza della parte opzionale: 4 punti  
 * - qualità della soluzione: 3 punti
 * 
 */

public class Test {

	private ScannerFactory factory;

	@org.junit.Before
	public void init() {
		//this.factory = new ScannerFactoryImpl();
	}

	@org.junit.Test
	public void optionalTestCumulativeSum() {
		// somma cumulativa degli elementi "fin qui"
		var s = this.factory.cumulativeSum();
		assertEquals(List.of(1,1,1,3,6,6,6,6,10), s.scan(List.of(1,0,0,2,3,0,0,0,4).iterator()));
	}
	
	@org.junit.Test
	public void testCollect() {
		// colleziona elementi con certe caratteristiche e li trasforma
		Scanner<Integer,List<Integer>> s = this.factory.collect(x-> x>0, x->x*2);
		assertEquals(List.of(2,4,6,8), s.scan(List.of(1,0,0,2,3,0,0,0,4).iterator()));
	}
	
	@org.junit.Test
	public void testFindFirst() {
		// trova il primo con certe caratteristiche
		Scanner<Integer,Optional<Integer>> s = this.factory.findFirst(x-> x<0);
		assertEquals(Optional.of(-3), s.scan(List.of(1,0,0,2,-3,0,0,0,4).iterator()));
		assertEquals(Optional.of(-1), s.scan(List.of(-1,0,0,2,-3,0,0,0,4).iterator()));
		assertEquals(Optional.of(-4), s.scan(List.of(1,0,0,2,3,0,0,0,-4).iterator()));
		assertEquals(Optional.empty(), s.scan(List.of(1,0,0,2,3,0,0,0,4).iterator()));
	}
	
	@org.junit.Test
	public void testMaximalPrefix() {
		// trova l'elemento più grande della sottosequenza iniziale ordinata
		Scanner<Integer,Optional<Integer>> s = this.factory.maximalPrefix();
		assertEquals(Optional.of(20), s.scan(List.of(1,10,20,5,30,40).iterator()));
		assertEquals(Optional.of(3), s.scan(List.of(1,2,3).iterator()));
		assertEquals(Optional.of(1), s.scan(List.of(1).iterator()));
		assertEquals(Optional.empty(), s.scan(List.<Integer>of().iterator()));
	}
	
}
