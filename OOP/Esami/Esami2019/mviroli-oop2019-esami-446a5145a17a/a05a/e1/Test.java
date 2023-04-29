package a05a.e1;

import static org.junit.Assert.*;

import java.util.List;

public class Test {
	
	/*
	 * Implementare l'interfaccia IteratorOfListsFactory come indicato nel metodo initFactory qui sotto.
	 * Realizza un concetto di factory per IteratorOfLists, che a sua volta modella un iteratore di liste,
	 * ossia uno oggetto che produce via via delle liste di elementi.
	 * 
	 * Il commento alle interfacce fornite, e i metodi di test qui sotto costituiscono la necessaria 
	 * spiegazione del problema.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, 
	 * ma concorrono comunque al raggiungimento della totalità del punteggio:
	 * - implementazione dei test chiamati optionalTestXYZ (relativi al metodo IteratorOfLists.reset) 
	 * - la buona progettazione della soluzione, in particolare con minimizzazione di ripetizioni
	 * 
	 * Si tolga il commento dal metodo initFactory.
	 * 
	 * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 9 punti
	 * - correttezza della parte opzionale: 4 punti
	 * - qualità della soluzione: 4 punti
	 * 
	 */
	
	private IteratorOfListsFactory factory = null;
	
	@org.junit.Before
	public void initFactory() {
		//this.factory = new IteratorOfListsFactoryImpl();
	}

	@org.junit.Test
	public void testIterative() {
		// Iteratore che produce {}, poi {10}, poi {10,10}, e così via
		final IteratorOfLists<Integer> sp = this.factory.iterative(10);
		assertEquals(sp.nextList(),List.of());
		assertEquals(sp.nextList(),List.of(10));
		assertEquals(sp.nextList(),List.of(10,10));
		assertEquals(sp.nextList(),List.of(10,10,10));
		assertEquals(sp.nextList(),List.of(10,10,10,10));
		assertTrue(sp.hasOtherLists());
		for (int i=0; i<100; i++) {
			sp.nextList();
		}
		assertEquals(sp.nextList().size(),105);
	}
	
	@org.junit.Test
	public void testIterativeOnList() {
		// Iteratore che produce {}, poi {a}, poi {a,b}, poi {a,b,c}, poi {a,b,c,a}, e così via
		final IteratorOfLists<String> sp = this.factory.iterativeOnList(List.of("a","b","c"));
		assertEquals(sp.nextList(),List.of());
		assertEquals(sp.nextList(),List.of("a"));
		assertEquals(sp.nextList(),List.of("a","b"));
		assertEquals(sp.nextList(),List.of("a","b","c"));
		assertEquals(sp.nextList(),List.of("a","b","c","a"));
		assertEquals(sp.nextList(),List.of("a","b","c","a","b"));
		assertEquals(sp.nextList(),List.of("a","b","c","a","b","c"));
		assertEquals(sp.nextList(),List.of("a","b","c","a","b","c","a"));
		assertTrue(sp.hasOtherLists());
		for (int i=0; i<100; i++) {
			sp.nextList();
		}
		assertEquals(sp.nextList().size(),108);
	}
	
	@org.junit.Test
	public void testIterativeWithPreamble() {
		// iteratore che produce {1,2,3}, {1,2,3,10}, {1,2,3,10,10}, e così via
		final IteratorOfLists<Integer> sp = this.factory.iterativeWithPreamble(10, List.of(1,2,3));
		assertEquals(sp.nextList(),List.of(1,2,3));
		assertEquals(sp.nextList(),List.of(1,2,3,10));
		assertEquals(sp.nextList(),List.of(1,2,3,10,10));
		assertEquals(sp.nextList(),List.of(1,2,3,10,10,10));
		assertEquals(sp.nextList(),List.of(1,2,3,10,10,10,10));
		assertTrue(sp.hasOtherLists());
		for (int i=0; i<100; i++) {
			sp.nextList();
		}
		assertEquals(sp.nextList().size(),108);
	}
	

	@org.junit.Test
	public void optionalTestReset1() {
		final IteratorOfLists<Integer> sp = this.factory.iterative(10);
		assertEquals(sp.nextList(),List.of());
		assertEquals(sp.nextList(),List.of(10));
		sp.reset();
		assertEquals(sp.nextList(),List.of());
		assertEquals(sp.nextList(),List.of(10));
		assertEquals(sp.nextList(),List.of(10,10));		
	}
	
	@org.junit.Test
	public void optionalTestReset2() {
		final IteratorOfLists<String> sp = this.factory.iterativeOnList(List.of("a","b","c"));
		assertEquals(sp.nextList(),List.of());
		assertEquals(sp.nextList(),List.of("a"));
		assertEquals(sp.nextList(),List.of("a","b"));
		sp.reset();
		assertEquals(sp.nextList(),List.of());
		assertEquals(sp.nextList(),List.of("a"));
		assertEquals(sp.nextList(),List.of("a","b"));
		assertEquals(sp.nextList(),List.of("a","b","c"));
		assertEquals(sp.nextList(),List.of("a","b","c","a"));
		assertEquals(sp.nextList(),List.of("a","b","c","a","b"));
		assertEquals(sp.nextList(),List.of("a","b","c","a","b","c"));
		assertEquals(sp.nextList(),List.of("a","b","c","a","b","c","a"));
	}
	
}

