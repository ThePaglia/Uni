package ex2014.a04.e1;

import static org.junit.Assert.*;
import java.util.*;

public class Test {
	
	/*
	 * Implementare l'interfaccia TicTacToe data tramite una classe TicTacToeImpl con costruttore senza argomenti, che 
	 * realizza la gestione di una partita del Tris (TicTacToe).
	 * Il commento al codice di TicTacToe, e i due metodi di test qui sotto costituiscono la necessaria spiegazione del 
	 * problema.
	 * 
	 * Si tolga il commento ai test qui sotto..
	 */
	
	/*

	// Test di una partita completa, in cui vince X alla fine
	@org.junit.Test
	public void testOK1() {
		TicTacToe ttt = new TicTacToeImpl();
		ttt.start(TicTacToe.Player.X);	// Comincia a giocare la X
		assertEquals(ttt.getAvailableCells().size(), 9); // Tutte le caselle sono libere
		ttt.move(TicTacToe.Cell.C, TicTacToe.Player.X);  // X segna nella casella centrale
		ttt.move(TicTacToe.Cell.NE, TicTacToe.Player.O);  // O segna nella casella in alto a destra
		ttt.move(TicTacToe.Cell.NW, TicTacToe.Player.X);  // X segna nella casella in alto a sinistra
		ttt.move(TicTacToe.Cell.E, TicTacToe.Player.O);  // O segna nella casella a destra
		assertEquals(ttt.getAvailableCells().size(), 5); // Tutte le caselle sono libere
		assertEquals(ttt.getOccupiedCells().get(TicTacToe.Player.X).size(),2); // 2 caselle hanno la X..
		assertTrue(ttt.getOccupiedCells().get(TicTacToe.Player.X).contains(TicTacToe.Cell.C)); // X è in C
		assertTrue(ttt.getOccupiedCells().get(TicTacToe.Player.X).contains(TicTacToe.Cell.NW)); // X è in NW
		assertEquals(ttt.getOccupiedCells().get(TicTacToe.Player.O).size(),2); // 2 caselle hanno lo O..
		assertFalse(ttt.isOver()); // La partita non è finita
		assertFalse(ttt.isEven()); // La partita non è finita pari
		assertNull(ttt.won()); // Nessuno ha vinto
		assertEquals(ttt.getAvailableCell(2, 2),TicTacToe.Cell.SE); // La cella 2,2 (SW) è libera
		assertNull(ttt.getAvailableCell(1, 1)); // La cella 1,1 (C) non è libera
		// stato attuale partita
		//	X.O
		//  .XO  
		//  ...
		ttt.move(TicTacToe.Cell.S, TicTacToe.Player.X);  
		ttt.move(TicTacToe.Cell.N, TicTacToe.Player.O);  
		ttt.move(TicTacToe.Cell.W, TicTacToe.Player.X);
		ttt.move(TicTacToe.Cell.SW, TicTacToe.Player.O);   
		// stato attuale partita
		//	XOO
		//  XXO  
		//  OX.
		assertFalse(ttt.isEven()); // La partita non è finita pari
		assertFalse(ttt.isOver()); // La partita non è finita
		assertNull(ttt.won()); // Nessuno ha vinto
		ttt.move(TicTacToe.Cell.SE, TicTacToe.Player.X); // Mossa vincente  
		
		assertEquals(ttt.won(),TicTacToe.Player.X); // X ha vinto
		assertFalse(ttt.isEven()); // La partita non è finita pari
		assertTrue(ttt.isOver()); // La partita è finita
		assertEquals(ttt.getAvailableCells().size(), 0); // Nessuna casella libera
	}
	
	// Test di una partita in cui O vince velocemente
	@org.junit.Test
	public void testOK2() {
		TicTacToe ttt = new TicTacToeImpl();
		ttt.start(TicTacToe.Player.O);
		
		ttt.move(TicTacToe.Cell.NE, TicTacToe.Player.O);
		ttt.move(TicTacToe.Cell.SE, TicTacToe.Player.X);
		ttt.move(TicTacToe.Cell.N, TicTacToe.Player.O); 
		ttt.move(TicTacToe.Cell.S, TicTacToe.Player.X);
		ttt.move(TicTacToe.Cell.NW, TicTacToe.Player.O);
		//	OOO
		//  ...  
		//  .XX
			
		assertEquals(ttt.won(),TicTacToe.Player.O); // O ha vinto
		assertFalse(ttt.isEven()); // La partita non è finita pari
		assertTrue(ttt.isOver()); // La partita è finita
	}
	
	// Test di una partita pari
	@org.junit.Test
	public void testOK3() {
		TicTacToe ttt = new TicTacToeImpl();
		ttt.start(TicTacToe.Player.O);
			
		ttt.move(TicTacToe.Cell.NE, TicTacToe.Player.O);
		ttt.move(TicTacToe.Cell.N, TicTacToe.Player.X);
		ttt.move(TicTacToe.Cell.NW, TicTacToe.Player.O); 
		ttt.move(TicTacToe.Cell.C, TicTacToe.Player.X);
		ttt.move(TicTacToe.Cell.W, TicTacToe.Player.O);
		ttt.move(TicTacToe.Cell.E, TicTacToe.Player.X);
		ttt.move(TicTacToe.Cell.S, TicTacToe.Player.O);
		ttt.move(TicTacToe.Cell.SW, TicTacToe.Player.X);
		ttt.move(TicTacToe.Cell.SE, TicTacToe.Player.O);
		//	OXO
		//  OXX  
		//  XOO
				
		assertNull(ttt.won()); // nessun vincitore
		assertTrue(ttt.isEven()); // La partita è finita pari
		assertTrue(ttt.isOver()); // La partita è finita
	}
	
	// Test di eccezioni
	@org.junit.Test
	public void testEXC() {
		TicTacToe ttt = new TicTacToeImpl();
		ttt.start(TicTacToe.Player.O);
		
		try{
			ttt.start(TicTacToe.Player.X);
			fail("Can't restart!");
		} catch (IllegalStateException e){
		} catch (Exception e){
			fail("Wrong exception thrown");
		}
		
		ttt.move(TicTacToe.Cell.NE, TicTacToe.Player.O);
		
		try{
			ttt.move(TicTacToe.Cell.NE, TicTacToe.Player.X);
			fail("Can't move there, it's already O!");
		} catch (IllegalStateException e){
		} catch (Exception e){
			fail("Wrong exception thrown");
		}
		
		ttt.move(TicTacToe.Cell.C, TicTacToe.Player.X);
		
		try{
			ttt.move(TicTacToe.Cell.NW, TicTacToe.Player.X);
			fail("Wrong turn! It's O turn");
		} catch (IllegalStateException e){
		} catch (Exception e){
			fail("Wrong exception thrown");
		}
	}
	
    */
}
