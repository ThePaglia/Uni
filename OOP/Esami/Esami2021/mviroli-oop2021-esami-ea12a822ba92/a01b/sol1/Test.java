package a01b.sol1;

import static org.junit.Assert.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Test {

	/*
	 * Implementare l'interfaccia EventSequenceProducerHelpers come indicato nel metodo
	 * initFactory qui sotto. Realizza un insieme di funzionalità helper per dei 
	 * EventSequenceProducer, ossia sorgenti di eventi (coppie tempo + contenuto).
	 * 
	 * Il commento alle interfacce fornite, e i metodi di test qui sotto
	 * costituiscono la necessaria spiegazione del problema.
	 * 
	 * Sono considerati invece opzionali ai fini della possibilità di correggere
	 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
	 * punteggio: 
	 * 
	 * - implementazione dei cinque metodi nell'helper (ossia, nella parte obbligatoria è sufficiente 
	 * implementarne 4 a piacimento) 
	 * 
	 * - la buona progettazione della soluzione, in particolare utilizzando codice succinto che evita ripetizioni
	 * 
	 * - evitare di creare strutture dati non necessarie
	 * 
	 * Si tolga il commento dal metodo initFactory.
	 * 
	 * Indicazioni di punteggio: 
	 * 
	 * - correttezza della parte obbligatoria: 10 punti (2.5 per ogni metodo dell'helper)
	 * 
	 * - correttezza della parte opzionale: 3 punti (ultimo metodo) 
	 * 
	 * - qualità della soluzione: 4 punti (3 punti per buon design, 1 punto per no strutture non necessarie)
	 * 
	 */

	private EventSequenceProducerHelpers helpers = null;
	
	private final static List<Pair<Double,String>> EVENTS = List.of(
			new Pair<>(1.1, "uno"),
			new Pair<>(2.2, "due"),
			new Pair<>(3.3, "tre"),
			new Pair<>(4.4, "quattro")
	);

	@org.junit.Before
	public void initFactory() {
		this.helpers = new EventSequenceProducerHelpersImpl();
	}
	
	@org.junit.Test
	public void testFromIterator() {
		final var producer = this.helpers.fromIterator(EVENTS.iterator());
		// produce i 4 elementi di EVENTS, e poi fallisce nel cercare di produrre il quinto
		assertEquals(new Pair<>(1.1, "uno"),producer.getNext());
		assertEquals(new Pair<>(2.2, "due"),producer.getNext());
		assertEquals(new Pair<>(3.3, "tre"),producer.getNext());
		assertEquals(new Pair<>(4.4, "quattro"),producer.getNext());
		assertThrows(NoSuchElementException.class, ()->producer.getNext());
	}
	
	@org.junit.Test
	public void testWindow() {
		var producer = this.helpers.fromIterator(EVENTS.iterator());
		// la finestra [2.0,4.0] produce solo gli eventi "due" e "tre"
		assertEquals(List.of("due", "tre"), this.helpers.window(producer, 2.0, 4.0));
		producer = this.helpers.fromIterator(EVENTS.iterator());
		// la finestra [1.0,4.0] produce solo gli eventi "uno","due" e "tre"
		assertEquals(List.of("uno", "due", "tre"), this.helpers.window(producer, 1.0, 4.0));
		producer = this.helpers.fromIterator(EVENTS.iterator());
		// la finestra [1.5,2.0] non produce nulla
		assertEquals(List.of(), this.helpers.window(producer, 1.5, 2.0));
	}
	
	@org.junit.Test
	public void testAsIterable() {
		final var producer = this.helpers.fromIterator(EVENTS.iterator());
		final var iterable = this.helpers.asEventContentIterable(producer);
		// l'iterable ottenuto genera un iteratore che si comporta producendo solo i 4 eventi
		final var iterator = iterable.iterator();
		assertTrue(iterator.hasNext());
		assertEquals("uno", iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals("due", iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals("tre", iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals("quattro", iterator.next());
		assertFalse(iterator.hasNext());
	}
	
	@org.junit.Test
	public void testNextAt() {
		var producer = this.helpers.fromIterator(EVENTS.iterator());
		// l'evento dopo il tempo 2.0 è (2.2, "due")
		assertEquals(Optional.of(new Pair<>(2.2, "due")), this.helpers.nextAt(producer, 2.0));
		producer = this.helpers.fromIterator(EVENTS.iterator());
		// l'evento dopo il tempo 4.0 è (4.4, "quattro")
		assertEquals(Optional.of(new Pair<>(4.4, "quattro")), this.helpers.nextAt(producer, 4.0));
		producer = this.helpers.fromIterator(EVENTS.iterator());
		// l'evento dopo il tempo 5.0 non c'è
		assertEquals(Optional.empty(), this.helpers.nextAt(producer, 5.0));
	}
	
	@org.junit.Test
	public void optionalTestFilter1() {
		var producer = this.helpers.fromIterator(EVENTS.iterator());
		var out = this.helpers.filter(producer, s -> s.length()==3);
		// un producer di soli tre eventi rimane filtrando quelli con contenuto lungo 3
		assertEquals(new Pair<>(1.1, "uno"),out.getNext());
		assertEquals(new Pair<>(2.2, "due"),out.getNext());
		assertEquals(new Pair<>(3.3, "tre"),out.getNext());
		assertThrows(NoSuchElementException.class, ()->out.getNext());	
	}
	
	@org.junit.Test
	public void optionalTestFilter2() {
		var producer = this.helpers.fromIterator(EVENTS.iterator());
		var out = this.helpers.filter(producer, s -> s.length()==4);
		// un producer vuoto rimane filtrando quelli con contenuto lungo 4
		assertThrows(NoSuchElementException.class, ()->out.getNext());	
	}
}
