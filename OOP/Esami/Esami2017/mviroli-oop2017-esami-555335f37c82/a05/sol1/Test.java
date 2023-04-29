package a05.sol1;

import static org.junit.Assert.*;
import java.util.*;

/**
 * In questo esercizio si modellerà un campionato organizzato all'Italiana (o meglio, simil-Italian), ossia con N squadre 
 * (pari) che svolgono (N-1)*2 giornate (day). Una squadra può giocare in casa (home) o in trasferta (away). Quindi nelle 
 * (N-1)*2 giornate ogni squadra incontra tutte le altre squadre 2 volte, una volta in casa e una volta fuori.
 * Una volta registrate le squadre in un certo ordine, le partite di una data giornata sono organizzate partendo dalla 
 * prima squadra (via via scendendo nella lista), in casa, e trovandogli (se c'è) la prima squadra con la quale non 
 * abbia già giocato in casa. 
 * Questo algoritmo è disponibile nella classe data Combinations---e può essere riadattato a piacimento, facoltativamente. 
 * Si noti quindi, che per semplicità a differenza della normale gestione all'Italiana, non c'è la suddivisione 
 * "girone d'andata" e "girone di ritorno", e quindi due squadre potrebbero giocare la partita d'andata e quella di ritorno 
 * anche entrambe all'inizio del campionato, o entrambe alla fine.
 * Si consideri a tal fine anche il test, e in particolare: testChampionship
 * 
 * Si consulti la documentazione dell'interfaccia Match fornita, che è già implementata in MatchImpl 
 * (si consiglia di usare questa implementazione).
 * Poi si consulti la documentazione della interfaccia Championship, che sarà da implementare con una classe 
 * ChampionshipImpl con costruttore senza argomenti. 
 *  
 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque 
 * al raggiungimento della totalità del punteggio:
 * - implementazione del test opzionale (relativo al metodo getClassification)
 * - la buona progettazione della soluzione
 * 
 * Si osservi con attenzione il test seguente, che assieme ai commenti delle interfacce date
 * costituisce la definizione del problema da risolvere. Si noti che in questo esercizio non è richiesto di 
 * gestire casi particolare che normalmente dovrebbero lanciare eccezioni.
 * 
 * Si tolga il commento al codice del test.
 */

public class Test {
	
	private static final List<String> teams = Arrays.asList("Juve", "Inter", "Milan", "Napoli");
	        
	@org.junit.Test
    public void testStart() {
		Championship c = new ChampionshipImpl();
		teams.forEach(team -> c.registerTeam(team));
		c.startChampionship();
		// non avendo chiamato newDay(), i pending match non sono ancora stati preparati
		assertTrue(c.pendingMatches().isEmpty());
	}
	
	@org.junit.Test
    public void testFirstDay() {
		ChampionshipImpl c = new ChampionshipImpl();
		teams.forEach(team -> c.registerTeam(team)); // "Juve", "Inter", "Milan", "Napoli"
		c.startChampionship();
		c.newDay(); 
		assertEquals(c.pendingMatches().size(),2);
		// si parte dalla Juve, che quindi gioca con la seconda in ordine, l'Inter
		assertTrue(c.pendingMatches().contains(new MatchImpl("Juve","Inter")));
		// L'Inter è già assegnata, quindi si prosegue con il Milanin casa, che gioca quindi col Napoli
		assertTrue(c.pendingMatches().contains(new MatchImpl("Milan","Napoli")));		
	}
	
	@org.junit.Test
    public void testGames() {
		ChampionshipImpl c = new ChampionshipImpl();
		teams.forEach(team -> c.registerTeam(team)); // "Juve", "Inter", "Milan", "Napoli"
		c.startChampionship();
		c.newDay();
		assertEquals(c.pendingMatches().size(),2);
		c.matchPlay(new MatchImpl("Juve","Inter"), 0, 2); // vince l'inter 2-0
		assertEquals(c.pendingMatches().size(),1);
		c.matchPlay(new MatchImpl("Milan","Napoli"), 1, 1); // pareggio 1-1
		assertEquals(c.pendingMatches().size(),0); // nota che non ci sono match pendendi ora, fino al prossimo newDay
	}
	
	@org.junit.Test
    public void testNewDay() {
		ChampionshipImpl c = new ChampionshipImpl();
		teams.forEach(team -> c.registerTeam(team)); // "Juve", "Inter", "Milan", "Napoli"
		c.startChampionship();
		c.newDay();
		c.matchPlay(new MatchImpl("Juve","Inter"), 0, 2);
		c.matchPlay(new MatchImpl("Milan","Napoli"), 1, 1);
		assertEquals(c.pendingMatches().size(),0);
		// comincio la seconda giornata
		c.newDay();
		assertEquals(c.pendingMatches().size(),2);
		// si parte sempre dalla Juve in casa: la prossima possibile è il Milan
		assertTrue(c.pendingMatches().contains(new MatchImpl("Juve","Milan")));
		// la prossima da considerare è l'Inter, e rimane solo il Napoli
		assertTrue(c.pendingMatches().contains(new MatchImpl("Inter","Napoli")));				
	}
	
	@org.junit.Test
    public void testChampionship() {
		ChampionshipImpl c = new ChampionshipImpl();
		teams.forEach(team -> c.registerTeam(team)); // "Juve", "Inter", "Milan", "Napoli"
		c.startChampionship();
		// elenco tutte le 6 giornate ( (4-1)*2 )
		c.newDay();
		assertTrue(c.pendingMatches().contains(new MatchImpl("Juve","Inter")));
		assertTrue(c.pendingMatches().contains(new MatchImpl("Milan","Napoli")));		
		c.matchPlay(new MatchImpl("Juve","Inter"), 0, 2);
		c.matchPlay(new MatchImpl("Milan","Napoli"), 1, 1);
		c.newDay();
		assertTrue(c.pendingMatches().contains(new MatchImpl("Juve","Milan")));
		assertTrue(c.pendingMatches().contains(new MatchImpl("Inter","Napoli")));				
		c.matchPlay(new MatchImpl("Juve","Milan"), 0, 0);
		c.matchPlay(new MatchImpl("Inter","Napoli"), 2, 1);
		c.newDay();
		assertTrue(c.pendingMatches().contains(new MatchImpl("Juve","Napoli")));
		assertTrue(c.pendingMatches().contains(new MatchImpl("Inter","Milan")));				
		c.matchPlay(new MatchImpl("Juve","Napoli"), 0, 1);
		c.matchPlay(new MatchImpl("Inter","Milan"), 3, 0);
		c.newDay();
		assertTrue(c.pendingMatches().contains(new MatchImpl("Inter","Juve")));
		assertTrue(c.pendingMatches().contains(new MatchImpl("Napoli","Milan")));				
		c.matchPlay(new MatchImpl("Inter","Juve"), 0, 2);
		c.matchPlay(new MatchImpl("Napoli","Milan"), 1, 1);
		c.newDay();
		assertTrue(c.pendingMatches().contains(new MatchImpl("Milan","Juve")));
		assertTrue(c.pendingMatches().contains(new MatchImpl("Napoli","Inter")));				
		c.matchPlay(new MatchImpl("Milan","Juve"), 0, 0);
		c.matchPlay(new MatchImpl("Napoli","Inter"), 2, 1);
		c.newDay();
		assertTrue(c.pendingMatches().contains(new MatchImpl("Milan","Inter")));
		assertTrue(c.pendingMatches().contains(new MatchImpl("Napoli","Juve")));				
		c.matchPlay(new MatchImpl("Milan","Inter"), 0, 1);
		c.matchPlay(new MatchImpl("Napoli","Juve"), 3, 0);
	}
	
	@org.junit.Test
    public void testChampionshipOver() {
		ChampionshipImpl c = new ChampionshipImpl();
		teams.forEach(team -> c.registerTeam(team)); // "Juve", "Inter", "Milan", "Napoli"
		c.startChampionship();
		c.newDay();
		c.matchPlay(new MatchImpl("Juve","Inter"), 0, 2);
		c.matchPlay(new MatchImpl("Milan","Napoli"), 1, 1);
		c.newDay();
		c.matchPlay(new MatchImpl("Juve","Milan"), 0, 0);
		c.matchPlay(new MatchImpl("Inter","Napoli"), 2, 1);
		c.newDay();
		c.matchPlay(new MatchImpl("Juve","Napoli"), 0, 1);
		c.matchPlay(new MatchImpl("Inter","Milan"), 3, 0);
		c.newDay();
		c.matchPlay(new MatchImpl("Inter","Juve"), 0, 2);
		c.matchPlay(new MatchImpl("Napoli","Milan"), 1, 1);
		c.newDay();
		c.matchPlay(new MatchImpl("Milan","Juve"), 0, 0);
		c.matchPlay(new MatchImpl("Napoli","Inter"), 2, 1);
		c.newDay();
		assertFalse(c.championshipOver());
		c.matchPlay(new MatchImpl("Milan","Inter"), 0, 1);
		c.matchPlay(new MatchImpl("Napoli","Juve"), 3, 0);
		assertTrue(c.championshipOver());
	}
	
	@org.junit.Test
    public void optionalTestClassification() {
		ChampionshipImpl c = new ChampionshipImpl();
		teams.forEach(team -> c.registerTeam(team));
		c.startChampionship();
		c.newDay();
		// classifica all'inizio
		assertEquals(c.getClassification().get("Juve"),new Integer(0));
		assertEquals(c.getClassification().get("Inter"),new Integer(0));
		assertEquals(c.getClassification().get("Milan"),new Integer(0));
		assertEquals(c.getClassification().get("Napoli"),new Integer(0));
		c.matchPlay(new MatchImpl("Juve","Inter"), 0, 2);
		c.matchPlay(new MatchImpl("Milan","Napoli"), 1, 1);
		c.newDay();
		// classifica dopo una giornata
		assertEquals(c.getClassification().get("Juve"),new Integer(0));
		assertEquals(c.getClassification().get("Inter"),new Integer(3));
		assertEquals(c.getClassification().get("Milan"),new Integer(1));
		assertEquals(c.getClassification().get("Napoli"),new Integer(1));
		c.matchPlay(new MatchImpl("Juve","Milan"), 0, 0);
		c.matchPlay(new MatchImpl("Inter","Napoli"), 2, 1);
		c.newDay();
		c.matchPlay(new MatchImpl("Juve","Napoli"), 0, 1);
		c.matchPlay(new MatchImpl("Inter","Milan"), 3, 0);
		c.newDay();
		c.matchPlay(new MatchImpl("Inter","Juve"), 0, 2);
		c.matchPlay(new MatchImpl("Napoli","Milan"), 1, 1);
		c.newDay();
		c.matchPlay(new MatchImpl("Milan","Juve"), 0, 0);
		c.matchPlay(new MatchImpl("Napoli","Inter"), 2, 1);
		c.newDay();
		assertFalse(c.championshipOver());
		c.matchPlay(new MatchImpl("Milan","Inter"), 0, 1);
		c.matchPlay(new MatchImpl("Napoli","Juve"), 3, 0);
		assertTrue(c.championshipOver());
		// classifica finale
		assertEquals(c.getClassification().get("Juve"),new Integer(5));
		assertEquals(c.getClassification().get("Inter"),new Integer(12));
		assertEquals(c.getClassification().get("Milan"),new Integer(4));
		assertEquals(c.getClassification().get("Napoli"),new Integer(11));
	}
}
