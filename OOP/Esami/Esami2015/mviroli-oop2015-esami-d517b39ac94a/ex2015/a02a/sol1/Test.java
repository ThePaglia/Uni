package ex2015.a02a.sol1;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import java.util.*;

public class Test {
	
	/*
	 * Implementare l'interfaccia League data tramite una classe LeagueImpl con costruttore senza argomenti.
	 * Modella il gestore di un campionato di calcio, con un metodo per aggiungere inizialmente le squadre,
	 * uno per iniziare il campionato, uno per inserire i risultati di una giornata, e infine uno per recuperare la classifica.
	 * Il commento al codice di League, e i metodi di test qui sotto costituiscono la necessaria spiegazione del 
	 * problema.
	 * I test il cui nome comincia con 'optional' sono considerati opzionali ai fini della possibilità di correggere
	 * l'esercizio -- anche se concorrono alla definizione del punteggio.
	 * Si tolga il commento ai test..
	 */

    private final static String INTER = "Inter";
    private final static String JUVE = "Juve";
    private final static String MILAN = "Milan";
    private final static String ROMA = "Roma";
    private final static String NAPOLI = "Napoli";
    private final static String LAZIO = "Lazio";
    
    // Metodo per costruire un campionato di partenza
    private League buildInitialLeague(){
        final League league = new LeagueImpl();
        league.addTeam(INTER);
        league.addTeam(JUVE);
        league.addTeam(MILAN);
        league.addTeam(ROMA);
        league.addTeam(NAPOLI);
        league.addTeam(LAZIO);
        return league;
    }
    
    @org.junit.Test
	public void testInitialTable() {
		final League league = buildInitialLeague();
		league.start();
		// Classifica: tutte a 0 inizialmente
		assertEquals(league.getTable().size(),6);
		assertThat(league.getTable().keySet(),hasItems(INTER,JUVE,MILAN,ROMA,LAZIO,NAPOLI));
		assertThat(league.getTable().values(),hasItems(0,0,0,0,0,0));
	}
	
    @org.junit.Test
    public void testTwoResults() {
        final League league = buildInitialLeague();
        league.start();
        // coatruzione risutati di una giornata
        Map<Pair<String,String>,Pair<Integer,Integer>> res = new HashMap<>();
        res.put(new Pair<>(INTER,JUVE), new Pair<>(3,0)); // Inter 3, Juve 0
        res.put(new Pair<>(MILAN,ROMA), new Pair<>(0,0)); // Milan 0, Roma 0
        res.put(new Pair<>(LAZIO,NAPOLI), new Pair<>(0,1)); // Lazio 0, Napoli 1
        league.storeResults(res);
        // Leggo la nuova classifica
        assertEquals(league.getTable().size(),6);
        assertThat(league.getTable().keySet(),hasItems(INTER,JUVE,MILAN,ROMA,LAZIO,NAPOLI));
        // Punteggi squadra per squadra
        assertEquals(league.getTable().get(INTER).intValue(),3);
        assertEquals(league.getTable().get(NAPOLI).intValue(),3);
        assertEquals(league.getTable().get(MILAN).intValue(),1);
        assertEquals(league.getTable().get(ROMA).intValue(),1);
        assertEquals(league.getTable().get(LAZIO).intValue(),0);
        assertEquals(league.getTable().get(JUVE).intValue(),0);
        
        // Altri risultati
        res = new HashMap<>();
        res.put(new Pair<>(INTER,MILAN), new Pair<>(0,0));
        res.put(new Pair<>(JUVE,LAZIO), new Pair<>(3,3));
        res.put(new Pair<>(ROMA,NAPOLI), new Pair<>(2,2));
        league.storeResults(res);
        System.out.println(league.getTable());
        assertEquals(league.getTable().size(),6);
        assertThat(league.getTable().keySet(),hasItems(INTER,JUVE,MILAN,ROMA,LAZIO,NAPOLI));
        assertEquals(league.getTable().get(INTER).intValue(),4);
        assertEquals(league.getTable().get(NAPOLI).intValue(),4);
        assertEquals(league.getTable().get(MILAN).intValue(),2);
        assertEquals(league.getTable().get(ROMA).intValue(),2);
        assertEquals(league.getTable().get(LAZIO).intValue(),1);
        assertEquals(league.getTable().get(JUVE).intValue(),1);
    }
    
    @org.junit.Test
    public void testErrorsOnAdding() {
        final League league = buildInitialLeague();
        try{
            league.addTeam(null);
            fail("cannot add a null team");
        } catch (NullPointerException e){
        } catch (Exception e){
            fail("wrong exception thrown");
        }
        try{
            league.addTeam(INTER);
            fail("cannot add a team twice");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception thrown");
        }
    }
    
    @org.junit.Test
    public void testErrorsOnStarted() {
        final League league = buildInitialLeague();
        // operazioni non possibili prima che il campionato sia partito
        try{
            league.getTable();
            fail("cannot read table before started");
        } catch (IllegalStateException e){
        } catch (Exception e){
            fail("wrong exception thrown");
        }
        try{
            league.storeResults(null);
            fail("cannot store results before started");
        } catch (IllegalStateException e){
        }catch (Exception e){
            fail("wrong exception thrown");
        }
        league.start();
        // operazioni non possibili dopo che il campionato sia partito
        try{
            league.addTeam("Cesena");
            fail("cannot add a team once started");
        } catch (IllegalStateException e){
        } catch (Exception e){
            fail("wrong exception thrown");
        }
    }
    
    @org.junit.Test
    public void optionalTestNameClash1() {
        final League league = buildInitialLeague();
        league.start();
        Map<Pair<String,String>,Pair<Integer,Integer>> res = new HashMap<>();
        res.put(new Pair<>(INTER,INTER), new Pair<>(3,0)); // NO!!!
        res.put(new Pair<>(MILAN,ROMA), new Pair<>(0,0));
        res.put(new Pair<>(LAZIO,NAPOLI), new Pair<>(0,1));
        try{
            league.storeResults(res);
            fail("a team can't play against itself");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception thrown");
        }
    }
    
    @org.junit.Test
    public void optionalTestNameClash2() {
        final League league = buildInitialLeague();
        league.start();
        Map<Pair<String,String>,Pair<Integer,Integer>> res = new HashMap<>();
        res.put(new Pair<>(INTER,JUVE), new Pair<>(3,0));
        res.put(new Pair<>(INTER,ROMA), new Pair<>(0,0)); // NO!!!
        res.put(new Pair<>(LAZIO,NAPOLI), new Pair<>(0,1));
        try{
            league.storeResults(res);
            fail("a team can't play twice");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception thrown");
        }
    }
    
    @org.junit.Test
    public void optionalTestNameClash3() {
        final League league = buildInitialLeague();
        league.start();
        Map<Pair<String,String>,Pair<Integer,Integer>> res = new HashMap<>();
        res.put(new Pair<>(INTER,JUVE), new Pair<>(3,0));
        res.put(new Pair<>("Cesena",ROMA), new Pair<>(0,0)); // NO!!!
        res.put(new Pair<>(LAZIO,NAPOLI), new Pair<>(0,1));
        try{
            league.storeResults(res);
            fail("a team do not exist");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception thrown");
        }
    }
}
