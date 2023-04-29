package a03a.sol1;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Test {

	/*
	 * Implementare l'interfaccia DecisionChainFactory come indicato nel metodo
	 * initFactory qui sotto. Realizza una factory per dei DecisionChain, ossia
	 * oggetti che preso un input devono decidere se ritornare subito un certo output, 
	 * oppure richiedere la delegazione della decisone ad un altro DecisionChain (e a quale)
	 * -- esercizio che illustra il pattern chiamato "Chain of Responsibility". 
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

	private DecisionChainFactory factory = null;
	
	@org.junit.Before
	public void initFactory() {
		this.factory = new DecisionChainFactoryImpl();
	}
	
	@org.junit.Test
	public void testOneResult() {
		DecisionChain<Integer,Integer> dt = this.factory.oneResult(10);
		// torna sempre 10, e subito
		assertEquals(Optional.of(10), dt.result(5));
		assertEquals(Optional.of(10), dt.result(-1));
		assertEquals(Optional.of(10), dt.result(11));
		assertEquals(10, dt.finalResult(-2).intValue());
	}
	
	@org.junit.Test
	public void testSimpleTwoWay() {
		DecisionChain<Integer,Integer> dt = this.factory.simpleTwoWay(x -> x > 0, +1, -1);
		// alla fine della catena di decisioni si produce +1 ricevendo un positivo, -1 altrimenti
		assertEquals(1, dt.finalResult(100).intValue());
		assertEquals(-1, dt.finalResult(-20).intValue());
		// ...ma la cosa è sempre decisa passando ad un altro decider, quindi il risultato diretto è "empty"
		assertEquals(Optional.empty(), dt.result(10));
		assertEquals(Optional.empty(), dt.result(-10));
		// ad esempio ricevendo 10 si delega ad un dt2 che mi darà sempre 1, e viceversa su dt3
		var dt2 = dt.next(10);
		assertEquals(Optional.of(1), dt2.result(10));
		var dt3 = dt.next(-10);
		assertEquals(Optional.of(-1), dt3.result(-10));
	}
	
	@org.junit.Test
	public void testEnumerationLike() {
		var map = List.of(new Pair<>("a",1), new Pair<>("b",2), new Pair<>("c",3));
		// alla fine della catena si produce 1 ricevendo "a", 2 ricevendo "b"... e -1 negli altri casi
		DecisionChain<String,Integer> dt = this.factory.enumerationLike(map,-1);
		assertEquals(1, dt.finalResult("a").intValue());
		assertEquals(2, dt.finalResult("b").intValue());
		assertEquals(3, dt.finalResult("c").intValue());
		assertEquals(-1, dt.finalResult("ddd").intValue());
		// ma dt risponde subito per "a", mentre per "b" delega al prossimo decider dt2
		assertEquals(Optional.of(1), dt.result("a"));
		assertEquals(Optional.empty(), dt.result("b"));
		var dt2 = dt.next("b");
		assertEquals(2, dt2.finalResult("b").intValue());
		assertEquals(3, dt2.finalResult("c").intValue());
		assertEquals(-1, dt2.finalResult("ddd").intValue());
		assertEquals(-1, dt2.finalResult("a").intValue());
		assertEquals(Optional.of(3), dt.next("c").next("c").result("c"));
	}
	
	@org.junit.Test
	public void testTwoWay() {
		DecisionChain<String,Integer> dt1 = this.factory.oneResult(0);
		var map = List.of(new Pair<>("a",1), new Pair<>("b",2), new Pair<>("c",3));
		DecisionChain<String,Integer> dt2 = this.factory.enumerationLike(map,-1);
		// se la stringa è vuota, dt delega a dt1, altrimenti a dt2
		DecisionChain<String,Integer> dt = this.factory.twoWay(String::isEmpty, dt1, dt2);
		assertEquals(0, dt.finalResult("").intValue());
		assertEquals(1, dt.finalResult("a").intValue());
		assertEquals(2, dt.finalResult("b").intValue());
		assertEquals(3, dt.finalResult("c").intValue());
		assertEquals(-1, dt.finalResult("ddd").intValue());
		var dt3 = dt.next("");
		// dt3 si comporta come dt1...
		assertEquals(Optional.of(0), dt3.result(""));
	}
	
	@org.junit.Test
	public void testSwitchChain() {
		var chain = List.<Pair<Predicate<Integer>,String>>of(
				new Pair<>(x -> x<10, "<10"), // se x<10 decide e torna "<10", altrimenti delega...
				new Pair<>(x -> x<20, "<20"), // se x<20 decide e torna "<20", altrimenti delega...
				new Pair<>(x -> x<30, "<30"), // ...
				new Pair<>(x -> x<40, "<40"));
		var dt = this.factory.switchChain(chain, "bigger"); // se nessuno ha deciso torna "bigger"
		assertEquals("<10", dt.finalResult(0));
		assertEquals("<20", dt.finalResult(15));
		assertEquals("<30", dt.finalResult(21));
		assertEquals("<40", dt.finalResult(39));
		assertEquals("bigger", dt.finalResult(100));
		assertEquals(Optional.empty(), dt.result(11));
	}

	
}
