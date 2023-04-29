package a06.sol1;

import static org.junit.Assert.*;

import java.util.List;
import java.util.NoSuchElementException;

public class Test {

	/*
	 * Implementare l'interfaccia BTreeFactory come indicato nel metodo
	 * initFactory qui sotto. Realizza una factory per dei BTree, ossia
	 * alberi binari dove i valori sono presenti sono nelle foglie (LEAF).
	 * Ad esempio un albero potrebbe avere una radice e due foglie con valori 1 e 2,
	 * indicato anche brevemente con (1,2), un altro potrebbe avere una radice,
	 * un figlio a sinistra costituito dall'abero (1,2) e a destra uno con la sola 
	 * foglia 3, e lo indicheremmo con ((1,2),3) 
	 * 
	 * Il commento alle interfacce fornite, e i metodi di test qui sotto
	 * costituiscono la necessaria spiegazione del problema.
	 * 
	 * Sono considerati invece opzionali ai fini della possibilità di correggere
	 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
	 * punteggio: 
	 * 
	 * - implementazione dell'ultimo test, chiamato optionalTestXYZ (relativo al
	 * metodo BTreeFactory.nested) 
	 * 
	 * - la buona progettazione della soluzione, in particolare utilizzando codice succinto
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

	private BTreeFactory factory = null;

	@org.junit.Before
	public void initFactory() {
		this.factory = new BTreeFactoryImpl();
	}
	
	@org.junit.Test
	public void testSimple() {
		var left = this.factory.leaf(1);
		var right = this.factory.leaf(2);
		var tree = this.factory.compose(left, right); // l'albero (1,2)
		
		// da qui, semplici getter
		assertTrue(left.isLeaf());
		assertTrue(right.isLeaf());
		assertFalse(tree.isLeaf());
		
		assertEquals(Integer.valueOf(1), left.getLeaf());
		assertEquals(Integer.valueOf(2), right.getLeaf());
		
		assertEquals(this.factory.leaf(1), tree.getLeft());
		assertEquals(this.factory.leaf(2), tree.getRight());
	}
	
	@org.junit.Test
	public void testExceptions() {
		var left = this.factory.leaf(1);
		var right = this.factory.leaf(2);
		var tree = this.factory.compose(left, right); // (1,2)

		// attenzione, una foglia non ha left e right, e viceversa
		assertThrows(NoSuchElementException.class, ()->tree.getLeaf());
		assertThrows(NoSuchElementException.class, ()->left.getLeft());
		assertThrows(NoSuchElementException.class, ()->left.getRight());
		assertThrows(NoSuchElementException.class, ()->right.getLeft());
		assertThrows(NoSuchElementException.class, ()->right.getRight());
	}
	
	@org.junit.Test
	public void testCompute() {
		var left = this.factory.leaf(1);
		var right = this.factory.leaf(2);
		var tree1 = this.factory.compose(left, right); // (1,2)
		var tree = this.factory.compose(tree1, this.factory.leaf(3)); // ((1,2),3)
		
		// si esegue il + per ogni nodo nell'albero, ottenendo come risultato ((1+2)+3)
		assertEquals(Integer.valueOf(3), tree1.compute((l1,l2) -> l1+l2));
		
		// stessa cosa anche - e *
		assertEquals(Integer.valueOf(6), tree.compute((l1,l2) -> l1+l2));
		assertEquals(Integer.valueOf(-4), tree.compute((l1,l2) -> l1-l2));
		assertEquals(Integer.valueOf(6), tree.compute((l1,l2) -> l1*l2));
	}
	
	@org.junit.Test
	public void testMap() {
		var left = this.factory.leaf(1);
		var right = this.factory.leaf(2);
		var tree1 = this.factory.compose(left, right); // +(1,2)
		
		// si incrementa ogni valore nelle foglie
		assertEquals(this.factory.leaf(2), left.map(i ->i+1));
		// si fa il quadrato di ogni valore nelle foglie
		assertEquals(this.factory.compose(this.factory.leaf(1), this.factory.leaf(4)), tree1.map(i->i*i)); 
	}
	
	@org.junit.Test
	public void testSymmetric() {
		var left = this.factory.leaf(1);
		var right = this.factory.leaf(2);
		var tree1 = this.factory.compose(left, right); // (1,2)
		var tree = this.factory.compose(tree1, this.factory.leaf(3)); // ((1,2),3)
		
		// il simmetrico è un albero orizzontalmente speculare a quello in input
		// simmetrico di (1,2) è (2,1)
		assertEquals(this.factory.compose(this.factory.leaf(2), this.factory.leaf(1)), tree1.symmetric());
		// simmetrico di ((1,2),3) è (3,(2,1))
		assertEquals(this.factory.compose(this.factory.leaf(2), this.factory.leaf(1)), tree.symmetric().getRight());
	}
	
	@org.junit.Test
	public void optionalTestNested() {
		// il nested di (1,2,3,4) è un albero che ha quei valori nelle foglie, ordinati, ad esempio (1,(2,(3,4))
		assertEquals(Integer.valueOf(10), this.factory.nested(List.of(1,2,3,4)).compute((a,b)->a+b));
		assertEquals(Integer.valueOf(24), this.factory.nested(List.of(1,2,3,4)).compute((a,b)->a*b));
	}
}
