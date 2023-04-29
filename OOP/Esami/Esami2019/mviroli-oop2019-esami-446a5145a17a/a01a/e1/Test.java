package a01a.e1;

import static org.junit.Assert.*;
import java.util.*;

public class Test {
	
	/*
	 * Implementare l'interfaccia GraphFactory come indicato nel metodo initFactory qui sotto.
	 * Realizza un concetto di factory per grafi orientati, dove ogni nodo è rappresentato da un
	 * valore il cui tipo è la type-variable <X> dell'interfaccia Graph.
	 * La factory fornisce varie funzionalità per creare semplicissimi grafi (a catena, ciclo, stella, 
	 * completamente connesso, o una unione di questi), nella variante "diretta" (solo archi orientati) o "bidirezionale" 
	 * (sempre due archi, uno andata e uno ritorno).
	 * Il commento alle interfacce fornite, e i metodi di test qui sotto costituiscono la necessaria spiegazione del 
	 * problema.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
	 * della totalità del punteggio:
	 * - implementazione dei test chiamati optionalTestXYZ (relativi ai metodi Graph.getEdgesStream() e GraphFactory.combine()) 
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
	
	private GraphFactory gf = null;
	
	@org.junit.Before
	public void initFactory() {
		//this.gf = new GraphFactoryImpl();
	}
	
	
	@org.junit.Test
	public void testDirectedChain() { 
		// a->b, b->c, c->d
		final Graph<String> g = this.gf.createDirectedChain(List.of("a","b","c","d"));
		assertEquals(Set.of("a","b","c","d"),g.getNodes());
		assertEquals(3,g.getEdgesCount());
		assertTrue(g.edgePresent("a", "b"));
		assertTrue(g.edgePresent("b", "c"));
		assertTrue(g.edgePresent("c", "d"));		
	}
	
	@org.junit.Test
	public void testDirectedCircle() {
		// 10->20, 20->30, 30->40, 40->10
		final Graph<Integer> g = this.gf.createDirectedCircle(List.of(10,20,30,40));
		assertEquals(Set.of(10,20,30,40),g.getNodes());
		assertEquals(4,g.getEdgesCount());
		assertTrue(g.edgePresent(10,20));
		assertTrue(g.edgePresent(20,30));
		assertTrue(g.edgePresent(30,40));
		assertTrue(g.edgePresent(40,10));
		
	}
	
	@org.junit.Test
	public void testDirectedStar() {
		// 0->10, 0->20, 0->30
		final Graph<Integer> g = this.gf.createDirectedStar(0,Set.of(10,20,30));
		assertEquals(Set.of(0,10,20,30),g.getNodes());
		assertEquals(3,g.getEdgesCount());
		assertTrue(g.edgePresent(0,10));
		assertTrue(g.edgePresent(0,20));
		assertTrue(g.edgePresent(0,30));
	}
	
	// da qui gli equivalenti bidirezionali..
	
	@org.junit.Test
	public void testBiChain() {
		final Graph<String> g = this.gf.createBidirectionalChain(List.of("a","b","c","d"));
		assertEquals(Set.of("a","b","c","d"),g.getNodes());
		assertEquals(6,g.getEdgesCount());
		assertTrue(g.edgePresent("a", "b"));
		assertTrue(g.edgePresent("b", "c"));
		assertTrue(g.edgePresent("c", "d"));	
		assertTrue(g.edgePresent("b", "a"));
		assertTrue(g.edgePresent("c", "b"));
		assertTrue(g.edgePresent("d", "c"));	
	}
	
	@org.junit.Test
	public void testBiCircle() {
		final Graph<Integer> g = this.gf.createBidirectionalCircle(List.of(10,20,30,40));
		assertEquals(Set.of(10,20,30,40),g.getNodes());
		assertEquals(8,g.getEdgesCount());
		assertTrue(g.edgePresent(10,20));
		assertTrue(g.edgePresent(20,30));
		assertTrue(g.edgePresent(30,40));
		assertTrue(g.edgePresent(40,10));
		assertTrue(g.edgePresent(20,10));
		assertTrue(g.edgePresent(30,20));
		assertTrue(g.edgePresent(40,30));
		assertTrue(g.edgePresent(10,40));
	}
	
	@org.junit.Test
	public void testBiStar() {
		final Graph<Integer> g = this.gf.createBidirectionalStar(0,Set.of(10,20,30));
		assertEquals(Set.of(0,10,20,30),g.getNodes());
		assertEquals(6,g.getEdgesCount());
		assertTrue(g.edgePresent(0,10));
		assertTrue(g.edgePresent(0,20));
		assertTrue(g.edgePresent(0,30));
		assertTrue(g.edgePresent(10,0));
		assertTrue(g.edgePresent(20,0));
		assertTrue(g.edgePresent(30,0));
	}
	
	@org.junit.Test
	public void testFull() {
		// 10->20, 10->30, .. 6 casi..
		final Graph<Integer> g = this.gf.createFull(Set.of(10,20,30));
		assertEquals(Set.of(10,20,30),g.getNodes());
		assertEquals(6,g.getEdgesCount());
		assertTrue(g.edgePresent(10,20));
		assertTrue(g.edgePresent(10,30));
		assertTrue(g.edgePresent(20,30));
		assertTrue(g.edgePresent(20,10));
		assertTrue(g.edgePresent(30,20));
		assertTrue(g.edgePresent(30,10));
	}
	
	// da qui l'opzionale
	
	@org.junit.Test
	public void optionalTestCombine() {
		// semplice unione dei due grafi
		final Graph<Integer> g1 = this.gf.createDirectedStar(0,Set.of(10,20,30));
		final Graph<Integer> g2 = this.gf.createDirectedStar(0,Set.of(10,40));
		final Graph<Integer> g = this.gf.combine(g1, g2);
		assertEquals(Set.of(0,10,20,30,40),g.getNodes());
		assertEquals(4,g.getEdgesCount());
		assertTrue(g.edgePresent(0,10));
		assertTrue(g.edgePresent(0,20));
		assertTrue(g.edgePresent(0,30));
		assertTrue(g.edgePresent(0,40));
	}
}

