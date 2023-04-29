package a01c.e1;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Test {

	/*
	 * Implementare l'interfaccia EventHistoryFactory come indicato nel metodo
	 * initFactory qui sotto. Realizza un insieme di factory per dei 
	 * EventHistory, ossia serie di eventi (coppie tempo + contenuto).
	 * 
	 * Il commento alle interfacce fornite, e i metodi di test qui sotto
	 * costituiscono la necessaria spiegazione del problema.
	 * 
	 * Sono considerati invece opzionali ai fini della possibilità di correggere
	 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
	 * punteggio: 
	 * 
	 * - implementazione dei cinque metodi nella factory (ossia, nella parte obbligatoria è sufficiente 
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
	 * - correttezza della parte obbligatoria: 10 punti (2.5+2.5+2.5+2.5)
	 * 
	 * - correttezza della parte opzionale: 3 punti 
	 * 
	 * - qualità della soluzione: 4 punti (3 punti per buon design, 1 punto per no strutture non necessarie)
	 * 
	 */

	
	private static final double TOLERANCE = 0.01;

	private EventHistoryFactory factory = null;
	
	@org.junit.Before
	public void initFactory() {
//		this.factory = new EventHistoryFactoryImpl();
	}
	
	@org.junit.Test
	public void testFromMap() {
		var h = this.factory.fromMap(Map.of(1.0,"uno", 2.0, "due", 3.0, "tre"));
		assertEquals(1.0, h.getTimeOfEvent(), TOLERANCE);
		assertEquals("uno", h.getEventContent());
		assertTrue(h.moveToNextEvent());
		assertEquals(2.0, h.getTimeOfEvent(), TOLERANCE);
		assertEquals("due", h.getEventContent());
		assertTrue(h.moveToNextEvent());
		assertEquals(3.0, h.getTimeOfEvent(), TOLERANCE);
		assertEquals("tre", h.getEventContent());
		assertFalse(h.moveToNextEvent());
	}
	
	@org.junit.Test
	public void testFromIterators() {
		var i1 = Stream.iterate(1.0, x -> x + 1.0).iterator();
		var i2 = Stream.of("uno", "due", "tre").iterator();
		var h = this.factory.fromIterators(i1,i2);
		assertEquals(1.0, h.getTimeOfEvent(), TOLERANCE);
		assertEquals("uno", h.getEventContent());
		assertTrue(h.moveToNextEvent());
		assertEquals(2.0, h.getTimeOfEvent(), TOLERANCE);
		assertEquals("due", h.getEventContent());
		assertTrue(h.moveToNextEvent());
		assertEquals(3.0, h.getTimeOfEvent(), TOLERANCE);
		assertEquals("tre", h.getEventContent());
		assertFalse(h.moveToNextEvent());
	}
	
	@org.junit.Test
	public void testFromListAndDelta() {
		var i = List.of(100, 200, 300, 400);
		var h = this.factory.fromListAndDelta(i,1.0,0.1);
		assertEquals(1.0, h.getTimeOfEvent(), TOLERANCE);
		assertEquals(100, h.getEventContent().intValue());
		assertTrue(h.moveToNextEvent());
		assertEquals(1.1, h.getTimeOfEvent(), TOLERANCE);
		assertEquals(200, h.getEventContent().intValue());
		assertTrue(h.moveToNextEvent());
		assertEquals(1.2, h.getTimeOfEvent(), TOLERANCE);
		assertEquals(300, h.getEventContent().intValue());
		assertTrue(h.moveToNextEvent());
		assertEquals(1.3, h.getTimeOfEvent(), TOLERANCE);
		assertEquals(400, h.getEventContent().intValue());
		assertFalse(h.moveToNextEvent());
	}
	
	@org.junit.Test
	public void testFromRandomTimesAndSupplier() {
		Random random = new Random();
		Supplier<Boolean> supplier = () -> random.nextBoolean();
		var h = this.factory.fromRandomTimesAndSupplier(supplier,3);
		double t1 = h.getTimeOfEvent();
		boolean b1 = h.getEventContent();
		h.moveToNextEvent();
		double t2 = h.getTimeOfEvent();
		boolean b2 = h.getEventContent();
		h.moveToNextEvent();
		double t3 = h.getTimeOfEvent();
		boolean b3 = h.getEventContent();
		assertFalse(h.moveToNextEvent());
		System.out.println(t1+" "+b1);
		System.out.println(t2+" "+b2);
		System.out.println(t3+" "+b3);
		assertTrue(t1 <= 1.0);
		assertTrue(t2 <= t1 + 1.0);
		assertTrue(t3 <= t2 + 1.0);
	}
	
	@org.junit.Test
	public void testFromFile() throws IOException {
		var h = this.factory.fromFile("file.txt");
		assertEquals(15.5, h.getTimeOfEvent(), TOLERANCE);
		assertEquals("aaa", h.getEventContent());
		assertTrue(h.moveToNextEvent());
		assertEquals(16.6, h.getTimeOfEvent(), TOLERANCE);
		assertEquals("bbb", h.getEventContent());
		assertTrue(h.moveToNextEvent());
		assertEquals(17.7, h.getTimeOfEvent(), TOLERANCE);
		assertEquals("ccc", h.getEventContent());
		assertFalse(h.moveToNextEvent());
	}
}
