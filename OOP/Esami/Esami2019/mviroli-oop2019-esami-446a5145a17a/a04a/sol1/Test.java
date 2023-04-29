package a04a.sol1;

import static org.junit.Assert.*;

import java.util.List;

public class Test {
	
	/*
	 * Implementare l'interfaccia SequencesProvidersFactory come indicato nel metodo initFactory qui sotto.
	 * Realizza un concetto di factory per SequencesProvider, che a sua volta modella un iteratore di liste,
	 * ossia che produce via via delle liste di elementi.
	 * 
	 * Il commento alle interfacce fornite, e i metodi di test qui sotto costituiscono la necessaria 
	 * spiegazione del problema.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, 
	 * ma concorrono comunque al raggiungimento della totalità del punteggio:
	 * - implementazione dei test chiamati optionalTestXYZ (relativi al metodo SequencesProvider.reset) 
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
	
	private SequencesProvidersFactory factory = null;
	
	@org.junit.Before
	public void initFactory() {
		this.factory = new SequencesProvidersFactoryImpl();
	}

	@org.junit.Test
	public void testIterative() {
		// iteratore che produce (), (10), (10,10), (10,10,10),...
		final SequencesProvider<Integer> sp = this.factory.iterative(10);
		assertEquals(sp.nextSequence(),List.of());
		assertEquals(sp.nextSequence(),List.of(10));
		assertEquals(sp.nextSequence(),List.of(10,10));
		assertEquals(sp.nextSequence(),List.of(10,10,10));
		assertEquals(sp.nextSequence(),List.of(10,10,10,10));
		assertTrue(sp.hasOtherSequences());
		for (int i=0; i<100; i++) {
			sp.nextSequence();
		}
		assertEquals(sp.nextSequence().size(),105);
	}
	
	@org.junit.Test
	public void testAlternating() {
		// iteratore che produce (), (), (a), (b), (a,a), (b,b),...
		final SequencesProvider<String> sp = this.factory.alternating("a","b");
		assertEquals(sp.nextSequence(),List.of());
		assertEquals(sp.nextSequence(),List.of());
		assertEquals(sp.nextSequence(),List.of("a"));
		assertEquals(sp.nextSequence(),List.of("b"));
		assertEquals(sp.nextSequence(),List.of("a","a"));
		assertEquals(sp.nextSequence(),List.of("b","b"));
		assertEquals(sp.nextSequence(),List.of("a","a","a"));
		assertEquals(sp.nextSequence(),List.of("b","b","b"));
		assertTrue(sp.hasOtherSequences());
		for (int i=0; i<100; i++) {
			sp.nextSequence();
		}
		assertEquals(sp.nextSequence().size(),54);
	}
	
	@org.junit.Test
	public void testIterativeBounded() {
		// come iterative, ma produce solo 4 liste
		final SequencesProvider<Integer> sp = this.factory.iterativeBounded(10,4);
		assertEquals(sp.nextSequence(),List.of());
		assertEquals(sp.nextSequence(),List.of(10));
		assertEquals(sp.nextSequence(),List.of(10,10));
		assertTrue(sp.hasOtherSequences());
		assertEquals(sp.nextSequence(),List.of(10,10,10));
		assertFalse(sp.hasOtherSequences());
	}
	
	@org.junit.Test
	public void testAlternatingBounded() {
		// come alternating, ma produce solo 7 liste
		final SequencesProvider<String> sp = this.factory.alternatingBounded("a","b",7);
		assertEquals(sp.nextSequence(),List.of());
		assertEquals(sp.nextSequence(),List.of());
		assertEquals(sp.nextSequence(),List.of("a"));
		assertEquals(sp.nextSequence(),List.of("b"));
		assertEquals(sp.nextSequence(),List.of("a","a"));
		assertEquals(sp.nextSequence(),List.of("b","b"));
		assertTrue(sp.hasOtherSequences());
		assertEquals(sp.nextSequence(),List.of("a","a","a"));
		assertFalse(sp.hasOtherSequences());
	}
	
	@org.junit.Test
	public void optionalTestReset1() {
		final SequencesProvider<Integer> sp = this.factory.iterative(10);
		assertEquals(sp.nextSequence(),List.of());
		assertEquals(sp.nextSequence(),List.of(10));
		sp.reset();
		assertEquals(sp.nextSequence(),List.of());
		assertEquals(sp.nextSequence(),List.of(10));
		assertEquals(sp.nextSequence(),List.of(10,10));		
	}
	
	@org.junit.Test
	public void optionalTestReset2() {
		final SequencesProvider<String> sp = this.factory.alternatingBounded("a","b",7);
		assertEquals(sp.nextSequence(),List.of());
		assertEquals(sp.nextSequence(),List.of());
		assertEquals(sp.nextSequence(),List.of("a"));
		assertEquals(sp.nextSequence(),List.of("b"));
		sp.reset();
		assertEquals(sp.nextSequence(),List.of());
		assertEquals(sp.nextSequence(),List.of());
		assertEquals(sp.nextSequence(),List.of("a"));
		assertEquals(sp.nextSequence(),List.of("b"));
		sp.reset();
		assertEquals(sp.nextSequence(),List.of());
		assertEquals(sp.nextSequence(),List.of());
		assertEquals(sp.nextSequence(),List.of("a"));
		assertEquals(sp.nextSequence(),List.of("b"));
		assertEquals(sp.nextSequence(),List.of("a","a"));
		assertEquals(sp.nextSequence(),List.of("b","b"));
		assertTrue(sp.hasOtherSequences());
		assertEquals(sp.nextSequence(),List.of("a","a","a"));
		assertFalse(sp.hasOtherSequences());
	}
	
}

