package ex2014.a05.e2;

import static org.junit.Assert.*;

import java.util.*;

public class Test {

	/*
	 * Realizzare l'interfaccia Roulette data attraverso una classe RouletteImpl con costruttore senza argomenti.
	 * Il commento di Roulette e i test qui sotto sono sufficienti per la comprensione dell'esercizio.
	 * Perché l'esercizio sia valutabile positivamente è necessario che passi testOK1 
	 * 
	 * Si tolga il commento ai test qui sotto..
	 */

	/*
	// Test di un "tiro"
	@org.junit.Test
	public void testOK1() {
		Roulette roulette = new RouletteImpl();
		roulette.waitBets();  // Puntate aperte
		assertEquals(roulette.getTotalBets(), 0);  	// Nessuna puntata finora
		roulette.bet("Mirko", 10, 1000);		   	// Mirko punta 1000 euro sul 10
		roulette.bet("Mirko", 36, 500);				// Mirko punta 500 euro sul 36
		roulette.bet("Mirko", 0, 100);				// Mirko punta 100 euro sullo 0
		roulette.bet("Carlo", 10, 400);				// Carlo punta 400 euro sul 10
		assertEquals(roulette.getTotalBets(), 2000);	// Puntate per 2000 euro sul tavolo
		roulette.debugDrawing(10);					// Esce il 10
		Map<String, Integer> map = roulette.getWins();
		System.out.println(map);
		assertEquals(map.size(), 2);				// 2 vincitori
		assertEquals(map.get("Mirko").intValue(), 1000 * 36);	// Mirko vince 1000*36
		assertEquals(map.get("Carlo").intValue(), 400 * 36);	// Carlo vince 1000*36
	}

	// Test sull'uscita di tutti i possibili numeri
	@org.junit.Test
	public void testOK2() {
		Roulette roulette = new RouletteImpl();
		List<Integer> l = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {	// 1000 giocate
			roulette.waitBets();		// Puntate aperte
			assertEquals(roulette.getTotalBets(), 0);
			int number = roulette.drawWinningNumber();	// Esce "number"
			assertTrue(number >= 0);	//..sta nei limiti
			assertTrue(number < 37);
			l.add(number);	// lo aggiungo a l
		}
		assertTrue(l.contains(0));	// lo 0 è uscito qualche volta
		assertTrue(l.contains(36));	// il 36 è uscito qualche volta
	}

	// Test sulla corretta gestione di più tiri
	@org.junit.Test
	public void testOK3() {
		// Stessa giocata di testOK1
		Roulette roulette = new RouletteImpl();
		roulette.waitBets();
		assertEquals(roulette.getTotalBets(), 0);
		roulette.bet("Mirko", 10, 1000);
		roulette.bet("Mirko", 36, 500);
		roulette.bet("Mirko", 0, 100);
		roulette.bet("Carlo", 10, 400);
		assertEquals(roulette.getTotalBets(), 2000);
		roulette.debugDrawing(10);
		Map<String, Integer> map = roulette.getWins();
		System.out.println(map);
		assertEquals(map.size(), 2);
		assertEquals(map.get("Mirko").intValue(), 1000 * 36);
		assertEquals(map.get("Carlo").intValue(), 400 * 36);

		// Faccio una nuova giocata'
		roulette.waitBets();
		assertEquals(roulette.getTotalBets(), 0);
		roulette.bet("Mirko", 36, 500);
		roulette.bet("Mirko", 0, 100);
		assertEquals(roulette.getTotalBets(), 600);
		roulette.debugDrawing(0);
		map = roulette.getWins();
		System.out.println(map);
		assertEquals(map.size(), 1);
		assertEquals(map.get("Mirko").intValue(), 100 * 36);
	}

	// Test sulla corretta gestione delle eccezioni
	@org.junit.Test
	public void testFAIL() {
		Roulette roulette = new RouletteImpl();
		roulette.waitBets();
		roulette.drawWinningNumber();
		try{
			roulette.bet("Mirko", 10, 1000);
			fail("Can't bet after drawing");
		} catch (IllegalStateException e){
		} catch (Exception e){
			fail("Wrong exception thrown");
		}
		roulette.waitBets();
		roulette.drawWinningNumber();
		try{
			roulette.drawWinningNumber();
			fail("Can't draw twice without allowing new bets");
		} catch (IllegalStateException e){
		} catch (Exception e){
			fail("Wrong exception thrown");
		}
	}
	*/
}
