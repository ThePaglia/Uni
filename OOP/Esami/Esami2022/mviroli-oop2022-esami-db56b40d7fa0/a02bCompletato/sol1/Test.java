package a02b.sol1;

import static org.junit.Assert.*;
import java.util.*;

/**
 * Si consulti la documentazione della interfaccia CursorHelpers, che modella
 * degli helper (factory e trasformatori) per Cursor, che a sua volta modella un cursore,
 * ossia una variante essenzialmente equivalente di un iteratore.
 * 
 * Il commento alle interfacce fornite, e i metodi di test qui sotto costituiscono la necessaria 
 * spiegazione del problema.
 * 
 * Sono considerati opzionali ai fini della possibilità di correggere
 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
 * punteggio: 
 * - implementazione del quinto metodo helper (ossia, a scelta se ne realizzino 4,
 *   ma considerando il metodo fromNonEmptyList come obbligatorio)
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

	private CursorHelpers helpers;

	@org.junit.Before
	public void init() {
		this.helpers = new CursorHelpersImpl();
	}

	@org.junit.Test
	public void testFromNonEmptyList() {
		var cursor = this.helpers.fromNonEmptyList(List.of(10,20,30));
		assertEquals(10, cursor.getElement().intValue()); // 10 è il primo elemento
		assertEquals(10, cursor.getElement().intValue()); // nulla è cambiato, sempre 10
		assertEquals(10, cursor.getElement().intValue());
		assertTrue(cursor.advance());				      // si riesce a avanzare		
		assertEquals(20, cursor.getElement().intValue()); // 20 è il prossimo elemento
		assertEquals(20, cursor.getElement().intValue());
		assertTrue(cursor.advance());					  // si riesce a avanzare			
		assertEquals(30, cursor.getElement().intValue()); // 30 è il prossimo elemento	
		assertFalse(cursor.advance());					  // NON si riesce ad avanzare	
		assertEquals(30, cursor.getElement().intValue()); // nulla è cambiato, sempre 30
		assertEquals(30, cursor.getElement().intValue());
		assertFalse(cursor.advance());
	}

	@org.junit.Test
	public void testNaturals() {
		var cursor = this.helpers.naturals();
		assertEquals(0, cursor.getElement().intValue());
		assertTrue(cursor.advance());				      
		assertEquals(1, cursor.getElement().intValue()); 
		assertEquals(1, cursor.getElement().intValue());
		assertTrue(cursor.advance());				      
		assertEquals(2, cursor.getElement().intValue()); 
		assertEquals(2, cursor.getElement().intValue());
		assertTrue(cursor.advance());					 
		assertTrue(cursor.advance());					 
		assertTrue(cursor.advance());					
		assertEquals(5, cursor.getElement().intValue()); 
		for (int i=0; i<100; i++){
			cursor.advance();
		}
		assertEquals(105, cursor.getElement().intValue()); 
	}

	@org.junit.Test
	public void testTake() {
		var cursor = this.helpers.take(this.helpers.fromNonEmptyList(List.of("a","b","c","d","e")),4);
		assertEquals("a", cursor.getElement());
		assertTrue(cursor.advance());				      
		assertEquals("b", cursor.getElement());
		assertTrue(cursor.advance());				      
		assertEquals("c", cursor.getElement());
		assertTrue(cursor.advance());				      
		assertEquals("d", cursor.getElement());
		assertFalse(cursor.advance());	
		
		cursor = this.helpers.take(this.helpers.fromNonEmptyList(List.of("a","b","c","d","e")),10);
		assertEquals("a", cursor.getElement());
		assertTrue(cursor.advance());				      
		assertEquals("b", cursor.getElement());
		assertTrue(cursor.advance());				      
		assertEquals("c", cursor.getElement());
		assertTrue(cursor.advance());				      
		assertEquals("d", cursor.getElement());
		assertTrue(cursor.advance());	
		assertEquals("e", cursor.getElement());
		assertFalse(cursor.advance());	
	}

	@org.junit.Test
	public void testForEach() {
		final StringBuilder stringBuilder = new StringBuilder();
		var cursor = this.helpers.fromNonEmptyList(List.of("a","b","a","d","e"));
		// per ogni elemento di cursor, lo appende allo stringbuilder
		this.helpers.forEach(cursor, s -> stringBuilder.append(s));

		// alla fine ci troviamo "abade"
		assertEquals("abade", stringBuilder.toString());
	}

	@org.junit.Test
	public void testToList() {
		var cursor = this.helpers.fromNonEmptyList(List.of("a","b","c","d","e"));
		// lista ottenuta dai primi 3 elementi del cursore
		var list = this.helpers.toList(cursor, 3);
		assertEquals(List.of("a","b","c"), list);
		
		cursor = this.helpers.fromNonEmptyList(List.of("a","b","c","d","e"));
		// lista ottenuta dai primi 7 elementi del cursore, che sono troppi, quindi prendi i 5 del cursore
		list = this.helpers.toList(cursor, 7);
		assertEquals(List.of("a","b","c","d","e"), list);
	}

}
