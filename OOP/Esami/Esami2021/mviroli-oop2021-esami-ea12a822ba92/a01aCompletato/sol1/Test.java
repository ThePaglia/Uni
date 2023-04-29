package a01a.sol1;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Test {

	/*
	 * Implementare l'interfaccia AcceptorFactory come indicato nel metodo
	 * initFactory qui sotto. Realizza una factory per degli Acceptor, ossia
	 * oggetti che prendo una sequenza di elementi uno-a-uno, producendo un risultato
	 * se la sequenza è correttamente accettata. 
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
	 * - la buona progettazione della soluzione, utilizzando qualche pattern per evitare ripetizioni
	 * 
	 * - evitare di creare strutture dati non necessarie
	 * 
	 * Si tolga il commento dal metodo initFactory.
	 * 
	 * Indicazioni di punteggio: 
	 * 
	 * - correttezza della parte obbligatoria: 10 punti (2+2+3+3 per i metodi di factory necessari) 
	 * 
	 * - correttezza della parte opzionale: 3 punti (metodo generalised)
	 * 
	 * - qualità della soluzione: 4 punti (3 punti per pattern, 1 punto per no strutture non necessarie)
	 * 
	 */

	private AcceptorFactory factory = null;
	
	
	@org.junit.Before
	public void initFactory() {
		this.factory = new AcceptorFactoryAdvancedImpl();
	}
	
	@org.junit.Test
	public void testCountEmptyString() {
		var acceptor = this.factory.countEmptyStringsOnAnySequence();
		assertTrue(acceptor.accept("aa"));
		assertTrue(acceptor.accept(""));
		assertTrue(acceptor.accept("bbb"));
		assertTrue(acceptor.accept(""));
		// Accetta la sequenza "aa","","bbb","" e quindi ritorna 2
		assertEquals(Optional.of(2), acceptor.end());

		acceptor = this.factory.countEmptyStringsOnAnySequence();
		// Accetta la sequenza vuota e quindi ritorna 0
		assertEquals(Optional.of(0), acceptor.end());
	}
	
	@org.junit.Test
	public void testSumElementsInTriples() {
		var acceptor = this.factory.sumElementsOnlyInTriples();
		assertTrue(acceptor.accept(10));
		assertTrue(acceptor.accept(20));
		assertTrue(acceptor.accept(30));
		// Accetta la sequenza 10,20,30 e quindi ritorna 60
		assertEquals(Optional.of(60), acceptor.end());
		
		acceptor = this.factory.sumElementsOnlyInTriples();
		assertTrue(acceptor.accept(10));
		assertTrue(acceptor.accept(20));
		// Non accetta la sequenza 10,20
		assertEquals(Optional.empty(), acceptor.end());
		
		acceptor = this.factory.sumElementsOnlyInTriples();
		assertTrue(acceptor.accept(10));
		assertTrue(acceptor.accept(20));
		assertTrue(acceptor.accept(30));
		assertFalse(acceptor.accept(40));
		// Non accetta la sequenza 10,20,30,40
		assertEquals(Optional.empty(), acceptor.end());
	}
	
	@org.junit.Test
	public void testShowAnIncreasingSequence() {
		var acceptor = this.factory.showAsStringOnlyOnIncreasingSequences();
		assertTrue(acceptor.accept(10));
		assertTrue(acceptor.accept(15));
		assertTrue(acceptor.accept(20));
		// Accetta la sequenza 10,15,20 e quindi ritorna la stringa qui sotto
		assertEquals(Optional.of("10:15:20"), acceptor.end());

		acceptor = this.factory.showAsStringOnlyOnIncreasingSequences();
		assertTrue(acceptor.accept(10));
		assertFalse(acceptor.accept(8)); // non accettato
		assertFalse(acceptor.accept(9)); // non accettato
		// Non accetta la sequenza 10,9,8
		assertEquals(Optional.empty(), acceptor.end());
	}
	
	@org.junit.Test
	public void testBoth1() {
		var acceptor1 = this.factory.showAsStringOnlyOnIncreasingSequences();
		var acceptor2 = this.factory.sumElementsOnlyInTriples();
		var acceptor = this.factory.acceptBoth(acceptor1, acceptor2);
		assertTrue(acceptor.accept(10));
		assertTrue(acceptor.accept(15));
		assertTrue(acceptor.accept(20));
		// Accetta la sequenza 10,15,20 e quindi ritorna la coppia dei due risultati
		assertEquals(Optional.of(new Pair<>("10:15:20",45)), acceptor.end());	
	}
	
	@org.junit.Test
	public void testBoth2() {
		var acceptor1 = this.factory.showAsStringOnlyOnIncreasingSequences();
		var acceptor2 = this.factory.sumElementsOnlyInTriples();
		var acceptor = this.factory.acceptBoth(acceptor1, acceptor2);
		assertTrue(acceptor.accept(10));
		assertFalse(acceptor.accept(8)); // non accetato da acceptor1
		assertFalse(acceptor.accept(9));
		// Non accetta la sequenza 10,8,9
		assertEquals(Optional.empty(), acceptor.end());	
	}
	
	@org.junit.Test
	public void testBoth3() {
		var acceptor1 = this.factory.showAsStringOnlyOnIncreasingSequences();
		var acceptor2 = this.factory.sumElementsOnlyInTriples();
		var acceptor = this.factory.acceptBoth(acceptor1, acceptor2);
		assertTrue(acceptor.accept(10));
		assertTrue(acceptor.accept(12));
		// Non accetta la sequenza 10,12, perché non al accetta acceptor2
		assertEquals(Optional.empty(), acceptor.end());	
	}

	@org.junit.Test
	public void testGeneralised1() {
		// aggiorna lo stato incrementando 1 su la lunghezza della stringa è 0 
		BiFunction<String,Integer,Optional<Integer>> stateFunction =
				(s,i) -> Optional.of(i + (s.length()==0 ? 1 : 0));
		// dà in uscita lo stato accumulatop 
		Function<Integer, Optional<Integer>> outputFunction = s -> Optional.of(s);
		// Questo acceptor è lo stesso della testCountEmptyString
		var acceptor = this.factory.generalised(0,stateFunction,outputFunction);
		assertTrue(acceptor.accept("aa"));
		assertTrue(acceptor.accept(""));
		assertTrue(acceptor.accept("bbb"));
		assertTrue(acceptor.accept(""));
		assertEquals(Optional.of(2), acceptor.end());
		acceptor = this.factory.generalised(0,stateFunction,outputFunction);
		assertEquals(Optional.of(0), acceptor.end());
	}
	
	@org.junit.Test
	public void testGeneralised2() {
		// aggiorna lo stato aggiungengo ad una lista interna, se non va oltre la dimensione 3 
		BiFunction<Integer,List<Integer>,Optional<List<Integer>>> stateFunction =
				(i,l) -> { l.add(i); return Optional.of(l).filter(ll -> ll.size()<=3);};
		// dà in uscita la lista se lunga 3 
		Function<List<Integer>, Optional<List<Integer>>> outputFunction = l -> Optional.of(l).filter(ll -> ll.size()==3);
		// Questo acceptor è simile a quello della testSumElementsInTriples, ma torna la lista
		var acceptor = this.factory.generalised(new ArrayList<Integer>(),stateFunction,outputFunction);
		assertTrue(acceptor.accept(10));
		assertTrue(acceptor.accept(20));
		assertTrue(acceptor.accept(30));
		assertEquals(Optional.of(List.of(10,20,30)), acceptor.end());
		acceptor = this.factory.generalised(new ArrayList<Integer>(),stateFunction,outputFunction);
		assertTrue(acceptor.accept(10));
		assertTrue(acceptor.accept(20));
		assertEquals(Optional.empty(), acceptor.end());
		acceptor = this.factory.generalised(new ArrayList<Integer>(),stateFunction,outputFunction);
		assertTrue(acceptor.accept(10));
		assertTrue(acceptor.accept(20));
		assertTrue(acceptor.accept(30));
		assertFalse(acceptor.accept(40));
		assertEquals(Optional.empty(), acceptor.end());
	}
}
