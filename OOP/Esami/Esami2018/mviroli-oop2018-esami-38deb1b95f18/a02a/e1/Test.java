package a02a.e1;

import static org.junit.Assert.*;
import java.util.*;
import a02a.e1.Tournament.Result;
import a02a.e1.Tournament.Type;

/**
 * Si consulti la documentazione delle interfacce fornite:
 * - Tournament modella i risultati di un Torneo di tennis
 * - TournamentBuilder modella un builder (nel senso del pattern Builder) per Tournament
 * 
 * Si noti dalla documentazione come un torneo è di varie tipologie: ad esempio un ATP500 dà 500 punti a chi lo vince.
 * Si noti che il finalista (che perde in finale), il semifinalista (che perde in semifinale), eccetera, prendano
 * un ammontare diverso di punti: ad esempio il finalista prende sempre il 50% dei punti massimi, quindi il
 * finalista di un ATP500 prende 250 punti.
 * Si noti che quando un torneo comincia, ogni giocatore ha già dei punti acquisiti in passato (scritti in initialRanking),
 * e dopo il torneo di conseguenza avrà più punti o uguali, (scritti in resultingRanking). Sarà quindi possibile 
 * dopo il torneo vedere che è il top player (chi ha più punti), e via via gli altri -- il cosiddetto "ranking".
 * 
 * Implementare TournamentBuilder attraverso una classe TournamentBuilderImpl con costruttore senza argomenti,
 * in modo che passi tutti i test di cui sotto.
 *  
 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
 * della totalità del punteggio:
 * - implementazione dei test opzionali (relativi ad alcuni controlli per le eccezioni)
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
	
	/*
	private static final Map<String,Integer> INITIAL_RANKING = new HashMap<String,Integer>();
	static{
		INITIAL_RANKING.put("Nadal", 6000);
		INITIAL_RANKING.put("Federer", 7000);
		INITIAL_RANKING.put("Djokovic", 6500);
		INITIAL_RANKING.put("Fognini", 4000);
	}
	
	
	private TournamentBuilder tb;
	
	@org.junit.Before
	public void init() {
		tb = new TournamentBuilderImpl();
	}
	
	// verifica base: ATP1000 di roma, con vincitore Federer (e via via gli altri)
	@org.junit.Test
    public void basicTest() {
		Tournament t = tb.setName("roma")
				         .setType(Type.ATP1000)
				         .setPriorRanking(INITIAL_RANKING)
				         .addResult("Federer", Result.WINNER) // 1000 points
				         .addResult("Nadal", Result.FINALIST) // 500 points
				         .addResult("Djokovic", Result.SEMIFINALIST) // 200 points
				         .addResult("Tsitsipas", Result.QUARTERFINALIST) // 100 point
				         .addResult("Fognini", Result.PARTICIPANT) // 0 points
				         .build();
		assertEquals(t.getName(),"roma");
		assertEquals(t.getType(),Type.ATP1000);
		assertEquals(t.getResult("Federer"),Optional.of(Result.WINNER));
		assertEquals(t.getResult("Fognini"),Optional.of(Result.PARTICIPANT));
		assertEquals(t.getResult("Del Potro"),Optional.empty()); // non ha partecipato
		assertEquals(t.initialRanking(),INITIAL_RANKING);
		assertEquals(t.resultingRanking().get("Federer").intValue(),1000+7000);
		assertEquals(t.resultingRanking().get("Nadal").intValue(),500+6000);
		assertEquals(t.resultingRanking().get("Djokovic").intValue(),200+6500);
		assertEquals(t.resultingRanking().get("Fognini").intValue(),4000);
		assertEquals(t.resultingRanking().get("Tsitsipas").intValue(),100);
		assertEquals(t.winner(),"Federer");
		// classifica finale dei giocatori dopo il torneo, da chi ha più punti in tutto, in giù
		assertEquals(t.rank(),Arrays.asList("Federer","Djokovic","Nadal","Fognini","Tsitsipas"));
    }
	
	// analoga verifica: MAJOR a melbourne, con vincitore Djokovic
	@org.junit.Test
    public void anotherTest() {
		Tournament t = tb.setName("melbourne")
				         .setType(Type.MAJOR)
				         .setPriorRanking(INITIAL_RANKING)
				         .addResult("Djokovic", Result.WINNER) // 2500 points
				         .addResult("Nadal", Result.FINALIST) // 1250 points
				         .addResult("Tsitsipas", Result.SEMIFINALIST) // 500 point
				         .addResult("Federer", Result.QUARTERFINALIST) // 250 points
				         .build();
		assertEquals(t.getName(),"melbourne");
		assertEquals(t.getType(),Type.MAJOR);
		assertEquals(t.getResult("Djokovic"),Optional.of(Result.WINNER));
		assertEquals(t.getResult("Nadal"),Optional.of(Result.FINALIST));
		assertEquals(t.initialRanking(),INITIAL_RANKING);
		assertEquals(t.resultingRanking().get("Federer").intValue(),250+7000);
		assertEquals(t.resultingRanking().get("Nadal").intValue(),1250+6000);
		assertEquals(t.resultingRanking().get("Djokovic").intValue(),2500+6500);
		assertEquals(t.resultingRanking().get("Fognini").intValue(),4000);
		assertEquals(t.resultingRanking().get("Tsitsipas").intValue(),500);
		assertEquals(t.winner(),"Djokovic");
		assertEquals(t.rank(),Arrays.asList("Djokovic","Federer","Nadal","Fognini","Tsitsipas"));
    }
	
	// Da qui in poi, test su situazioni di eccezione: il nome del test è esplicativo
	// Alcuni sono opzionali
	
	@org.junit.Test(expected = IllegalStateException.class)
	public void testNoTypeSpecified() {
		Tournament t = tb.setName("melbourne")
		         .setPriorRanking(INITIAL_RANKING)
		         .addResult("Djokovic", Result.WINNER) 
		         .addResult("Nadal", Result.FINALIST) 
		         .addResult("Tsitsipas", Result.SEMIFINALIST) 
		         .addResult("Federer", Result.QUARTERFINALIST) 
		         .build();
    }
	
	@org.junit.Test(expected = IllegalStateException.class)
	public void testTournamentCantBeBuiltTwice() {
		TournamentBuilder tb2 = tb.setName("melbourne")
		         .setType(Type.MAJOR)
		         .setPriorRanking(INITIAL_RANKING)
		         .addResult("Djokovic", Result.WINNER) 
		         .addResult("Nadal", Result.FINALIST) 
		         .addResult("Tsitsipas", Result.SEMIFINALIST) 
		         .addResult("Federer", Result.QUARTERFINALIST); 
		tb2.build();
		tb2.build();
	}
	
	@org.junit.Test(expected = IllegalStateException.class)
	public void optionalTestNoNameSpecified() {
		Tournament t = tb.setType(Type.MAJOR)
		         .setPriorRanking(INITIAL_RANKING)
		         .addResult("Djokovic", Result.WINNER) 
		         .addResult("Nadal", Result.FINALIST) 
		         .addResult("Tsitsipas", Result.SEMIFINALIST) 
		         .addResult("Federer", Result.QUARTERFINALIST) 
		         .build();
	}
	
	@org.junit.Test(expected = IllegalStateException.class)
	public void optionalTestNoRankingSpecified() {
		Tournament t = tb.setName("roma")
				 .setType(Type.MAJOR)
		         .addResult("Djokovic", Result.WINNER) 
		         .addResult("Nadal", Result.FINALIST) 
		         .addResult("Tsitsipas", Result.SEMIFINALIST) 
		         .addResult("Federer", Result.QUARTERFINALIST) 
		         .build();
	}

	
	@org.junit.Test(expected = IllegalStateException.class)
	public void optionalTestCantAddResultBeforeType() {
		Tournament t = tb.setName("melbourne")
		         .setPriorRanking(INITIAL_RANKING)
		         .addResult("Djokovic", Result.WINNER) 
		         .setType(Type.MAJOR)
		         .addResult("Nadal", Result.FINALIST) 
		         .addResult("Tsitsipas", Result.SEMIFINALIST) 
		         .addResult("Federer", Result.QUARTERFINALIST)
		         .build(); 
	}
	
	@org.junit.Test(expected = IllegalStateException.class)
	public void optionalTestCantAddResultTwice() {
		Tournament t = tb.setName("melbourne")
		         .setPriorRanking(INITIAL_RANKING)
		         .setType(Type.MAJOR)
		         .addResult("Djokovic", Result.WINNER) 
		         .addResult("Djokovic", Result.FINALIST) 
		         .build();
	}

	@org.junit.Test(expected = IllegalStateException.class)
	public void optionalTestCantAddWinnerTwice() {
		Tournament t = tb.setName("melbourne")
		         .setPriorRanking(INITIAL_RANKING)
		         .setType(Type.MAJOR)
		         .addResult("Djokovic", Result.WINNER) 
		         .addResult("Nadal", Result.WINNER) 
		         .build(); 
	}
	
	@org.junit.Test(expected = NullPointerException.class)
	public void optionalTypeCantBeNull() {
		Tournament t = tb.setName("melbourne")
		         .setPriorRanking(INITIAL_RANKING)
		         .setType(null)
		         .build(); 
	}
	*/
}
