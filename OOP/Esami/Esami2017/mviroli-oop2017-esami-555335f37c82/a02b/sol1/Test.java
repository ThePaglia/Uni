package a02b.sol1;

import static org.junit.Assert.*;
import java.util.*;

public class Test {
	
	/*
	 * Si fornisce una interfaccia TransducerFactory (da implementare con una classe TransducerFactoryImpl con costruttore senza argomenti),
	 * che modella una factory per dei Transducer, ossia dei trasformatori di sequenze. Un Transducer riceve oggetti in sequenza finché
	 * il suo input non viene chiuso. A gruppi, questi oggetti vengono trasformati in un oggetto da produrre in uscita con una modalità simile ad 
	 * un iteratore: l'estrazione può avvenire appena i valori sono disponibili o anche successivamente.
	 * La factory chiede di implementare due tipi di transducer:
	 * - concatenator(N): raggruppa gli oggetti a liste di N elementi, e per ognuno produce la concatenazione dei loro toString
	 * - summer: raggruppa interi due a due, e per ogni coppia produce la loro somma
	 *
	 * In entrambi i casi, quando il transducer viene chiuso gli elementi rimasti che contiene concorrono alla formazione di almeno un
	 * elemento in uscita.
	 * 
	 * Si noti che i test con annotazione @org.junit.Test(expected = ...) passano solo se viene lanciata l'eccezione indicata nei ...
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
	 * della totalità del punteggio:
	 * - implementazione dei test chiamati optionalTestXYZ (relativi al transducer pairsummer) 
	 * - la buona progettazione della soluzione, in particolare con minimizzazione di ripetizioni
	 * 
	 */
	
	@org.junit.Test
	public void testConcatenator() {
		Transducer<Integer,String> t = new TransducerFactoryImpl().makeConcatenator(3); // concatena interi 3 a 3
		assertFalse(t.isNextOutputReady());
		assertFalse(t.isOutputOver());
		t.provideNextInput(1);	// inserisco 1
		assertFalse(t.isNextOutputReady());
		assertFalse(t.isOutputOver());
		t.provideNextInput(2);
		t.provideNextInput(3);
		t.provideNextInput(4);  // inserisco 2,3,4: ora mi trovo internamente 1,2,3,4
		assertTrue(t.isNextOutputReady());
		assertFalse(t.isOutputOver());
		assertEquals(t.getOutputElement(),"123"); // emetto una uscita, rimane 4
		assertFalse(t.isNextOutputReady());
		assertFalse(t.isOutputOver());
		t.provideNextInput(5);
		t.provideNextInput(6);  // inserisco 5,6: ora mi trovo internamente 4,5,6
		assertTrue(t.isNextOutputReady());
		assertFalse(t.isOutputOver());
		assertEquals(t.getOutputElement(),"456"); // emetto una uscita, non rimane nulla
		t.provideNextInput(7);  // inserisco 7: ora mi trovo internamente 7
		assertFalse(t.isNextOutputReady());
		assertFalse(t.isOutputOver());
		t.inputIsOver();		// l'input viene chiuso
		assertTrue(t.isNextOutputReady()); // il 7 da solo è disponibile per una uscita
		assertFalse(t.isOutputOver());
		assertEquals(t.getOutputElement(),"7");  // emetto una uscita, non rimane nulla
		assertFalse(t.isNextOutputReady());
		assertTrue(t.isOutputOver());
	}
	
	@org.junit.Test
	public void testEmptyConcatenator() {
		// test in cui si chiude il transducer subito..
		Transducer<Integer,String> t = new TransducerFactoryImpl().makeConcatenator(3);
		assertFalse(t.isNextOutputReady());
		assertFalse(t.isOutputOver());
		t.inputIsOver();
		assertFalse(t.isNextOutputReady());
		assertTrue(t.isOutputOver());
	}
		
	@org.junit.Test(expected = IllegalStateException.class)
	public void testGettingWhenNotAvailable() {
		// test in cui si tenta illegalmente di prendere output anche quando non ve ne sono..
		Transducer<Integer,String> t = new TransducerFactoryImpl().makeConcatenator(3);
		assertFalse(t.isNextOutputReady());
		assertFalse(t.isOutputOver());
		t.provideNextInput(1);
		assertFalse(t.isNextOutputReady());
		t.getOutputElement();
	}
	
	@org.junit.Test(expected = IllegalStateException.class)
	public void testGettingWhenOver() {
		// test in cui si tenta illegalmente di prendere un output anche quando l'input è chiuso e non ve ne sono
		Transducer<Integer,String> t = new TransducerFactoryImpl().makeConcatenator(3);
		assertFalse(t.isNextOutputReady());
		assertFalse(t.isOutputOver());
		t.provideNextInput(1);
		t.inputIsOver();
		assertEquals(t.getOutputElement(),"1");
		t.getOutputElement();
	}
	
	@org.junit.Test(expected = IllegalStateException.class)
	public void testClosingTwice() {
		// test in cui si tenta illegalmente di chiudere un input già chiuso
		Transducer<Integer,String> t = new TransducerFactoryImpl().makeConcatenator(3);
		t.inputIsOver();
		t.inputIsOver();
	}
	
	// da qui in poi solo test opzionali
	
	@org.junit.Test
	public void optionalTestSummer() {
		// test per il sommatore di coppie
		// unica nota: alla chiusura, se rimane un intero da solo questo viene prodotto in uscita
		Transducer<Integer,Integer> t = new TransducerFactoryImpl().makePairSummer();
		assertFalse(t.isNextOutputReady());
		assertFalse(t.isOutputOver());
		t.provideNextInput(1);
		assertFalse(t.isNextOutputReady());
		assertFalse(t.isOutputOver());
		t.provideNextInput(20);
		t.provideNextInput(300);
		assertTrue(t.isNextOutputReady());
		assertFalse(t.isOutputOver());
		assertEquals(t.getOutputElement().intValue(),21);
		assertFalse(t.isNextOutputReady());
		assertFalse(t.isOutputOver());
		t.provideNextInput(4000);
		t.provideNextInput(50000);
		assertTrue(t.isNextOutputReady());
		assertFalse(t.isOutputOver());
		assertEquals(t.getOutputElement().intValue(),4300);
		t.inputIsOver(); // 5000 rimane da solo
		assertTrue(t.isNextOutputReady());
		assertFalse(t.isOutputOver());
		assertEquals(t.getOutputElement().intValue(),50000); // 5000 viene emesso
		assertFalse(t.isNextOutputReady());
		assertTrue(t.isOutputOver());
	}

	@org.junit.Test(expected = IllegalStateException.class)
	public void optionalTestGettingWhenNotAvailableOnSummer() {
		// come testGettingWhenNotAvailable ma per Summer
		Transducer<Integer,Integer> t = new TransducerFactoryImpl().makePairSummer();
		assertFalse(t.isNextOutputReady());
		assertFalse(t.isOutputOver());
		t.provideNextInput(1);
		assertFalse(t.isNextOutputReady());
		t.getOutputElement();
	}
	
	@org.junit.Test(expected = IllegalStateException.class)
	public void optionalTestGettingWhenOverOnSummer() {
		// come testGettingWhenOver ma per Summer
		Transducer<Integer,Integer> t = new TransducerFactoryImpl().makePairSummer();
		assertFalse(t.isNextOutputReady());
		assertFalse(t.isOutputOver());
		t.provideNextInput(1);
		t.inputIsOver();
		assertEquals(t.getOutputElement().intValue(),1);
		t.getOutputElement();
	}
	
}

