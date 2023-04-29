package ex2014.a05.e2;

import java.util.*;

/*
 * Questa interfaccia definisce il contratto di un oggetto che gestisce una Roulette al Casinò
 */

public interface Roulette {
	
	// Costante che definisce il fattore di vincita: se punto su un numero e questo esce, vinco 36 volte la posta
	final static int SINGLE_BET_WIN_FACTOR = 36; 
	
	
	/**
	 * Parte una nuova giocata, si possono fare le puntate
	 */
	void waitBets();
	
	/**
	 * 
	 * Un giocatore fà una puntata, mettendo fish sul tavolo
	 * 
	 * @param playerName, nome del giocatore
	 * @param betNumber, numero puntato
	 * @param quantity, somma puntata
	 * 
	 * @throws IllegalStateException se non è partita una nuova giocata tramite waitBets
	 */
	void bet(String playerName, int betNumber, int quantity);
	
	/**
	 * Il croupier lancia la pallina ed esce un risultato 
	 * 
	 * @return il numero uscito, generato a caso, compreso fra 0 e 36 inclusi
	 * 
	 * @throws IllegalStateException se non è partita una nuova giocata tramite waitBets
	 */
	int drawWinningNumber();
	
	/**
	 * Altra modalità di lancio della pallina, usata ai fini di debug 
	 * 
	 * @param drawNumber, il numero uscito
	 * 
	 * @throws IllegalStateException se non è partita una nuova giocata tramite waitBets
	 */
	void debugDrawing(int drawnNumber);
	
	/**
	 * Ritorna i risultati della giocata, ossia chi ha vinto quanto 
	 * 
	 * @return a mappa da nome giocatore a vincita
	 */
	Map<String,Integer> getWins();
	
	/**
	 * @return quanti soldi puntati ci sono sul tavolo
	 */
	int getTotalBets();
	
}
