package ex2016.a03b.e1;

import static org.junit.Assert.*;

import java.util.*;

/**
 * Si consulti la documentazione delle interfacce Time e Clock fornite.
 * Time, che va implementata con una classe TimeImpl con costruttore a tre argomenti di tipo int (ore, minuti, secondi),
 * modella il concetto di orario (ad esempio: 20:30:00 per indicare le 8 e mezza di sera).
 * Clock, che va implementata con una classe ClockImpl con costruttore che prende in ingresso un Time, modella
 * un orologio con "sveglia", con metodi per avanzare di un secondo l'orario, e per registrare un osservatore
 * che verrà notificato ad una certa ora (pattern Observer).
 * 
 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
 * della totalità del punteggio:
 * - implementazione dei test opzionali (relativi alla gestione delle notifiche, ossia il pattern Observer) 
 * - la buona progettazione della soluzione, in particolare con minimizzazione di ripetizioni e uso di numeri magici
 * 
 * Si osservi con attenzione il test seguente, che assieme ai commenti delle interfacce date
 * costituisce la definizione del problema da risolvere.
 * 
 * Si tolga il commento al codice del test.
 */

public class Test {
    /*
    @org.junit.Test
    public void testTime() {
        // Test del funzionamento base, in particolare, di getSecondsFromMidnight
        final Time time = new TimeImpl(20,30,0); // 20:30:00, 8 e mezza di sera
        assertEquals(time.getHours(),20);
        assertEquals(time.getMinutes(),30);
        assertEquals(time.getSeconds(),0);
        assertEquals(time.getSecondsFromMidnight(),73800);
        final Time time2 = new TimeImpl(1,1,1); // l'una e un minuti e un secondo, di notte
        assertEquals(time2.getHours(),1); // un'ora.. 3600 secondi
        assertEquals(time2.getMinutes(),1); // un minuto.. 60 secondi
        assertEquals(time2.getSeconds(),1);
        assertEquals(time2.getSecondsFromMidnight(),3600+60+1);
    }
    
    @org.junit.Test
    public void testLabel() {
        final Time time = new TimeImpl(20,30,0); // 20:30:00, 8 e mezza di sera
        assertEquals(time.getLabel24(),"20:30:00");
        final Time time2 = new TimeImpl(1,1,1); // l'una e un minuto e un secondo, di notte
        assertEquals(time2.getLabel24(),"01:01:01");
    }
    
    @org.junit.Test
    public void testClock() {
        // Verifica del funzionamento del tick del clock
        final Time time = new TimeImpl(20,30,0); // 20:30:00, 8 e mezza di sera
        final Clock clock = new ClockImpl(time);
        assertEquals(clock.getTime(),time); // va implementata TimeImpl.equals!!
        clock.tick();
        assertEquals(clock.getTime(),new TimeImpl(20,30,1)); 
        for (int i=0;i<59;i++){ // passano 59 secondi
            clock.tick();
        }
        assertEquals(clock.getTime(),new TimeImpl(20,31,0)); 
        for (int i=0;i<60;i++){ // passa un minuto
            clock.tick();
        }
        assertEquals(clock.getTime(),new TimeImpl(20,32,0)); 
        for (int i=0;i<8*60;i++){ // passano 8 minuti
            clock.tick();
        }
        assertEquals(clock.getTime(),new TimeImpl(20,40,0)); 
        for (int i=0;i<20*60+1;i++){ // passano 20 minuti e un secondo
            clock.tick();
        }
        assertEquals(clock.getTime(),new TimeImpl(21,0,1)); 
        for (int i=0;i<60*60;i++){ // passa un'ora
            clock.tick();
        }
        assertEquals(clock.getTime(),new TimeImpl(22,0,1)); 
        for (int i=0;i<2*60*60;i++){ // passano due ore
            clock.tick();
        }
        assertEquals(clock.getTime(),new TimeImpl(0,0,1)); 
    }
    
    @org.junit.Test
    public void testTimeException() {
        // verifica dei limiti di ore (0-23), minuti (0-59) e secondi (0-59)
        try{
            new TimeImpl(25,30,0);
            fail("no 25 hours");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception thrown");
        }
        try{
            new TimeImpl(-1,30,0);
            fail("no -1 hours");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception thrown");
        }
        try{
            new TimeImpl(20,60,0);
            fail("no 60 minutes");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception thrown");
        }
        try{
            new TimeImpl(20,30,60);
            fail("no 60 seconds");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception thrown");
        }
    }
    
    
    @org.junit.Test
    public void optionalTestNotifications(){
        // Funzionamento delle notifiche
        final Set<String> alarms = new HashSet<>();
        final Time time = new TimeImpl(20,30,0); // 20:30:00, 8 e mezza di sera
        final Clock clock = new ClockImpl(time);
        // gli observer qui sono lambda che inseriscono stringhe in alarms
        clock.registerAlarmObserver(new TimeImpl(20,30,5), ()-> alarms.add("a") ); // "a", at 20:30:05
        clock.registerSecondsDeadlineObserver(5, ()-> alarms.add("b") ); // "b", at 20:30:05.. ossia fra 5 secondi
        clock.registerMinutesDeadlineObserver(1, ()-> alarms.add("c") ); // "c", at 20:31:00.. ossia fra 1 minuto
        clock.registerHoursDeadlineObserver(1, ()-> alarms.add("d")); // "d", at 21:31:00.. ossia fra 1 ora
        clock.tick(); // 20:30:01
        clock.tick(); // 20:30:02
        clock.tick();
        clock.tick();
        assertEquals(alarms.size(),0); // fin qui nessu allarme
        clock.tick(); // 20:30:05
        assertEquals(alarms,new HashSet<>(Arrays.asList("a","b"))); // allarme a e b scattati
        for (int i=0;i<60;i++){
            clock.tick(); // going to 20:31:05 
        }
        assertEquals(alarms,new HashSet<>(Arrays.asList("a","b","c"))); // anche c scatta
        for (int i=0;i<60*60;i++){
            clock.tick(); // going to 21:31:05 
        }
        assertEquals(alarms,new HashSet<>(Arrays.asList("a","b","c","d"))); // anche d scatta
    }
    */    
}
