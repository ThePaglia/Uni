package a02c.e1;

import static org.junit.Assert.*;
import java.util.*;

/**
 * Si consulti la documentazione della interfaccia ListSplitterFactory, che modella
 * una factory per ListSplitter, che a sua volta modella un trasformatore da liste a
 * liste di lista, che funziona spezzando la lista in input in vari pezzi.
 * 
 * Il commento alle interfacce fornite, e i metodi di test qui sotto costituiscono la necessaria 
 * spiegazione del problema.
 * 
 * Sono considerati opzionali ai fini della possibilità di correggere
 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
 * punteggio: 
 * - implementazione dei test opzionali (denominati 'optionalTestXYZ', ossia quelli relativi a 
 *   ListSplitterFactory.byPredicate 
 * - concisione del codice e rimozione di tutte le ripetizioni con eventuale uso appropriato di pattern
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

	private ListSplitterFactory factory;

	@org.junit.Before
	public void init() {
		// this.factory = new ListSplitterFactoryImpl();
	}

	@org.junit.Test
	public void testAsPairs() {
		// spezza in coppie, senza "resto"
		ListSplitter<Integer> ls = this.factory.asPairs();
		assertEquals(List.of(List.of(1,2),List.of(3,4),List.of(5,6)),ls.split(List.of(1,2,3,4,5,6)));
		assertEquals(List.of(List.of(1,2),List.of(3,4),List.of(5,6)),ls.split(List.of(1,2,3,4,5,6,7)));
	}
	
	@org.junit.Test
	public void testAsTriplets() {
		// spezza in triple, senza "resto"
		ListSplitter<Integer> ls = this.factory.asTriplets();
		assertEquals(List.of(List.of(1,2,3),List.of(4,5,6)),ls.split(List.of(1,2,3,4,5,6)));
		assertEquals(List.of(List.of(1,2,3),List.of(4,5,6)),ls.split(List.of(1,2,3,4,5,6,7,8)));
	}
	
	@org.junit.Test
	public void testAsTripletsWithRest() {
		// spezza in triple, con "resto"
		ListSplitter<Integer> ls = this.factory.asTripletsWithRest();
		assertEquals(List.of(List.of(1,2,3),List.of(4,5,6)),ls.split(List.of(1,2,3,4,5,6)));
		assertEquals(List.of(List.of(1,2,3),List.of(4,5,6),List.of(7,8)),ls.split(List.of(1,2,3,4,5,6,7,8)));
	}
	
	@org.junit.Test
	public void testBySeparator() {
		// spezza ad ogni separatore
		ListSplitter<String> ls = this.factory.bySeparator(":");
		assertEquals(List.of(List.of("a","b"),List.of(":"),List.of("c","d","e"),List.of(":"),List.of("f")),
				ls.split(List.of("a","b",":","c","d","e",":","f")));
	}
	
	@org.junit.Test
	public void optionalTestByPredicate() {
		// spezza ad ogni cambio di validità del predicato
		ListSplitter<Integer> ls = this.factory.byPredicate(x -> x>0);
		assertEquals(List.of(List.of(1,2),List.of(-5,-6),List.of(3,4),List.of(-10)),
				ls.split(List.of(1,2,-5,-6,3,4,-10)));
	}
	
	
}
