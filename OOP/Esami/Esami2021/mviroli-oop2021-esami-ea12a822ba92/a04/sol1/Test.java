package a04.sol1;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;

public class Test {

	/*
	 * Implementare l'interfaccia EitherFactory come indicato nel metodo initFactory qui sotto. 
	 * Realizza una factory per dei Either<A,B> ("A oppure B"), ossia delle varianti di Optional dove un valore
	 * (di tipo B) può essere presente (caso di "successo"), ma se non c'è (caso di "fallimento")
	 * allora è presente un valore che rappresenta il motivo del fallimento (di tipo A). 
	 * Quindi dentro un Either o è presente il valore di successo, o è presente quello del fallimento 
	 * (mai entrambi, e almeno uno dei due ci deve essere).
	 * 
	 * Il commento alle interfacce fornite, e i metodi di test qui sotto
	 * costituiscono la necessaria spiegazione del problema.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere
	 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
	 * punteggio: 
	 * 
	 * - uno dei tre metodi fold/flatMap/filterOrElse di Either, a scelta
	 * (ossia, nella parte obbligatoria è sufficiente implementarne 2 di questi 3, il terzo è quindi opzionale) 
	 * 
	 * - la buona progettazione della soluzione, riusando più codice possibile e tentendo tutte le implementazioni
	 * molto succinte
	 * 
	 * Si tolga il commento dal metodo initFactory.
	 * 
	 * Indicazioni di punteggio: 
	 * 
	 * - correttezza della parte obbligatoria: 10 punti
	 * 
	 * - correttezza della parte opzionale: 2 punti (metodo ulteriore)
	 * 
	 * - qualità della soluzione: 5 punti (per la buona progettazione della soluzione, come sopra indicato)
	 * 
	 */



	private EitherFactory factory;
	
	@org.junit.Before
	public void initFactory() {
		this.factory = new EitherFactoryImpl();
	}
	
	@org.junit.Test
	public void testFailure() {
		// un failure non è un successo
		Either<String, Integer> e = this.factory.failure("error");
		assertTrue(e.isFailure());
		assertFalse(e.isSuccess());
		assertEquals(Optional.of("error"), e.getFailure());
		assertEquals(Optional.empty(), e.getSuccess());
	}
	
	@org.junit.Test
	public void testSuccess() {
		// un successo non è un failure
		Either<String, Integer> e = this.factory.success(100);
		assertFalse(e.isFailure());
		assertTrue(e.isSuccess());
		assertEquals(Optional.of(100), e.getSuccess());
		assertEquals(Optional.empty(), e.getFailure());
	}
	
	@org.junit.Test
	public void testException() {
		// il risultato di 0/0 è un failure, contenente l'eccezione lanciata, ossia ArithmeticException
		Either<Exception, Integer> e = this.factory.of(() -> 0/0);
		assertTrue(e.isFailure());
		assertEquals(ArithmeticException.class, e.getFailure().get().getClass());
	}
	
	@org.junit.Test
	public void testNoException() {
		// il risultato di 5/5 è un successo
		Either<Exception, Integer> e = this.factory.of(() -> 5/5);
		assertTrue(e.isSuccess());
		assertEquals(Optional.of(1), e.getSuccess());
	}
	
	@org.junit.Test
	public void testOrElse() {
		// otteniamo il valore di successo, o un default -1 in caso di fallimento
		assertEquals(Integer.valueOf(-1), this.factory.of(() -> 0/0).orElse(-1));
		assertEquals(Integer.valueOf(10), this.factory.of(() -> 10).orElse(-1));
	}
	
	@org.junit.Test
	public void testMap() {
		// ottenuto un risultato, aggiungiamo 1, ma funziona solo se era un successo
		assertEquals(Optional.empty(), this.factory.of(() -> 0/0).map(x -> x+1).getSuccess());
		assertEquals(Optional.of(11), this.factory.of(() -> 10).map(x -> x+1).getSuccess());
	}
	
	@org.junit.Test
	public void testFlatMap() {
		// ottenuto un risultato x, convertiamo in 10/x, ma funziona solo se era un successo e se 10/x si può fare...
		assertTrue(this.factory.of(() -> 0/0).flatMap(x -> this.factory.of(() -> 10/x)).isFailure());
		assertTrue(this.factory.of(() -> 0).flatMap(x -> this.factory.of(() -> 10/x)).isFailure());
		assertEquals(Optional.of(5), this.factory.of(() -> 2).flatMap(x -> this.factory.of(() -> 10/x)).getSuccess());
	}
	
	@org.junit.Test
	public void testFilter() {
		// ottenuto un risultato x, filtro solo se positivo, altrimenti mi dà fallimento
		assertEquals(Optional.of("negative!"), this.factory.of(() -> 0/0).filterOrElse(x -> x>0, "negative!").getFailure());
		assertEquals(Optional.of("negative!"), this.factory.of(() -> -5).filterOrElse(x -> x>0, "negative!").getFailure());
		assertEquals(Optional.of(5), this.factory.of(() -> 5).filterOrElse(x -> x>0, "negative!").getSuccess());
	}
	
	@org.junit.Test
	public void testFold() {
		// applica una funzione in caso di successo e una in caso di fallimento: qualcosa torna quindi sempre...
		Either<String, Integer> e1 = this.factory.failure("error");
		Either<String, Integer> e2 = this.factory.success(10);
		assertEquals("error!", e1.fold(s -> s + "!", i -> String.valueOf(i)));
		assertEquals("10", e2.fold(s -> s + "!", i -> String.valueOf(i)));
	}
	
	@org.junit.Test
	public void testTraverseOK() {
		// applica il /2 a una lista di interi pari: il tutto in questo caso è un successo!
		var list = List.of(10, 20, 30, 42);
		var out = this.factory.traverse(list, i -> this.factory.of(()->i).filterOrElse(j -> j%2 == 0, "dispari").map(j -> j/2));
		assertEquals(List.of(5, 10, 15, 21), out.getSuccess().get());
	}
	
	@org.junit.Test
	public void testTraverseNO() {
		// applica il /2 a una lista di interi pari: il tutto in questo caso è un fallimento!
		var list = List.of(10, 20, 31, 42);
		var out = this.factory.traverse(list, i -> this.factory.of(()->i).filterOrElse(j -> j%2 == 0, "dispari").map(j -> j/2));
		assertEquals("dispari", out.getFailure().get());
	}
}
