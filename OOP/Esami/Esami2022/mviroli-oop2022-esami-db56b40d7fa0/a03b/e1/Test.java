package a03b.e1;

import static org.junit.Assert.*;
import java.util.*;

/**
 * Si consulti la documentazione della interfaccia LazyTreeFactory, che modella
 * una factory per LazyTree, che a sua volta modella un albero binario "lazy", ossia
 * dove il figlio sinistro e destro di un nodo sono calcolati solo all'occorrenza, se vi si accede,
 * e quindi possono potenzialmente essere infiniti.
 * 
 * Il commento alle interfacce fornite, e i metodi di test qui sotto costituiscono la necessaria 
 * spiegazione del problema.
 * 
 * Sono considerati opzionali ai fini della possibilità di correggere
 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
 * punteggio: 
 * - implementazione del quinto metodo factory (ossia, a scelta se ne realizzino 4,
 *   ma considerando il primo metodo constantInfinite() come obbligatorio)
 * - elementi di qualità come concisione del codice, rimozione di ripetizioni, uso parsimonioso della memoria
 *
 * Si tolga il commento dal metodo init.
 * 
 * Indicazioni di punteggio:
 * - correttezza della parte obbligatoria: 10 punti
 * - correttezza della parte opzionale: 4 punti  
 * - qualità della soluzione: 3 punti
 * - bug di programmazione, o violazione di regole base di programmazione Java, comportano decurtamento del punteggio 
 *   complessivo, anche in caso di bonus
 */

public class Test {

	private LazyTreeFactory factory;

	@org.junit.Before
	public void init() {
		// this.factory = new LazyTreeFactoryImpl();
	}

	@org.junit.Test
	public void testConstant() {
		// un albero infinito di "a" ovunque
		var tree = this.factory.constantInfinite("a");
		assertEquals("a", tree.root());
		assertEquals("a", tree.left().root());
		assertEquals("a", tree.left().left().root());
		assertEquals("a", tree.left().left().left().root());
		assertEquals("a", tree.left().right().root());
		assertEquals("a", tree.left().right().right().root());
		assertEquals("a", tree.right().root());
		assertEquals("a", tree.right().left().root());
		assertEquals("a", tree.right().right().root());
		// andando in profondità a piacere, si trova sempre un elemento
		assertTrue(tree.right().right().right().right().hasRoot());
	}
	
	@org.junit.Test
	public void testFromMap() {
		// un albero con radice "1", figlio sx "2", figlio dx "3"
		// figlio sx del "2" di nuovo "1", figlio dx del "2" di nuovo il "2"
		// da trattare come dei "loop"
		// il "4" non ha figli
		var tree = this.factory.fromMap("1", Map.of(
			"1", new Pair<>("2","3"),
			"2", new Pair<>("1","2"),
			"3", new Pair<>("3","4")));
		assertEquals("1", tree.root());
		assertEquals("2", tree.left().root());
		assertEquals("1", tree.left().left().root());
		assertEquals("2", tree.left().left().left().root());
		assertEquals("2", tree.left().right().root());
		assertEquals("2", tree.left().right().right().root());
		assertEquals("3", tree.right().root());
		assertEquals("3", tree.right().left().root());
		assertEquals("4", tree.right().right().root());
		assertTrue(tree.right().right().hasRoot());
		assertFalse(tree.right().right().left().hasRoot());
		assertFalse(tree.right().right().right().hasRoot());
	}


	@org.junit.Test
	public void testCons() {
		// sottoalbero a sinistra: un albero infinito di "a"
		LazyTree<String> treeL = this.factory.constantInfinite("a");
		// sottoalbero a destra: un albero vuoto
		LazyTree<String> treeR = this.factory.cons(Optional.empty(), () -> null,() -> null);
		// albero con radice "b" e i due sottoalberi di cui sopra
		LazyTree<String> tree = this.factory.cons(Optional.of("b"), ()->treeL, ()->treeR);
		assertEquals("b", tree.root());
		assertEquals("a", tree.left().root());
		assertFalse(tree.right().hasRoot());
		assertEquals("a", tree.left().left().root());
		assertEquals("a", tree.left().right().root());
		assertEquals("a", tree.left().left().left().root());
	}

	@org.junit.Test
	public void testTwoIterations() {
		// un albero infinito con radice 0, e dove ogni nodo con valore x ha 
		// a sinistra x-1 e a destra x+1
		var tree = this.factory.fromTwoIterations(0, x -> x-1, x-> x+1);
		assertEquals(0, tree.root().intValue());
		assertEquals(-1, tree.left().root().intValue());
		assertEquals(-2, tree.left().left().root().intValue());
		assertEquals(-3, tree.left().left().left().root().intValue());
		assertEquals(0, tree.left().right().root().intValue());
		assertEquals(1, tree.left().right().right().root().intValue());
		assertEquals(1, tree.right().root().intValue());
		assertEquals(0, tree.right().left().root().intValue());
		assertEquals(2, tree.right().right().root().intValue());
		assertTrue(tree.right().right().right().right().right().hasRoot());
	}

	@org.junit.Test
	public void testBound() {
		// da un albero infinito di "a", si taglia a profondità 2
		var tree = this.factory.fromTreeWithBound(factory.constantInfinite("a"),2);
		assertEquals("a", tree.root());
		assertEquals("a", tree.left().root());
		assertEquals("a", tree.right().root());
		assertFalse(tree.left().left().hasRoot());
		assertFalse(tree.left().right().hasRoot());
		assertFalse(tree.right().left().hasRoot());
		assertFalse(tree.right().right().hasRoot());
	}

}
