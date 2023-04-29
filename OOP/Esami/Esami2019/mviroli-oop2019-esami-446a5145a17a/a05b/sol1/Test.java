package a05b.sol1;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Test {
	
	/*
	 * Implementare l'interfaccia IteratorIteratorFactory come indicato nel metodo initFactory qui sotto.
	 * Realizza una factory per vari iteratori, ognuno dei quali ad ogni passo produce a sua volta un iteratore.
	 * Un iteratore di iteratore è un modo per generare via via varie sequenze di elementi.
	 * 
	 * Il commento alle interfacce fornite, e i metodi di test qui sotto costituiscono la necessaria 
	 * spiegazione del problema.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, 
	 * ma concorrono comunque al raggiungimento della totalità del punteggio:
	 * - implementazione dei test chiamati optionalTestXYZ (relativi al metodo increasingSizeWithSingleton() della factory) 
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
	
	private IteratorIteratorFactory factory = null;
	
	@org.junit.Before
	public void initFactory() {
		this.factory = new IteratorIteratorFactoryImpl();
	}

	@org.junit.Test
	public void testConstant() {
		final Iterator<Iterator<String>> ii = this.factory.constantWithSingleton("a");
		
		// ii first gives an iterator, i1, giving only an "a"
		assertTrue(ii.hasNext());
		final Iterator<String> i1 = ii.next();
		assertTrue(i1.hasNext());
		assertEquals("a",i1.next());
		assertFalse(i1.hasNext());
		
		// ii then gives an iterator, i2, giving only an "a"
		assertTrue(ii.hasNext());
		final Iterator<String> i2 = ii.next();
		assertTrue(i2.hasNext());
		assertEquals("a",i2.next());
		assertFalse(i2.hasNext());
		
		// after many times..
		for (int i=0;i<100;i++) {
			ii.next();
		}
		
		//.. ii still gives an iterator, i3, giving only an "a"
		assertTrue(ii.hasNext());
		final Iterator<String> i3 = ii.next();
		assertTrue(i3.hasNext());
		assertEquals("a",i3.next());
		assertFalse(i3.hasNext());
	}
	
	private <E> List<E> iteratorToList(Iterator<E> it){
		final List<E> l = new LinkedList<>();
		while (it.hasNext()) {
			l.add(it.next());
		}
		return l;
	}
	
	@org.junit.Test
	public void testConstantVariant() {
		// an equivalent formulation of the test above, more succint thanks to method iteratorToList
		final Iterator<Iterator<String>> ii = this.factory.constantWithSingleton("a");
		
		// ii first gives the sequence with only an "a" 
		assertEquals(List.of("a"), this.iteratorToList(ii.next()));
		// ii then gives the sequence with only an "a" 
		assertEquals(List.of("a"), this.iteratorToList(ii.next()));
		// after many times..
		for (int i=0;i<100;i++) {
			ii.next();
		}
		// ..ii still gives the sequence with only an "a" 
		assertEquals(List.of("a"), this.iteratorToList(ii.next()));
		assertTrue(ii.hasNext());
	}
	
	@org.junit.Test
	public void optionalTestIncreasing() {
		final Iterator<Iterator<String>> ii = this.factory.increasingSizeWithSingleton("a");
		
		// the sequences returned each time by the iterator
		assertEquals(List.of("a"), this.iteratorToList(ii.next()));
		assertEquals(List.of("a","a"), this.iteratorToList(ii.next()));
		assertEquals(List.of("a","a","a"), this.iteratorToList(ii.next()));
		assertEquals(List.of("a","a","a","a"), this.iteratorToList(ii.next()));
		for (int i=0;i<100;i++) {
			ii.next();
		}
		// after 100 attempts can still give more
		assertTrue(ii.hasNext());
	}
	
	@org.junit.Test
	public void testSquares() {
		final Iterator<Iterator<Integer>> ii = this.factory.squares();
		// giving {0}, {0,1}, {0,1,4}, {0,1,4,9,16}... namely the square numbers
		assertEquals(List.of(0), this.iteratorToList(ii.next()));
		assertEquals(List.of(0,1), this.iteratorToList(ii.next()));
		assertEquals(List.of(0,1,4), this.iteratorToList(ii.next()));
		assertEquals(List.of(0,1,4,9), this.iteratorToList(ii.next()));
		assertEquals(List.of(0,1,4,9,16), this.iteratorToList(ii.next()));
		for (int i=0;i<100;i++) {
			ii.next();
		}
		assertTrue(ii.hasNext());
	}
	
	@org.junit.Test
	public void testEvens() {
		final Iterator<Iterator<Integer>> ii = this.factory.evens();
		
		// giving {0}, {0,2}, {0,2,4}, ... namely the even numbers
		assertEquals(List.of(0), this.iteratorToList(ii.next()));
		assertEquals(List.of(0,2), this.iteratorToList(ii.next()));
		assertEquals(List.of(0,2,4), this.iteratorToList(ii.next()));
		assertEquals(List.of(0,2,4,6), this.iteratorToList(ii.next()));
		assertEquals(List.of(0,2,4,6,8), this.iteratorToList(ii.next()));
		for (int i=0;i<100;i++) {
			ii.next();
		}
		assertTrue(ii.hasNext());
	}


	
}

