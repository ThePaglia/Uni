package a01a.e1;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Test {
	
	/*
	 * Implementare l'interfaccia TreeFactory come indicato nel metodo initFactory qui sotto.
	 * Realizza una factory per dei Tree, ossia strutture dati albero immutabile (un nodo ha "n" figli ordinati).
	 * Implementare correttamente (via Eclipse come al solito) i metodi equals e hashcode per questi Tree. 
	 * 
	 * Il commento alle interfacce fornite, e i metodi di test qui sotto costituiscono la necessaria 
	 * spiegazione del problema.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, 
	 * ma concorrono comunque al raggiungimento della totalità del punteggio:
	 * - implementazione dei test chiamati optionalTestXYZ (relativi al toString e a twoChains) 
	 * - la buona progettazione della soluzione, in particolare utilizzando codice succinto
	 * 
	 * Si tolga il commento dal metodo initFactory.
	 * 
	 * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 10 punti
	 * - correttezza della parte opzionale: 4 punti (2 per i toString, 2 per twoChains 
	 * - qualità della soluzione: 3 punti
	 * 
	 */
	
	
	private TreeFactory factory = null;
	
	
	@org.junit.Before
	public void initFactory() {
		// this.factory= new TreeFactoryImpl();
	}
	
	/**
	 * An optional test, showing all the trees we will use in this exam 
	 */
	@org.junit.Test
	public void optionalTestToStrings() {
		Tree<String> tree = this.factory.singleValue("a");
		assertEquals("a[]", tree.toString());
		
		Tree<Integer> tree2 = this.factory.oneLevel(10, List.of(11,12,13));
		assertEquals("10[11[], 12[], 13[]]", tree2.toString());
		
		Tree<Integer> treeA = this.factory.oneLevel(10, List.of(11,12));
		Tree<Integer> treeB = this.factory.singleValue(20);
		Tree<Integer> treeC = this.factory.twoChildren(1, treeA, treeB);
		assertEquals("1[10[11[], 12[]], 20[]]", treeC.toString());
		
		Tree<Integer> tree3 = this.factory.chain(10, List.of(20,30));
		assertEquals("10[20[30[]]]", tree3.toString());
	
	}

	
	@org.junit.Test
	public void testSingleValue() {
		// un albero con solo la radice
		Tree<String> tree = this.factory.singleValue("a");

		assertEquals("a", tree.getRoot());
		assertTrue(tree.getChildren().isEmpty());
		assertEquals(Set.of("a"), tree.getLeafs());
		assertEquals(Set.of("a"), tree.getAll());
		//assertEquals("a[]", tree.toString()); // STESSO TEST IN optionalTestToString
	}
	
	@org.junit.Test
	public void testOneLevel() {
		// un albero con la radice e 3 figli che sono anche foglie
		Tree<Integer> tree = this.factory.oneLevel(10, List.of(11,12,13));

		assertEquals(Integer.valueOf(10), tree.getRoot());
		assertEquals(3, tree.getChildren().size());
		assertEquals(Integer.valueOf(11),tree.getChildren().get(0).getRoot()); // primo figlio
		assertEquals(Integer.valueOf(12),tree.getChildren().get(1).getRoot()); // secondo figlio
		assertEquals(Integer.valueOf(13),tree.getChildren().get(2).getRoot()); // terzo figlio
		assertEquals(Set.of(11,12,13), tree.getLeafs());
		assertEquals(Set.of(10,11,12,13), tree.getAll());
		//assertEquals("10[11[], 12[], 13[]]", tree.toString()); // STESSO TEST IN optionalTestToString
	}
	
	@org.junit.Test
	public void testTwoChildren() {
		Tree<Integer> tree1 = this.factory.oneLevel(10, List.of(11,12));
		Tree<Integer> tree2 = this.factory.singleValue(20);
		// un albero con la radice e i 2 figli tree1 e tree2
		Tree<Integer> tree = this.factory.twoChildren(1, tree1, tree2);
		
		assertEquals(Integer.valueOf(1), tree.getRoot());
		assertEquals(2, tree.getChildren().size());
		assertEquals(Integer.valueOf(10),tree.getChildren().get(0).getRoot()); // radice del primo figlio
		assertEquals(2,tree.getChildren().get(0).getChildren().size()); // quanti figli ha il primo figlio
		assertEquals(Integer.valueOf(20),tree.getChildren().get(1).getRoot()); // radice del secondo figlio
		assertEquals(Set.of(11,12,20), tree.getLeafs());
		assertEquals(Set.of(1,10,11,12,20), tree.getAll());
		//assertEquals("1[10[11[], 12[]], 20[]]", tree.toString()); // STESSO TEST IN optionalTestToString
	}
	
	@org.junit.Test
	public void optionalTestChain() {
		// un albero con radice 10, figlio 20, figlio del figlio 30
		Tree<Integer> tree = this.factory.chain(10, List.of(20,30));
		
		assertEquals(Integer.valueOf(10), tree.getRoot());
		assertEquals(1, tree.getChildren().size());
		assertEquals(Integer.valueOf(20),tree.getChildren().get(0).getRoot()); // radice del figlio
		assertEquals(1,tree.getChildren().get(0).getChildren().size()); // numero di figli del figlio
		assertEquals(Integer.valueOf(30),tree.getChildren().get(0).getChildren().get(0).getRoot()); // radice del figlio del figlio
		assertEquals(0,tree.getChildren().get(0).getChildren().get(0).getChildren().size()); // ...
		assertEquals(Set.of(30), tree.getLeafs());
		assertEquals(Set.of(10,20,30), tree.getAll());
		//assertEquals("10[20[30[]]]", tree.toString()); // STESSO TEST IN optionalTestToString
	}	
	
}

