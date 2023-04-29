package a04b.sol1;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;

public class Test {
	
	/*
	 * Un parser è un component software che consuma una sequenza di token (stringhe) una a una, e quando
	 * finita riconosce se la "frase" complessiva corrisponde ad una determinata sintassi (o struttura/grammatica),
	 * eventualmente producendo un risultato.
	 * Implementare l'interfaccia ParsersFactory come indicato nel metodo initFactory qui sotto.
	 * Realizza un concetto di factory per Parser<R>, che a sua volta modella un parser che ritorna un risultato 
	 * di tipo R.
	 * 
	 * Il commento alle interfacce fornite, e i metodi di test qui sotto costituiscono la necessaria 
	 * spiegazione del problema.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, 
	 * ma concorrono comunque al raggiungimento della totalità del punteggio:
	 * - implementazione dei test chiamati optionalTestXYZ (relativi al metodo ParserFactory.createNonEmptySequenceParserToString) 
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
	
	private ParsersFactory factory = null;
	
	@org.junit.Before
	public void initFactory() {
		this.factory = new ParsersFactoryImpl();
	}

	@org.junit.Test
	public void testSequenceParserOK() {
		// un parser per sequenze arbitrariamente lunghe di "a"
		final Parser<Integer> sp = this.factory.createSequenceParserToCount("a");
		assertTrue(sp.getNext("a"));
		assertTrue(sp.getNext("a"));
		assertTrue(sp.getNext("a"));
		// la frase "a","a","a" và riconosciuta, con risultato 3
		assertEquals(sp.completeAndCreateResult().intValue(),3);
	}
	
	@org.junit.Test
	public void testSequenceParserOKWithList() {
		// un parser per sequenze arbitrariamente lunghe di "a"
		final Parser<Integer> sp = this.factory.createSequenceParserToCount("a");
		assertTrue(sp.getAllInList(List.of("a","a","a","a")));
		// la frase "a","a","a","a" và riconosciuta, con risultato 4
		assertEquals(sp.completeAndCreateResult().intValue(),4);
	}
	
	@org.junit.Test
	public void testSequenceParserWithEmptySequence() {
		// un parser per sequenze arbitrariamente lunghe di "a"
		final Parser<Integer> sp = this.factory.createSequenceParserToCount("a");
		// la frase senza token, caso particolare di una sequenza di "a" và riconosciuta, con risultato 0
		assertEquals(sp.completeAndCreateResult().intValue(),0);
	}
	
	@org.junit.Test
	public void testSequenceParserThatFails() {
		// un parser per sequenze arbitrariamente lunghe di "a"
		final Parser<Integer> sp = this.factory.createSequenceParserToCount("a");
		assertTrue(sp.getNext("a")); // fin qui ok
		assertTrue(sp.getNext("a")); // fin qui ok
		assertFalse(sp.getNext("b")); // token non riconosciuto, il parser diventa inusabile
		assertFalse(sp.getNext("a")); // non riconosce più niente..
		assertFalse(sp.getNext("a"));
		try {
			// il tentativo di completare causa una IllegalStateException
			sp.completeAndCreateResult();
			Assert.fail();
		} catch (IllegalStateException e) {
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	@org.junit.Test
	public void optionalTestNonEmptySequenceParserOK() {
		// un parser per sequenze non vuote di "b".. che dà la concatenazione come risultato
		final Parser<String> sp = this.factory.createNonEmptySequenceParserToString("b");
		assertTrue(sp.getAllInList(List.of("b","b","b","b")));
		// la frase "b","b","b","b" và riconosciuta, con risultato "bbbb"
		assertEquals(sp.completeAndCreateResult(),"bbbb");
	}
	
	@org.junit.Test
	public void optionalTestNonEmptySequenceParsersThatFails() {
		// un parser per sequenze non vuote di "b".. che dà la concatenazione come risultato
		final Parser<String> sp = this.factory.createNonEmptySequenceParserToString("b");
		// la frase "b","b","a","b" NON va riconosciuta
		assertFalse(sp.getAllInList(List.of("b","b","a","b")));
		try {
			// il tentativo di completare causa una IllegalStateException
			sp.completeAndCreateResult();
			Assert.fail();
		} catch (IllegalStateException e) {
		} catch (Exception e) {
			Assert.fail();
		}
		// un parser per sequenze non vuote di "b".. che dà la concatenazione come risultato
		final Parser<String> sp2 = this.factory.createNonEmptySequenceParserToString("b");
		try {
			// il tentativo di completare causa una IllegalStateException, perché non si riconosce la seq. vuota
			sp2.completeAndCreateResult();
			Assert.fail();
		} catch (IllegalStateException e) {
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	@org.junit.Test
	public void testExpressionParserOK1() {
		// un parser per espressioni con +/- su 0/1
		final Parser<Integer> sp = this.factory.createExpressionParserToResult();
		assertTrue(sp.getAllInList(List.of("1","+","1","+","0")));
		// espressione corretta, risultato 2
		assertEquals(sp.completeAndCreateResult().intValue(),2);
	}
	
	@org.junit.Test
	public void testExpressionParserOK2() {
		// un parser per espressioni con +/- su 0/1
		final Parser<Integer> sp = this.factory.createExpressionParserToResult();
		assertTrue(sp.getAllInList(List.of("1","-","1","+","1")));
		// espressione corretta, risultato 1
		assertEquals(sp.completeAndCreateResult().intValue(),1);
	}
	
	@org.junit.Test
	public void testExpressionParserOK3() {
		// un parser per espressioni con +/- su 0/1
		final Parser<Integer> sp = this.factory.createExpressionParserToResult();
		assertTrue(sp.getAllInList(List.of("1","-","1","-","1","-","0")));
		// espressione corretta, risultato -1
		assertEquals(sp.completeAndCreateResult().intValue(),-1);
	}
	
	@org.junit.Test
	public void testExpressionParsersThatFails1() {
		// un parser per espressioni con +/- su 0/1
		final Parser<Integer> sp = this.factory.createExpressionParserToResult();
		// espressione NON corretta
		assertFalse(sp.getAllInList(List.of("1","-","1","1")));
	}
	
	@org.junit.Test
	public void testExpressionParsersThatFails2() {
		// un parser per espressioni con +/- su 0/1
		final Parser<Integer> sp = this.factory.createExpressionParserToResult();
		// espressione NON corretta
		assertFalse(sp.getAllInList(List.of("1","-","-","1")));
	}
	
	@org.junit.Test
	public void testExpressionParsersThatFails3() {
		// un parser per espressioni con +/- su 0/1
		final Parser<Integer> sp = this.factory.createExpressionParserToResult();
		// espressione NON corretta
		assertFalse(sp.getAllInList(List.of("+","1","-","1")));
	}
	
	@org.junit.Test
	public void testExpressionParsersThatFails4() {
		// un parser per espressioni con +/- su 0/1
		final Parser<Integer> sp = this.factory.createExpressionParserToResult();
		// espressione NON corretta
		assertFalse(sp.getAllInList(List.of("1","-","2")));
	}
}

