package pr2016.a05.sol1;

import static org.junit.Assert.*;
import java.util.*;

/**
 * Si consulti la documentazione della interfaccia Elevator: modella un ascensore che accetta chiamate
 * e di conseguenza si sposta su e giù nei vari piani (floor) di un palazzo.
 * 
 * Nella parte obbligatoria dell'esercizio, realizzare una implementazione AlternateElevatorImpl con
 * costruttore senza argomenti che implementa la seguente politica:
 * - se l'ascensore sta salendo, allora continua a salire finché ci sono chiamate pendenti relativi a 
 *   a piani più in alto, e solo quando arrivato all'ultimo allora scenderà verso il basso, e viceversa
 * 
 * Nella parte opionale dell'esercizo, realizzare una semplice variante, una implementazione 
 * BasicElevatorImpl che invece implementa la politica:
 * - ogni volta si procede verso il piano più vicino verso il quale c'è stata una chiamata
 * 
 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
 * della totalità del punteggio:
 * - implementazione di BasicElevatorImpl
 * - minimizzazione di ripetizioni, tramite pattern template method
 * 
 * Si osservi con attenzione il test seguente, che assieme ai commenti delle interfacce date
 * costituisce la definizione del problema da risolvere. Si noti che i tre metodi privati qui sotto
 * definiscono test che devono valere per entrambi gli Elevator, e che sono chiamati due volte in metodi
 * pubblici, ossia passando i due tipi di Elevator. Poi seguono due test specifici.
 * 
 * Si tolga il commento al codice del test.
 */


public class Test {
    
    private void testInitialCondition(final Elevator elevator) {
        // stato initiale dell'Elevator
        assertEquals(elevator.getCurrentFloor(),0);
        assertFalse(elevator.isMoving());
        assertFalse(elevator.isMovingUp());
        assertFalse(elevator.isMovingDown());
        assertEquals(elevator.pendingCalls().size(),0);
    }
    
    public void testSimpleCall(final Elevator elevator) {
        // risposta alla chiamata di andare al piano 5
        elevator.call(5);
        assertEquals(elevator.getCurrentFloor(),0);
        assertTrue(elevator.isMoving());
        assertTrue(elevator.isMovingUp());
        assertFalse(elevator.isMovingDown());
        assertEquals(elevator.pendingCalls(),new HashSet<>(Arrays.asList(5)));
        elevator.moveToNext();
        assertEquals(elevator.getCurrentFloor(),5);
        assertFalse(elevator.isMoving());
        assertFalse(elevator.isMovingUp());
        assertFalse(elevator.isMovingDown());
        assertTrue(elevator.pendingCalls().isEmpty());
    }
    
    private void testSequenceCalls(final Elevator elevator) {
        // risposta alla chiamata di andare al piano 5, 9 e 6
        // l'elevator andrà su, prima su 5, poi 6, poi 9
        elevator.call(5);
        elevator.call(9);
        elevator.call(6);
        assertTrue(elevator.isMovingUp());
        assertEquals(elevator.pendingCalls(),new HashSet<>(Arrays.asList(5,6,9)));
        assertEquals(elevator.getCurrentFloor(),0);
        elevator.moveToNext();
        assertTrue(elevator.isMovingUp());
        assertEquals(elevator.pendingCalls(),new HashSet<>(Arrays.asList(6,9)));
        assertEquals(elevator.getCurrentFloor(),5);
        elevator.moveToNext();
        assertTrue(elevator.isMovingUp());
        assertEquals(elevator.pendingCalls(),new HashSet<>(Arrays.asList(9)));
        assertEquals(elevator.getCurrentFloor(),6);
        elevator.moveToNext();
        assertFalse(elevator.isMoving());
        assertEquals(elevator.getCurrentFloor(),9);
    }
    
    @org.junit.Test
    public void testInitialConditionAlternate() {
        testInitialCondition(new AlternateElevatorImpl());
    }
    
    @org.junit.Test
    public void testSimpleCallAlternate() {
        testSimpleCall(new AlternateElevatorImpl());
    }
    
    @org.junit.Test
    public void testSequenceCallsAlternate() {
        testSequenceCalls(new AlternateElevatorImpl());
    }
    
    @org.junit.Test
    public void optionalTestInitialConditionBasic() {
        testInitialCondition(new BasicElevatorImpl());
    }
    
    @org.junit.Test
    public void optionalTestSimpleCallBasic() {
        testSimpleCall(new BasicElevatorImpl());
    }
    
    @org.junit.Test
    public void optionalTestSequenceCallsBasic() {
        testSequenceCalls(new BasicElevatorImpl());
    }
    
    
    @org.junit.Test
    public void testUpDownCallsAlternate() {
        // risposta alla chiamata di andare al piano 5, 9 e 6
        // l'elevator andrà su, prima su 5
        // a quel punto si chiama da 0 e da 1
        // ma questo ascensore prosegue verso 6 e poi 9
        // e solo dopo torna giù
        final Elevator elevator = new AlternateElevatorImpl();
        elevator.call(5);
        elevator.call(6);
        elevator.call(9);
        assertTrue(elevator.isMovingUp());
        assertEquals(elevator.pendingCalls(),new HashSet<>(Arrays.asList(5,6,9)));
        assertEquals(elevator.getCurrentFloor(),0);
        elevator.moveToNext();
        assertTrue(elevator.isMovingUp());
        assertEquals(elevator.pendingCalls(),new HashSet<>(Arrays.asList(6,9)));
        assertEquals(elevator.getCurrentFloor(),5);
        elevator.call(0);
        elevator.call(1);
        elevator.moveToNext();
        assertTrue(elevator.isMovingUp());
        assertEquals(elevator.pendingCalls(),new HashSet<>(Arrays.asList(0,1,9)));
        assertEquals(elevator.getCurrentFloor(),6);
        elevator.moveToNext();
        assertTrue(elevator.isMovingDown());
        assertEquals(elevator.pendingCalls(),new HashSet<>(Arrays.asList(0,1)));
        assertEquals(elevator.getCurrentFloor(),9);
        elevator.moveToNext();
        assertTrue(elevator.isMovingDown());
        assertEquals(elevator.pendingCalls(),new HashSet<>(Arrays.asList(0)));
        assertEquals(elevator.getCurrentFloor(),1);
        elevator.moveToNext();
        assertFalse(elevator.isMoving());
        assertEquals(elevator.getCurrentFloor(),0);
    }

    
    @org.junit.Test
    public void testUpDownCallsBasic() {
        // risposta alla chiamata di andare al piano 5 e 9 
        // l'elevator andrà su, prima su 5
        // a quel punto si chiama da 4
        // e questo ascensore scende subito su 4
        final Elevator elevator = new BasicElevatorImpl();
        elevator.call(5);
        elevator.call(9);
        assertTrue(elevator.isMovingUp());
        assertEquals(elevator.pendingCalls(),new HashSet<>(Arrays.asList(5,9)));
        assertEquals(elevator.getCurrentFloor(),0);
        elevator.moveToNext();
        assertTrue(elevator.isMovingUp());
        assertEquals(elevator.pendingCalls(),new HashSet<>(Arrays.asList(9)));
        assertEquals(elevator.getCurrentFloor(),5);
        elevator.call(4);
        assertEquals(elevator.pendingCalls(),new HashSet<>(Arrays.asList(4,9)));
        assertTrue(elevator.isMovingDown());
        elevator.moveToNext();
        assertEquals(elevator.pendingCalls(),new HashSet<>(Arrays.asList(9)));
        assertEquals(elevator.getCurrentFloor(),4);
    }
    
    
}
