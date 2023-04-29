package ex2014.a01.e1;

import static org.junit.Assert.*;
import java.util.*;

public class Test {
	
	/*
	 * Implementare l'interfaccia FibonacciAcceptor data tramite una classe FibonacciAcceptorImpl con costruttore senza argomenti.
	 * Il commento al codice di FibonacciAcceptor, e i due metodi di test qui sotto costituiscono la necessaria spiegazione del 
	 * problema.
	 * Si tolga il commento ai test..
	 */

	/*
	@org.junit.Test
	public void testOK() {
		// Creo l'oggetto
		FibonacciAcceptor fib = new FibonacciAcceptorImpl();
		// eseguo il primo reset, creando la sequenza dal nome "Standard"
		assertEquals(fib.getAllSequences().size(),0);
		fib.reset("Standard");
		assertEquals(fib.getAllSequences().size(),1);
		// inserisco la sequenza di Fibonacci da 1,1, fino a 13..
		assertTrue(fib.consumeNext(1));
		assertTrue(fib.consumeNext(1));
		assertTrue(fib.consumeNext(2));
		assertTrue(fib.consumeNext(3));
		assertTrue(fib.consumeNext(5));
		assertTrue(fib.consumeNext(8));
		// testo l'attuale sequenza
		assertEquals(fib.getCurrentSequence(),Arrays.asList(1l,1l,2l,3l,5l,8l));
		assertTrue(fib.consumeNext(13));
		// testo l'attuale sequenza, in due modi
		assertEquals(fib.getCurrentSequence(),Arrays.asList(1l,1l,2l,3l,5l,8l,13l));
		assertEquals(fib.getAllSequences().get("Standard"),Arrays.asList(1l,1l,2l,3l,5l,8l,13l));
		// ripeto con una nuova sequenza, questa volta da 10,11
		fib.reset("From 10 and 11");
		assertTrue(fib.consumeNext(10));
		assertTrue(fib.consumeNext(11));
		assertTrue(fib.consumeNext(21));
		assertTrue(fib.consumeNext(32));
		assertEquals(fib.getCurrentSequence(),Arrays.asList(10l,11l,21l,32l));
		assertEquals(fib.getAllSequences().get("From 10 and 11"),Arrays.asList(10l,11l,21l,32l));
		// ripeto con una nuova sequenza, questa volta ancora da 1,1
		fib.reset("Another Standard");
		assertTrue(fib.consumeNext(1));
		assertTrue(fib.consumeNext(1));
		assertTrue(fib.consumeNext(2));
		// un numero non corretto è intercettato e ignorato
		assertFalse(fib.consumeNext(100));
		assertTrue(fib.consumeNext(3));
		assertTrue(fib.consumeNext(5));
		assertEquals(fib.getCurrentSequence(),Arrays.asList(1l,1l,2l,3l,5l));
		assertEquals(fib.getAllSequences().get("Another Standard"),Arrays.asList(1l,1l,2l,3l,5l));
		// faccio un po' di letture sulle sequenze attuali
		assertEquals(fib.getAllSequences().size(),3);
		assertTrue(fib.getAllSequences().containsKey("Standard"));
		assertEquals(fib.getAllSequences().get("Standard"),Arrays.asList(1l,1l,2l,3l,5l,8l,13l));
	}
	
	@org.junit.Test
	public void testExceptions() {
		// Test di eccezioni autoesplicativi
		FibonacciAcceptor fib = new FibonacciAcceptorImpl();
		try{
			fib.consumeNext(1);
			fail("Should reset first!");
		} catch (IllegalStateException e){
		} catch (Exception e){
			fail("Wrong exception thrown");
		}
		try{
			fib.getCurrentSequence();
			fail("Should reset first!");
		} catch (IllegalStateException e){
		} catch (Exception e){
			fail("Wrong exception thrown");
		}
		fib.reset("a");
		try{
			fib.reset("a");
			fail("Cannot create a sequence with existing name!");
		} catch (IllegalArgumentException e){
		} catch (Exception e){
			fail("Wrong exception thrown");
		}
	}
	*/
}
