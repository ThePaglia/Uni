package a01b.sol1;

import static org.junit.Assert.*;
import java.util.*;

public class Test {
	
	/*
	 * Implementare l'interfaccia GridFactory come indicato nel metodo initFactory qui sotto.
	 * Realizza un concetto di factory per griglie rettangolari, dove ogni cella contiene un
	 * valore il cui tipo è quello della type-variable <X> dell'interfaccia Grid.
	 * La factory fornisce una griglia che ha logicamente tutte le celle inizialmente vuote (valore null).
	 * La griglia fornisce metodi per impostare i valori su una riga, una colonna, la diagonale, e tutto il bordo.
	 * Fornisce anche un metodo per iterare tutte le celle, o solo quelle non vuote.
	 * Il commento alle interfacce fornite, e i metodi di test qui sotto costituiscono la necessaria spiegazione del 
	 * problema.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
	 * della totalità del punteggio:
	 * - implementazione dei test chiamati optionalTestXYZ (relativo al metodo Grid.iterator()) 
	 * - la buona progettazione della soluzione, in particolare con minimizzazione di ripetizioni
	 * 
	 * 	Si tolga il commento dal metodo initFactory.
	 * 
	 * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 9 punti
	 * - correttezza della parte opzionale: 4 punti
	 * - qualità della soluzione: 4 punti
	 * 
	 */
	
	private GridFactory gf = null;
	
	@org.junit.Before
	public void initFactory() {
		this.gf = new GridFactoryImpl();
	}
	
	
	@org.junit.Test
	public void testBasic() {
		final Grid<String> g = this.gf.create(2, 3); // due righe [null,null,null] e [null,null,null] 
		assertNull(g.getValue(0, 0));
		assertEquals(2,g.getRows());
		assertEquals(3,g.getColumns());
	}
	
	@org.junit.Test
	public void testRow() {
		final Grid<String> g = this.gf.create(2, 3); // due righe: [null,null,null] e [null,null,null]
		g.setRow(0, "a"); // due righe: [a,a,a] e [null,null,null]
		assertNull(g.getValue(1, 0));
		assertNull(g.getValue(1, 1));
		assertNull(g.getValue(1, 2));
		assertEquals("a",g.getValue(0,0));
		assertEquals("a",g.getValue(0,1));
		assertEquals("a",g.getValue(0,2));
	}
	
	@org.junit.Test
	public void testColumn() {
		final Grid<Integer> g = this.gf.create(2, 3); // due righe: [null,null,null] e [null,null,null]
		g.setColumn(1, 10); // due righe [null,10,null] e [null,10,null]
		assertNull(g.getValue(0, 0));
		assertNull(g.getValue(0, 2));
		assertNull(g.getValue(1, 0));
		assertNull(g.getValue(1, 2));
		assertEquals(10,g.getValue(0,1).intValue());
		assertEquals(10,g.getValue(1,1).intValue());
	}
	
	@org.junit.Test
	public void testDiagonal() { // due righe: [null,null,null] e [null,null,null]
		final Grid<Integer> g = this.gf.create(2, 3);
		g.setDiagonal(100); // due righe: [100,null,null] e [null,100,null]
		assertEquals(100,g.getValue(0, 0).intValue());
		assertNull(g.getValue(0, 1));
		assertNull(g.getValue(0, 2));
		assertNull(g.getValue(1, 0));
		assertEquals(100,g.getValue(1, 1).intValue());
		assertNull(g.getValue(1, 2));
	}
	
	@org.junit.Test
	public void testBorder() {
		final Grid<String> g = this.gf.create(3, 3); // tree righe: [null,null,null], [null,null,null]  e [null,null,null]
		g.setBorder("b"); // tree righe: [b,b,b], [b,null,b]  e [b,b,b]
		assertEquals("b",g.getValue(0, 0));
		assertEquals("b",g.getValue(0, 1));
		assertEquals("b",g.getValue(0, 2));
		assertEquals("b",g.getValue(1, 0));
		assertNull(g.getValue(1, 1));
		assertEquals("b",g.getValue(1, 2));
		assertEquals("b",g.getValue(2, 0));
		assertEquals("b",g.getValue(2, 1));
		assertEquals("b",g.getValue(2, 2));
	}
	
	// da qui gli opzionali
	
	@org.junit.Test
	public void optionalTestIteratorOnlyNonNull() {
		final Grid<String> g = this.gf.create(3, 3);
		g.setColumn(0,"a");
		g.setColumn(2,"b");
		var it = g.iterator(true);
		assertTrue(it.hasNext());
		assertEquals(new Cell<>(0,0,"a"),it.next());
		assertEquals(new Cell<>(0,2,"b"),it.next());
		assertEquals(new Cell<>(1,0,"a"),it.next());
		assertEquals(new Cell<>(1,2,"b"),it.next());
		assertEquals(new Cell<>(2,0,"a"),it.next());
		assertEquals(new Cell<>(2,2,"b"),it.next());
		assertFalse(it.hasNext());
	}
	
	@org.junit.Test
	public void optionalTestIteratorAlsoNull() {
		final Grid<String> g = this.gf.create(3, 3);
		g.setColumn(0,"a");
		g.setColumn(2,"b");
		var it = g.iterator(false);
		assertTrue(it.hasNext());
		assertEquals(new Cell<>(0,0,"a"),it.next());
		assertEquals(new Cell<>(0,1,null),it.next());
		assertEquals(new Cell<>(0,2,"b"),it.next());
		assertEquals(new Cell<>(1,0,"a"),it.next());
		assertEquals(new Cell<>(1,1,null),it.next());
		assertEquals(new Cell<>(1,2,"b"),it.next());
		assertEquals(new Cell<>(2,0,"a"),it.next());
		assertEquals(new Cell<>(2,1,null),it.next());
		assertEquals(new Cell<>(2,2,"b"),it.next());
		assertFalse(it.hasNext());
	}
}

