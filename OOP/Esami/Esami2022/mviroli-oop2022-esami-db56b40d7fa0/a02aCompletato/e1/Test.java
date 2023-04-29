package a02a.e1;

import static org.junit.Assert.*;
import java.util.*;

/**
 * Si consulti la documentazione della interfaccia RecursiveIteratorHelpers, che modella
 * degli helper (factory e trasformatori) per RecursiveIterator, che a sua volta modella un iterator,
 * ossia una variante essenzialmente equivalente di un iteratore.
 * 
 * Il commento alle interfacce fornite, e i metodi di test qui sotto costituiscono la necessaria 
 * spiegazione del problema.
 * 
 * Sono considerati opzionali ai fini della possibilità di correggere
 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
 * punteggio: 
 * - implementazione del quinto metodo helper (ossia, a scelta se ne realizzino 4,
 *   ma considerando il metodo fromList come obbligatorio)
 * - elementi di qualità come concisione del codice, rimozione di ripetizioni, uso parsimonioso della memoria
 *
 * Si tolga il commento dal metodo init.
 * 
 * Indicazioni di punteggio:
 * - correttezza della parte obbligatoria: 10 punti
 * - correttezza della parte opzionale: 4 punti  
 * - qualità della soluzione: 3 punti
 * - bug di programmazione, o violazione di regole base di programmazione Java, comportano decurtamento del punteggio 
 *   complessivo, anche in caso di bonus
 */

public class Test {

	private RecursiveIteratorHelpers factory;

	@org.junit.Before
	public void init() {
		this.factory = new RecursiveIteratorHelpersImpl();
	}

	@org.junit.Test
	public void testFromList() {
		var rit = this.factory.fromList(List.of(10,20,30));
		assertEquals(10, rit.getElement().intValue()); // 10 è il primo elemento
		assertEquals(10, rit.getElement().intValue()); // anche in chiamate successive
		assertEquals(10, rit.getElement().intValue());
		rit = rit.next();							   // si ottiene il prossimo iteratore	
		assertEquals(20, rit.getElement().intValue()); // 20 è il prossimo elemento
		assertEquals(20, rit.getElement().intValue());
		rit = rit.next();							   // si ottiene il prossimo iteratore			
		assertEquals(30, rit.getElement().intValue());
		assertEquals(30, rit.getElement().intValue());
		assertEquals(30, rit.getElement().intValue());
		rit = rit.next();
		assertNull(rit);	// iterazione finita

		assertNull(this.factory.fromList(List.of())); // iterazione vuota
	}

	@org.junit.Test
	public void testToList() {
		var rit = this.factory.fromList(List.of(10,20,30));
		// lista ottenuta dai primi 10 elementi dell'iteratore, che sono troppi, quindi prendi i 3 dell'iteratore
		assertEquals(List.of(10,20,30), this.factory.toList(rit,10));
		var rit2 = this.factory.fromList(List.of(10,20,30));
		// lista ottenuta dai primi 2 elementi dell'iteratore
		assertEquals(List.of(10,20), this.factory.toList(rit2,2));
	}

	@org.junit.Test
	public void testZip() {
		var rit = this.factory.fromList(List.of(10,20,30,40));
		var rit2 = this.factory.fromList(List.of("a","b","c"));
		var ritZip = this.factory.zip(rit,rit2);
		assertEquals(new Pair<>(10,"a"), ritZip.getElement());
		ritZip = ritZip.next();
		assertEquals(new Pair<>(20,"b"), ritZip.getElement());
		ritZip = ritZip.next();
		assertEquals(new Pair<>(30,"c"), ritZip.getElement());
		ritZip = ritZip.next();
		// nota, non ci sono più di 3 elementi...
		assertNull(ritZip);
	}

	@org.junit.Test
	public void testZipWithIndex() {
		var rit = this.factory.fromList(List.of(10,20,30));
		// si zippa con l'iteratore che produce 0,1,2,3,4,...
		var ritZip = this.factory.zipWithIndex(rit);
		assertEquals(new Pair<>(10,0), ritZip.getElement());
		ritZip = ritZip.next();
		assertEquals(new Pair<>(20,1), ritZip.getElement());
		ritZip = ritZip.next();
		assertEquals(new Pair<>(30,2), ritZip.getElement());
		ritZip = ritZip.next();
		assertNull(ritZip);
	}

	@org.junit.Test
	public void testAlternate() {
		var rit = this.factory.fromList(List.of(10,20));
		var rit2 = this.factory.fromList(List.of(1,2,3,4));
		var ritAlt = this.factory.alternate(rit,rit2);
		assertEquals(10, ritAlt.getElement().intValue());
		ritAlt = ritAlt.next();
		assertEquals(1, ritAlt.getElement().intValue());
		ritAlt = ritAlt.next();
		assertEquals(20, ritAlt.getElement().intValue());
		ritAlt = ritAlt.next();
		assertEquals(2, ritAlt.getElement().intValue());
		ritAlt = ritAlt.next();
		// è finito il primo iteratore, si procede col secondo
		assertEquals(3, ritAlt.getElement().intValue());
		ritAlt = ritAlt.next();
		assertEquals(4, ritAlt.getElement().intValue());
		ritAlt = ritAlt.next();
		assertNull(ritAlt);
	}
}
