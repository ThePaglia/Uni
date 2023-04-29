package a02b.sol1;

import static org.junit.Assert.*;
import java.util.*;


/**
 * Si consulti la documentazione delle interfacce fornite:
 * - Tournament modella i risultati di un Torneo di tennis
 * - TournamentFactory modella una semplice factory per tornei
 * - Ranking modella uno storico dei risultati degli ultimi tornei
 *  
 * Si noti dalla documentazione come un torneo abbia una precisa collocazione nel tempo (anno e settimana).
 * Si noti che un giocatore che partecipa ad un torneo acquisisce un numero di punti >=0
 * Si noti che i tornei sono caricabili uno dopo l'altro in ordine cronologico
 * Si noti che ai fini del calcolo del punteggio complessivo di un giocatore, concorrono solo i tornei
 * dell'ultimo anno (andando all'indietro dall'ultimo torneo caricato) 
 * 
 * Implementare TournamentFactory attraverso una classe TournamentFactoryImpl con costruttore senza argomenti,
 * e implementare Ranking attraverso una class RankingImpl con costruttore senza argomenti,
 * in modo che passi tutti i test di cui sotto.
 *  
 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
 * della totalità del punteggio:
 * - implementazione dei test opzionali (relativi ad alcuni controlli per le eccezioni e agli ultimi due metodi di Ranking)
 * - la qualità della soluzione, in particolare con minimizzazione di ripetizioni e codice non inutilmente complesso
 * 
 * Indicazioni di punteggio:
 * - correttezza della parte obbligatoria: 9 punti
 * - correttezza della parte opzionale: 3 punti
 * - qualità della soluzione: 5 punti
 * 
 * Si tolga il commento al codice del test.
 */

public class Test {
	
	private static final Map<String,Integer> P1 = new HashMap<String,Integer>(){{
		this.put("Nadal", 6000);
		this.put("Federer", 7000);
		this.put("Djokovic", 6500);
		this.put("Fognini", 4000);
	}};
	
	private TournamentFactory tf;
	private Ranking ranking;
	
	private Tournament roma2018() {
		Set<String> players = new HashSet<>(Arrays.asList("Federer","Nadal","Djokovic","Fognini"));
		Map<String,Integer> points = new HashMap<String,Integer>(){{
			this.put("Federer", 1000);
			this.put("Djokovic", 500);
		}};
		return tf.make("roma2018", 2018, 20, players, points);
	}
	
	private Tournament wimbledon2018() {
		Set<String> players = new HashSet<>(Arrays.asList("Federer","Nadal","Djokovic","Tsitsipas"));
		Map<String,Integer> points = new HashMap<String,Integer>(){{
			this.put("Djokovic", 2500);
			this.put("Federer", 1000);
			this.put("Nadal", 500);
		}};
		return tf.make("wimbledon2018", 2018, 30, players, points);
	}
	
	private Tournament roma2019() {
		Set<String> players = new HashSet<>(Arrays.asList("Federer","Nadal","Djokovic","Tsitsipas"));
		Map<String,Integer> points = new HashMap<String,Integer>(){{
			this.put("Nadal", 1500);
			this.put("Federer", 500);
			this.put("Tsitsipas", 200);
		}};
		return tf.make("roma2019", 2019, 20, players, points);
	}
	
	@org.junit.Before
	public void init() {
		tf = new TournamentFactoryImpl();
		ranking = new RankingImpl();
	}
	
	// verifica base sulla creazione del torneo roma2018
	@org.junit.Test
    public void testRoma2018() {
		Tournament t = roma2018();
		assertEquals(t.getName(),"roma2018");
		assertEquals(t.getYear(),2018);
		assertEquals(t.getWeek(),20);
		assertEquals(t.getResult("Federer"),Optional.of(1000));
		assertEquals(t.getResult("Djokovic"),Optional.of(500));
		assertEquals(t.getResult("Nadal"),Optional.of(0)); // ha partecipato
		assertEquals(t.getResult("Fognini"),Optional.of(0)); // ha partecipato
		assertEquals(t.getResult("Tsitsipas"),Optional.empty()); // non ha partecipato
		assertEquals(t.winner(), "Federer");
	}
	
	// verifica base sulla creazione del torneo roma2018
	@org.junit.Test
    public void testWimbledon2018() {
		Tournament t = wimbledon2018();
		assertEquals(t.getName(),"wimbledon2018");
		assertEquals(t.getYear(),2018);
		assertEquals(t.getWeek(),30);
		assertEquals(t.getResult("Djokovic"),Optional.of(2500));
		assertEquals(t.getResult("Federer"),Optional.of(1000));
		assertEquals(t.getResult("Nadal"),Optional.of(500)); 
		assertEquals(t.getResult("Tsitsipas"),Optional.of(0)); // ha partecipato
		assertEquals(t.getResult("Fognini"),Optional.empty()); // non ha partecipato
		assertEquals(t.winner(), "Djokovic");
	}
	
	@org.junit.Test
    public void testWholeRanking() {
		ranking.loadTournament(roma2018());
		ranking.loadTournament(wimbledon2018());
		ranking.loadTournament(roma2019()); // points will only be counted on the last two
		assertEquals(ranking.getCurrentWeek(),20);
		assertEquals(ranking.getCurrentYear(),2019);
		assertEquals(ranking.pointsFromPlayer("Federer").intValue(),1500);
		assertEquals(ranking.pointsFromPlayer("Djokovic").intValue(),2500);
		assertEquals(ranking.pointsFromPlayer("Nadal").intValue(),2000);
		assertEquals(ranking.pointsFromPlayer("Tsitsipas").intValue(),200);
		assertEquals(ranking.pointsFromPlayer("Fognini").intValue(),0);
		assertEquals(ranking.pointsFromPlayer("Fognini").intValue(),0);
		assertEquals(ranking.ranking(),Arrays.asList("Djokovic","Nadal","Federer","Tsitsipas","Fognini"));
		assertEquals(ranking.winnersFromTournamentInLastYear().get("roma2019"),"Nadal");
		assertEquals(ranking.winnersFromTournamentInLastYear().get("wimbledon2018"),"Djokovic");
	}
	
	@org.junit.Test
    public void optionalTestPointsAtEachTournamentFromPlayer() {
		ranking.loadTournament(roma2018());
		ranking.loadTournament(wimbledon2018());
		ranking.loadTournament(roma2019()); // points will only be counted on the last two
		assertEquals(ranking.pointsAtEachTournamentFromPlayer("Federer").get("roma2018").intValue(),1000);
		assertEquals(ranking.pointsAtEachTournamentFromPlayer("Federer").get("wimbledon2018").intValue(),1000);
		assertEquals(ranking.pointsAtEachTournamentFromPlayer("Federer").get("roma2019").intValue(),500);
		assertEquals(ranking.pointsAtEachTournamentFromPlayer("Federer").size(),3);
		assertEquals(ranking.pointsAtEachTournamentFromPlayer("Tsitsipas").get("roma2019").intValue(),200);
		assertEquals(ranking.pointsAtEachTournamentFromPlayer("Tsitsipas").get("wimbledon2018").intValue(),0);
		assertEquals(ranking.pointsAtEachTournamentFromPlayer("Tsitsipas").size(),2);
		List<Pair<String,Integer>> list = ranking.pointsAtEachTournamentFromPlayerSorted("Federer");
		assertEquals(list.size(),3);
		assertEquals(list.get(0),new Pair<>("roma2018",1000));
		assertEquals(list.get(1),new Pair<>("wimbledon2018",1000));
		assertEquals(list.get(2),new Pair<>("roma2019",500));
		list = ranking.pointsAtEachTournamentFromPlayerSorted("Tsitsipas");
		assertEquals(list.size(),2);
		assertEquals(list.get(0),new Pair<>("wimbledon2018",0));
		assertEquals(list.get(1),new Pair<>("roma2019",200));
	}
	
	@org.junit.Test(expected = IllegalStateException.class)
	public void optionalTestCantLoadATournamentTwice() {
		ranking.loadTournament(roma2018());
		ranking.loadTournament(roma2018());
	}
	
	@org.junit.Test(expected = IllegalStateException.class)
	public void optionalTestCantLoadTournamentsInWrongOrder() {
		ranking.loadTournament(wimbledon2018());
		ranking.loadTournament(roma2018());
	}
	
	@org.junit.Test(expected = IllegalStateException.class)
	public void optionalTestCantAccessPropertiesBeforeLoadingTournaments() {
		ranking.getCurrentWeek();
	}

}
