package a04.sol1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.List;

public class Test {

	/*
	 * Implementare l'interfaccia FunctionalStreamFactory come indicato nel metodo
	 * initFactory qui sotto. Realizza una factory per dei FunctionalStream<E>, ossia
	 * flussi infiniti di elementi di tipo E.
	 * 
	 * Il commento alle interfacce fornite, e i metodi di test qui sotto
	 * costituiscono la necessaria spiegazione del problema.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere
	 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
	 * punteggio: 
	 * 
	 * - implementazione dei test chiamati optionalTestXYZ (relativi al
	 * metodo FunctionalStream.toIterator) 
	 * 
	 * - la buona progettazione della soluzione, in particolare utilizzando codice succinto e
	 * che eviti ripetizioni
	 * 
	 * Si tolga il commento dal metodo initFactory.
	 * 
	 * Indicazioni di punteggio: 
	 * 
	 * - correttezza della parte obbligatoria: 10 punti 
	 * 
	 * - correttezza della parte opzionale: 4 punti 
	 * 
	 * - qualità della soluzione: 3 punti
	 * 
	 */

	private FunctionalStreamFactory factory = null;

	@org.junit.Before
	public void initFactory() {
		this.factory = new FunctionalStreamFactoryImpl();
	}


	@org.junit.Test
	public void testIterate() {
		var str = this.factory.iterate("",s -> s+"a"); // {"","a","aa","aaa",...}
		var res = str.next();
		assertEquals("", res.getElement());
		res = res.getStream().next();
		assertEquals("a", res.getElement());
		res = res.getStream().next();
		assertEquals("aa", res.getElement());
		var list = res.getStream().toList(3); // lista dei prossimi 3 elementi a partire da qui
		assertEquals(List.of("aaa","aaaa","aaaaa"), list);
	}
	
	@org.junit.Test
	public void testMap() {
		var str0 = this.factory.iterate(0, s -> s+2); // {0,2,4,6,8,...}
		var str = this.factory.map(str0, i -> ""+i+i); // {"00","22","44","66","88",...}
		var res = str.next();
		assertEquals("00", res.getElement());
		res = res.getStream().next();
		assertEquals("22", res.getElement());
		res = res.getStream().next();
		assertEquals("44", res.getElement());
		var list = res.getStream().toList(5);
		assertEquals(List.of("66","88","1010","1212","1414"), list);
	}
	
	@org.junit.Test
	public void testListRepetitive() {
		var str = this.factory.fromListRepetitive(List.of("a","b")); // {"a","b","a","b","a",...}
		var res = str.next();
		assertEquals("a", res.getElement());
		res = res.getStream().next();
		assertEquals("b", res.getElement());
		res = res.getStream().next();
		assertEquals("a", res.getElement());
		var list = res.getStream().toList(3);
		assertEquals(List.of("b","a","b"), list);
	}
	
	@org.junit.Test
	public void optionalTestIterator() {
		var str = this.factory.iterate("",s -> s+"a");
		var iterator = str.toIterator();
		assertEquals("", iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals("a", iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals("aa", iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals("aaa", iterator.next());
		assertTrue(iterator.hasNext());
	}
	
	
}
