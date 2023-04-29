package a04.sol1;

import static org.junit.Assert.*;
import java.util.*;

/**
 * In questo esercizio si modellerà un torneo ad eliminazione diretta (con quarti di finale, semifinale, finale..),
 * con tabellone del tipo mostrato in figura (tabellone.gif.. i numeri riportati non sono indicativi).
 * 
 * Si consulti prima la documentazione delle interfacce Player e Match fornite, che sono già implementate 
 * in PlayerImpl e MatchImpl (si consiglia di usare queste implementazione).
 * Si consulti poi la documentazione della interfaccia Turnament, che sarà da implementare con una classe 
 * TurnamentImpl con costruttore senza argomenti. 
 *  
 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque 
 * al raggiungimento della totalità del punteggio:
 * - implementazione dei test opzionali (relativi ai metodi isTurnamentOver, winner e opponents)
 * - la buona progettazione della soluzione
 * 
 * Si osservi con attenzione il test seguente, che assieme ai commenti delle interfacce date
 * costituisce la definizione del problema da risolvere. Si noti che in questo esercizio non è richiesto di 
 * gestire casi particolare che normalmente dovrebbero lanciare eccezioni.
 * 
 * Si tolga il commento al codice del test.
 */

public class Test {
	
	private List<Player> players = null;
	
	private Turnament createTurnament() {
		// Metodo per creare un turneo a 8 giocatori
		// quindi con 4 quarti di finale, poi 2 simifinali, infine una finale
		// ..la vostra soluzione dovrebbero però gestire anche i casi con 16,32,64 giocatori..
		Turnament t = new TurnamentImpl();
		players = new LinkedList<>();
		players.add(t.makePlayer(100, "federer"));
		players.add(t.makePlayer(101, "fognini"));
		players.add(t.makePlayer(102, "wawrinka"));
		players.add(t.makePlayer(300, "cecchinato"));
		players.add(t.makePlayer(200, "zverev"));
		players.add(t.makePlayer(400, "thiem"));
		players.add(t.makePlayer(105, "kyrgios"));
		players.add(t.makePlayer(106, "nadal"));
		// registro gli 8 giocatori presso il torneo
		players.forEach(t::registerPlayer);
		return t;
	}
	
	@org.junit.Test
    public void testStart() {
		Turnament t = createTurnament();
		assertTrue(t.getPendingGames().isEmpty());
		// parte il torneo
		t.startTurnament();
		// ho 8 giocatori e 4 partite pendenti
		assertEquals(4,t.getPendingGames().size());
		assertEquals(8,t.getPlayers().size());
		// giocatore in cima al tabellone, in fondo al tabellone, e in posizione 6
		assertEquals("federer",t.getPlayers().get(0).getName());
		assertEquals("nadal",t.getPlayers().get(7).getName());
		assertEquals(105,t.getPlayers().get(6).getId());
    }
	
	@org.junit.Test
    public void testMatchesFirstRound() {
		Turnament t = createTurnament();
		t.startTurnament();
		// le prime 4 partite pendenti sono fra i giocatori di posizione: 0-1, 2-3, 4-5, 6-7
		assertTrue(t.getPendingGames().contains(t.makeMatch(players.get(0), players.get(1))));
		assertTrue(t.getPendingGames().contains(t.makeMatch(players.get(2), players.get(3))));
		assertTrue(t.getPendingGames().contains(t.makeMatch(players.get(4), players.get(5))));
		assertTrue(t.getPendingGames().contains(t.makeMatch(players.get(6), players.get(7))));
    }
	
	@org.junit.Test
    public void testPlay() {
		Turnament t = createTurnament();
		t.startTurnament();
		// gioca la partita 0-1, e vince il giocatore 0, rimangono 3 partite pendenti
		t.playMatch(t.makeMatch(players.get(0), players.get(1)), players.get(0));
		assertEquals(3,t.getPendingGames().size());
		assertTrue(t.getPendingGames().contains(t.makeMatch(players.get(2), players.get(3))));
		assertTrue(t.getPendingGames().contains(t.makeMatch(players.get(4), players.get(5))));
		assertTrue(t.getPendingGames().contains(t.makeMatch(players.get(6), players.get(7))));
		assertEquals(8,t.getPlayers().size());
    }
	
	@org.junit.Test
    public void testPlayWholeTurnament() {
		Turnament t = createTurnament();
		t.startTurnament();
		// torneo completo.. prima giocano 3 quarti di finale qualsiasi
		t.playMatch(t.makeMatch(players.get(0), players.get(1)), players.get(0));
		t.playMatch(t.makeMatch(players.get(6), players.get(7)), players.get(7));
		t.playMatch(t.makeMatch(players.get(2), players.get(3)), players.get(3));
		// rimane un solo quarto finale
		assertEquals(1,t.getPendingGames().size());
		t.playMatch(t.makeMatch(players.get(4), players.get(5)), players.get(5));
		// finiti i quarti di finale, ora le partite pendenti sono le due semifinali
		assertEquals(2,t.getPendingGames().size());
		// in base agli esiti dei quarti, le semifinali sono 0-3 e 5-7
		assertTrue(t.getPendingGames().contains(t.makeMatch(players.get(0), players.get(3))));
		assertTrue(t.getPendingGames().contains(t.makeMatch(players.get(5), players.get(7))));
		// si giocano le semifinali
		t.playMatch(t.makeMatch(players.get(0), players.get(3)), players.get(0));
		t.playMatch(t.makeMatch(players.get(5), players.get(7)), players.get(7));
		// manca solo la finale 0-7 
		assertTrue(t.getPendingGames().contains(t.makeMatch(players.get(0), players.get(7))));
		t.playMatch(t.makeMatch(players.get(0), players.get(7)), players.get(0));
    }

	@org.junit.Test
    public void optionalTestWinner() {
		Turnament t = createTurnament();
		t.startTurnament();
		// gioco i 4 quarti, le 2 semifinali
		t.playMatch(t.makeMatch(players.get(0), players.get(1)), players.get(0));
		t.playMatch(t.makeMatch(players.get(6), players.get(7)), players.get(7));
		t.playMatch(t.makeMatch(players.get(2), players.get(3)), players.get(3));
		t.playMatch(t.makeMatch(players.get(4), players.get(5)), players.get(5));
		t.playMatch(t.makeMatch(players.get(0), players.get(3)), players.get(0));
		t.playMatch(t.makeMatch(players.get(5), players.get(7)), players.get(7));
		// torneo non finito
		assertFalse(t.isTurnamentOver());
		// gioco la finale, il torneo è finito e ho il vincitore
		t.playMatch(t.makeMatch(players.get(0), players.get(7)), players.get(0));
		assertTrue(t.isTurnamentOver());
		assertEquals("federer",t.winner().getName());
    }
	
	@org.junit.Test
    public void optionalTestOpponents() {
		Turnament t = createTurnament();
		t.startTurnament();
		// gioco tutto il torneo
		t.playMatch(t.makeMatch(players.get(0), players.get(1)), players.get(0));
		t.playMatch(t.makeMatch(players.get(6), players.get(7)), players.get(7));
		t.playMatch(t.makeMatch(players.get(2), players.get(3)), players.get(3));
		t.playMatch(t.makeMatch(players.get(4), players.get(5)), players.get(5));
		t.playMatch(t.makeMatch(players.get(0), players.get(3)), players.get(0));
		t.playMatch(t.makeMatch(players.get(5), players.get(7)), players.get(7));
		t.playMatch(t.makeMatch(players.get(0), players.get(7)), players.get(0));
		Set<Player> opponents = new HashSet<>(Arrays.asList(
			players.get(1), players.get(3), players.get(7)
		));
		// il giocatore 0, che ha vinto il torneo, ha battuto i giocatori 1,3,7
		assertEquals(opponents,t.opponents(players.get(0)));
    }
        
}
