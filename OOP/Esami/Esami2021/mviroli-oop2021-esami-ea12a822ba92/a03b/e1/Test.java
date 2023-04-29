package a03b.e1;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Test {

	/*
	 * Implementare l'interfaccia LensFactory come indicato nel metodo
	 * initFactory qui sotto. Realizza una factory per delle Lens, che modellano la
	 * possibilità di accedere ad un certo punto di una struttura dati (anche complessa)
	 * per leggerne (get) o modificarne per copia (set) una sua sottoparte. 
	 * 
	 * Il commento alle interfacce fornite, e i metodi di test qui sotto
	 * costituiscono la necessaria spiegazione del problema.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere
	 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
	 * punteggio: 
	 * 
	 * - implementazione dei cinque metodi nella factory (ossia, nella parte obbligatoria è sufficiente 
	 * implementarne 4 a piacimento) 
	 * 
	 * - la buona progettazione della soluzione, evitando ripetizioni
	 * 
	 * Si tolga il commento dal metodo initFactory.
	 * 
	 * Indicazioni di punteggio: 
	 * 
	 * - correttezza della parte obbligatoria: 10 punti (2+2+3+3 per i metodi di factory necessari) 
	 * 
	 * - correttezza della parte opzionale: 3 punti (metodo ulteriore)
	 * 
	 * - qualità della soluzione: 4 punti (per soluzione che minimizza le ripetizioni e che segue buone prassi di programmazione)
	 * 
	 */

	private LensFactory factory = null;
	
	@org.junit.Before
	public void initFactory() {
		//this.factory = new LensFactoryImpl();
	}
	
	@org.junit.Test
	public void testIndexer() {
		var lens = this.factory.indexer(1);
		// getter e copy-setter sull'elemento 1-esimo (ossia il secondo) di una lista 
		assertEquals(20,lens.get(List.of(10,20,30,40,50)));
		assertEquals(List.of(10,21,30,40,50),lens.set(21,List.of(10,20,30,40,50)));
		
		var lens2 = this.factory.indexer(3);
		// getter e copy-setter sull'elemento 3-esimo (ossia il quarto) di una lista 
		assertEquals(40,lens2.get(List.of(10,20,30,40,50)));
		assertEquals(List.of(10,20,30,41,50),lens2.set(41,List.of(10,20,30,40,50)));
	}
	
	@org.junit.Test
	public void testDoubleIndexer() {
		var lens = this.factory.doubleIndexer(2,1);
		// getter e copy-setter sull'elemento di riga 2 e colonna 1 di una lista di liste (matrice)
		assertEquals(31,lens.get(List.of(List.of(10,11,12), List.of(20,21,22), List.of(30,31,32))));
		assertEquals(
				List.of(List.of(10,11,12), List.of(20,21,22), List.of(30,0,32)),
				lens.set(0,List.of(List.of(10,11,12), List.of(20,21,22), List.of(30,31,32))));
	}
	
	@org.junit.Test
	public void testLeft() {
		var lens = this.factory.left();
		// getter e copy-setter sul primo componente del pair
		assertEquals("a",lens.get(new Pair<>("a","b")));
		assertEquals(new Pair<>("a2","b"),lens.set("a2",new Pair<>("a","b")));
	}
	
	@org.junit.Test
	public void testRight() {
		var lens = this.factory.right();
		// getter e copy-setter sul secondo componente del pair
		assertEquals("b",lens.get(new Pair<>("a","b")));
		assertEquals(new Pair<>("a","b2"),lens.set("b2",new Pair<>("a","b")));
	}
	
	@org.junit.Test
	public void testRightRightAtPos() {
		var lens = this.factory.rightRightAtPos(2);
		// getter e setter che accedono ad una sottoparte "profonda" di una lista di pair di pair
		assertEquals(222,lens.get(List.of(
				new Pair<>("a",new Pair<>(1,2)),
				new Pair<>("b",new Pair<>(11,22)),
				new Pair<>("c",new Pair<>(111,222)),
				new Pair<>("d",new Pair<>(1111,2222)))));
		assertEquals(List.of(
				new Pair<>("a",new Pair<>(1,2)),
				new Pair<>("b",new Pair<>(11,22)),
				new Pair<>("c",new Pair<>(111,0)),
				new Pair<>("d",new Pair<>(1111,2222))),
				lens.set(0,List.of(
						new Pair<>("a",new Pair<>(1,2)),
						new Pair<>("b",new Pair<>(11,22)),
						new Pair<>("c",new Pair<>(111,222)),
						new Pair<>("d",new Pair<>(1111,2222)))));
	}
}
