package ex2015.a06.sol1;

import static org.junit.Assert.*;

import java.util.*;

/**
 * Si consulti la documentazione delle interfacce Countdown e PowerCountdown fornite, che modellano dei contatori all'indietro.
 * Implementare l'interfaccia Countdown con una classe StandardCountdown, con costruttore che accetta il valore iniziale del 
 * conteggio: tale contatore si decrementi sempre di 1 fino a raggiungere il valore 0.
 * Implementare l'interfaccia PowerCountdown con una classe PowerCountdownImpl, con costruttore che accetta il valore iniziale 
 * e finale del conteggio (quest'ultimo sia minore): tale contatore si decrementa inizialmente di 1 alla volta, e ad ogni 
 * chiamata di speedup() si decrementa poi di un valore crescente (2,3,4,..); al reset si ritorni al valore iniziale di 
 * conteggio e a decremento 1.
 * 
 * Considerato opzionale ai fini della possibilità di correggere l'esercizio, si utilizzi -- anche se concorre al raggiungimento 
 * del punteggio massimo -- il pattern Template Method per fattorizzare le due classi e eliminare ogni forma di ripetizione 
 * possibile nel codice.
 * 
 * Si osservi con attenzione il test seguente, che assieme ai commenti delle interfacce date
 * costituisce la definizione del problema da risolvere.
 * 
 * Si tolga il commento al codice del test.
 */

public class Test {

    // Testing StandardCountdown: going from 3 to 0
    @org.junit.Test
    public void testStandard() {
        Countdown countdown = new StandardCountdown(3);
        assertEquals(countdown.getValue(),3);
        assertFalse(countdown.isOver());
        countdown.decrease();        
        assertEquals(countdown.getValue(),2);
        assertFalse(countdown.isOver());
        countdown.decrease();        
        assertEquals(countdown.getValue(),1);
        assertFalse(countdown.isOver());
        countdown.decrease();        
        assertEquals(countdown.getValue(),0);
        assertTrue(countdown.isOver());
    }
    
    // Testing StandardCountdown: going from 3 to 0, and trying again
    @org.junit.Test
    public void testStandardException() {
        Countdown countdown = new StandardCountdown(3);
        countdown.decrease();        
        countdown.decrease();        
        countdown.decrease();        
        try{
            countdown.decrease();
            fail("Cannot decrease anymore");
        } catch (IllegalStateException e){
            assertTrue(countdown.isOver());
        } catch (Exception e){
            fail("Wrong exception thrown");
        }
    }
    
    // Testing PowerCountdownImpl: going from 4 to 2
    @org.junit.Test
    public void testPower1() {
        PowerCountdown pcountdown = new PowerCountdownImpl(4,2);
        assertEquals(pcountdown.getValue(),4);
        assertFalse(pcountdown.isOver());
        pcountdown.decrease();        
        assertEquals(pcountdown.getValue(),3);
        assertFalse(pcountdown.isOver());
        pcountdown.decrease();        
        assertEquals(pcountdown.getValue(),2);
        assertTrue(pcountdown.isOver());
    }
    
    // Testing PowerCountdownImpl: going from 4 to 2, and trying again
    @org.junit.Test
    public void testPower1Exception() {
        PowerCountdown pcountdown = new PowerCountdownImpl(4,2);
        pcountdown.decrease();        
        pcountdown.decrease();        
        try{
            pcountdown.decrease();
            fail("Cannot decrease anymore");
        } catch (IllegalStateException e){
            assertTrue(pcountdown.isOver());
        } catch (Exception e){
            fail("Wrong exception thrown");
        }
    }
    
    // Testing PowerCountdownImpl: going from 10 to 0, at 8 increasing speed to 3, at 2 resetting
    @org.junit.Test
    public void testPower2() {
        PowerCountdown pcountdown = new PowerCountdownImpl(10,0);
        // speed is now 1;
        assertEquals(pcountdown.getValue(),10);
        assertFalse(pcountdown.isOver());
        pcountdown.decrease();        
        assertEquals(pcountdown.getValue(),9);
        assertFalse(pcountdown.isOver());
        pcountdown.decrease();        
        assertEquals(pcountdown.getValue(),8);
        assertFalse(pcountdown.isOver());
        pcountdown.speedup();
        pcountdown.speedup(); // speed is now 3;
        pcountdown.decrease();        
        assertEquals(pcountdown.getValue(),5);
        assertFalse(pcountdown.isOver());
        pcountdown.decrease();        
        assertEquals(pcountdown.getValue(),2);
        assertFalse(pcountdown.isOver());
        pcountdown.reset(); // now at value 10, and speed back to 1
        assertEquals(pcountdown.getValue(),10);
        assertFalse(pcountdown.isOver());
        pcountdown.decrease();        
        assertEquals(pcountdown.getValue(),9);
        assertFalse(pcountdown.isOver());
    }
}
