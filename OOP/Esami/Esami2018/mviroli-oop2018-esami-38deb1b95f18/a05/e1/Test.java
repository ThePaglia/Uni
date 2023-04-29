package a05.e1;

import static org.junit.Assert.*;
import org.hamcrest.CoreMatchers;
import org.hamcrest.CoreMatchers.*;

import a05.sol1.PowerIterator;
import a05.sol1.PowerIteratorsFactoryImpl;

import java.util.*;

/**
 * Si consulti la documentazione della interfaccia PowerIteratorsFactory: modella una factory per
 * creare vari oggetti di tipo PowerIterator<X>, che a sua volta rappresenta un iteratore di elementi di tipo X,
 * con metodi per accedere agli elementi già prodotti (in passato).
 *  
 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
 * della totalità del punteggio:
 * - implementazione dei test opzionali (denominati 'optionalTestXYZ') 
 * - minimizzazione di ripetizioni
 * - minimizzazione di sprechi di memoria, allocando oggetti di possibili grosse dimensioni inutilmente
 * 
 * Si osservi con attenzione il test seguente, che assieme ai commenti delle interfacce date
 * costituisce la definizione del problema da risolvere.
 * 
 * Si tolga il commento al codice del test.
 * 
 * Indicazioni di punteggio:
 * - correttezza della parte obbligatoria: 9 punti
 * - correttezza della parte opzionale: 4 punti
 * - qualità della soluzione: 4 punti
 * 
 * Si tolga il commento al codice del test.
 */


public class Test {
	
	private PowerIteratorsFactory factory;
	
	/*
	@org.junit.Before
	public void init() {
		this.factory = new PowerIteratorsFactoryImpl(); /Untitled/ classe da creare
	}
    
   
    @org.junit.Test
    public void testIncremental() {
    	PowerIterator<Integer> pi = factory.incremental(5,x->x+2); // pi produce 5,7,9,11,13,...
        assertEquals(pi.next(),Optional.of(5));
        assertEquals(pi.next(),Optional.of(7));
        assertEquals(pi.next(),Optional.of(9));
        assertEquals(pi.next(),Optional.of(11));
        assertEquals(pi.allSoFar(),Arrays.asList(5,7,9,11)); // elementi già prodotti
        for (int i = 0; i < 10; i++) {
        	pi.next(); // procedo in avanti per un po'..
        }
        assertEquals(pi.next(),Optional.of(33)); // sono arrivato a 33
    }
    
    @org.junit.Test
    public void testRandom() { // semi-automatico, si controlleranno anche le stampe a video 
    	PowerIterator<Boolean> pi = factory.randomBooleans(4); // pi produce 4 booleani random
    	boolean b1 = pi.next().get();
    	boolean b2 = pi.next().get();
    	boolean b3 = pi.next().get();
    	boolean b4 = pi.next().get();
    	System.out.println(b1+ " "+b2+ " "+b3+ " "+b4); // stampo a video.. giusto per vedere se sono proprio random..
        assertFalse(pi.next().isPresent()); // ne ho già prodotti 4, quindi il prossimo è un opzionale vuoto
        assertEquals(pi.allSoFar(),Arrays.asList(b1,b2,b3,b4)); // ho prodotto proprio b1,b2,b3,b4
    }
     
    @org.junit.Test
    public void testFromList() {  
    	PowerIterator<String> pi = factory.fromList(Arrays.asList("a","b","c")); // pi produce a,b,c
    	assertEquals(pi.next(),Optional.of("a"));
        assertEquals(pi.next(),Optional.of("b"));
        assertEquals(pi.allSoFar(),Arrays.asList("a","b")); // fin qui a,b
        assertEquals(pi.next(),Optional.of("c"));
        assertEquals(pi.allSoFar(),Arrays.asList("a","b","c")); // fin qui a,b,c
        assertFalse(pi.next().isPresent()); // non c'è più niente da produrre
    }
    
    @org.junit.Test
    public void optionalTestReversedOnList() {  
    	PowerIterator<String> pi = factory.fromList(Arrays.asList("a","b","c"));
    	assertEquals(pi.next(),Optional.of("a"));
        assertEquals(pi.next(),Optional.of("b"));
        PowerIterator<String> pi2 = pi.reversed(); //pi2 itera su b,a
        assertEquals(pi.next(),Optional.of("c")); // c viene prodotto da pi normalmente
        assertFalse(pi.next().isPresent());
        
        assertEquals(pi2.next(),Optional.of("b"));
        assertEquals(pi2.next(),Optional.of("a"));
        assertEquals(pi2.allSoFar(),Arrays.asList("b","a")); // pi2 ha prodotto b,a
        assertFalse(pi2.next().isPresent());        
    }
    
    @org.junit.Test
    public void optionalTestReversedOnIncremental() {  
    	PowerIterator<Integer> pi = factory.incremental(0,x->x+1); // 0,1,2,3,...
    	assertEquals(pi.next(),Optional.of(0));
        assertEquals(pi.next(),Optional.of(1));
        assertEquals(pi.next(),Optional.of(2));
        assertEquals(pi.next(),Optional.of(3));
        PowerIterator<Integer> pi2 = pi.reversed(); // pi2 itera su 3,2,1,0
        assertEquals(pi2.next(),Optional.of(3));
        assertEquals(pi2.next(),Optional.of(2));
        PowerIterator<Integer> pi3 = pi2.reversed(); // pi2 ha prodotto 3,2 in passato, quindi pi3 itera su 2,3
        assertEquals(pi3.next(),Optional.of(2));
        assertEquals(pi3.next(),Optional.of(3));
        assertFalse(pi3.next().isPresent());        
    }
    */
    
}
