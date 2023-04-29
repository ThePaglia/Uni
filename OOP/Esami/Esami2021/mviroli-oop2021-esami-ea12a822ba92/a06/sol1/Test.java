package a06.sol1;

import static org.junit.Assert.*;

import java.util.List;

public class Test {

	/*
	 * Implementare l'interfaccia CirclerFactory come indicato nel metodo initFactory qui sotto. 
	 * Realizza una factory per dei Circler<T>, ossia oggetti con funzionalità per iterare i dati
	 * di una sorgente costituita da una lista (finita) di elementi del tipo T. 
	 * 
	 * Il commento alle interfacce fornite, e i metodi di test qui sotto
	 * costituiscono la necessaria spiegazione del problema.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere
	 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
	 * punteggio: 
	 * 
	 * - i metodi stayToLastSkipOne e alternateSkipOne (i cui test cominciano con "optional...")
	 * 
	 * - la buona progettazione della soluzione, riusando più codice possibile e tenendo tutte le 
	 * implementazioni molto succinte
	 * 
	 * Si tolga il commento dal metodo initFactory.
	 * 
	 * Indicazioni di punteggio: 
	 * 
	 * - correttezza della parte obbligatoria: 9 punti
	 * 
	 * - correttezza della parte opzionale: 3 punti (2 metodi ulteriori)
	 * 
	 * - qualità della soluzione: 5 punti (per la buona progettazione della soluzione, come sopra indicato)
	 * 
	 */

	private CirclerFactory factory;
	
	@org.junit.Before
	public void initFactory() {
		this.factory = new CirclerFactoryImpl();
	}
	
	@org.junit.Test
	public void testLeftToRight() {
		var circler = this.factory.leftToRight();
		circler.setSource(List.of(10,20,30)); // iterazione: 10,20,30,10,20,30,10,20,30,...
		
		assertEquals(10, circler.produceOne());
		assertEquals(20, circler.produceOne());
		assertEquals(30, circler.produceOne());
		assertEquals(10, circler.produceOne());
		assertEquals(20, circler.produceOne());
		
		// produco i prossimi 5
		assertEquals(List.of(30,10,20,30,10), circler.produceMany(5));
		
		// nuova sorgente: resetto e ricomincio con la nuova iterazione
		circler.setSource(List.of("1","2","3","4"));
		assertEquals("1", circler.produceOne());
		assertEquals("2", circler.produceOne());
		assertEquals("3", circler.produceOne());
		assertEquals("4", circler.produceOne());
		assertEquals("1", circler.produceOne());
	}
	
	@org.junit.Test
	public void testAlternate() {
		var circler = this.factory.alternate();
		circler.setSource(List.of(10,20,30)); // iterazione: 10,20,30,30,20,10,10,20,30,...
		
		assertEquals(10, circler.produceOne());
		assertEquals(20, circler.produceOne());
		assertEquals(30, circler.produceOne());
		assertEquals(30, circler.produceOne());
		assertEquals(20, circler.produceOne());
		
		assertEquals(List.of(10,10,20,30,30), circler.produceMany(5));
		
		circler.setSource(List.of("1","2","3","4"));
		assertEquals("1", circler.produceOne());
		assertEquals("2", circler.produceOne());
		assertEquals("3", circler.produceOne());
		assertEquals("4", circler.produceOne());
		assertEquals("4", circler.produceOne());
		assertEquals("3", circler.produceOne());
	}
	
	@org.junit.Test
	public void testStayToLast() {
		var circler = this.factory.stayToLast();
		circler.setSource(List.of(10,20,30));
		
		assertEquals(10, circler.produceOne());
		assertEquals(20, circler.produceOne());
		assertEquals(30, circler.produceOne());
		assertEquals(30, circler.produceOne());
		assertEquals(30, circler.produceOne());
		assertEquals(30, circler.produceOne());
		
		circler.setSource(List.of("1","2","3","4"));
		assertEquals("1", circler.produceOne());
		assertEquals(List.of("2","3","4","4","4","4","4","4","4","4"), circler.produceMany(10));
	}
	
	@org.junit.Test
	public void testLeftToRightSkipOne() {
		var circler = this.factory.leftToRightSkipOne();
		
		circler.setSource(List.of(10,20,30,40,50));
		assertEquals(List.of(10,30,50,20,40,10,30,50,20,40), circler.produceMany(10));
		
		circler.setSource(List.of(10,20,30,40));
		assertEquals(List.of(10,30,10,30,10,30,10,30,10,30), circler.produceMany(10));
	}
	
	@org.junit.Test
	public void optionalTestAlternateSkipOne() {
		var circler = this.factory.alternateSkipOne();
		
		circler.setSource(List.of(10,20,30,40,50));
		assertEquals(List.of(10,30,50,40,20,10,30,50,40,20), circler.produceMany(10));
		
		circler.setSource(List.of(10,20,30,40));
		assertEquals(List.of(10,30,40,20,10,30,40,20,10,30), circler.produceMany(10));
	}
	
	@org.junit.Test
	public void optionalTestStayToLastSkipOne() {
		var circler = this.factory.stayToLastSkipOne();
		
		circler.setSource(List.of(10,20,30,40,50));
		assertEquals(List.of(10,30,50,50,50,50,50,50,50,50), circler.produceMany(10));
		
		circler.setSource(List.of(10,20,30,40));
		assertEquals(List.of(10,30,40,40,40,40,40,40,40,40), circler.produceMany(10));
	}
}