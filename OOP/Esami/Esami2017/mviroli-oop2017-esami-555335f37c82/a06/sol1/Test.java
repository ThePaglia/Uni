package a06.sol1;

import static org.junit.Assert.*;
import java.util.*;

/**
 * Si consulti la documentazione delle interfacce fornite:
 * - Sequence<X> modella una sorgente infinita e sequenziale di dati di tipo X
 * - SequenceHelpers modella un unsieme di funzionalità per creare e combinare delle Sequence 
 * 
 * Implementare SequenceHelpers attraverso una classe SequenceHelpersImpl con costruttore senza argomenti,
 * in modo che passi tutti i test di cui sotto.
 *  
 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
 * della totalità del punteggio:
 * - implementazione dei test opzionali (relativo al metodo accumulating)
 * - la buona progettazione della soluzione, in particolare con minimizzazione di ripetizioni
 * 
 * Si osservi con attenzione il test seguente, che assieme ai commenti delle interfacce date
 * costituisce la definizione del problema da risolvere.
 * 
 * Si tolga il commento al codice del test.
 */

public class Test {
	
	@org.junit.Test
    public void testValue() {
        // Test su sequenze costituite da un solo elemento
        final SequenceHelpers iso = new SequenceHelpersImpl();
        assertEquals(iso.of("a").nextListOfElements(5),Arrays.asList("a","a","a","a","a"));
        assertEquals(iso.of("a").nextListOfElements(1),Arrays.asList("a"));
        assertEquals(iso.of("a").nextListOfElements(10),Arrays.asList("a","a","a","a","a","a","a","a","a","a"));
        assertEquals(iso.of(1).nextListOfElements(10),Arrays.asList(1,1,1,1,1,1,1,1,1,1));
    }
     
	@org.junit.Test
    public void testCyclic() {
        // Test su sequenze cicliche
        final SequenceHelpers iso = new SequenceHelpersImpl();
        // sequenza: a,b,a,b,a,b,a,b,a,b,....
        assertEquals(iso.cyclic(Arrays.asList("a","b")).nextListOfElements(5),Arrays.asList("a","b","a","b","a"));
        // sequenza: 1,2,3,1,2,3,1,2,3,1,2,3,1,2,....
        assertEquals(iso.cyclic(Arrays.asList(1,2,3)).nextListOfElements(10),Arrays.asList(1,2,3,1,2,3,1,2,3,1));
    }
	
	@org.junit.Test
    public void testIncrementing() {
		// Test su costruzione sequenza di incrementi
		final SequenceHelpers iso = new SequenceHelpersImpl();
        assertEquals(iso.incrementing(1, 2).nextListOfElements(5), Arrays.asList(1,3,5,7,9));    	
        assertEquals(iso.incrementing(0, -3).nextListOfElements(4), Arrays.asList(0,-3,-6,-9));    	
    }
	
	
	@org.junit.Test
    public void testZip() {
		// Test su costruzione di sequenze con zipping
		final SequenceHelpers iso = new SequenceHelpersImpl();
        assertEquals(iso.zip(iso.cyclic(Arrays.asList("a","b","c"))).nextListOfElements(4), 
        		Arrays.asList(new Pair<>("a",0),new Pair<>("b",1), new Pair<>("c",2), new Pair<>("a",3)));
    }

	@org.junit.Test
    public void optionalTestAccumulating() {
		// Test su costruzione sequenze per accumulo
		final SequenceHelpers iso = new SequenceHelpersImpl();
        assertEquals(iso.accumulating(iso.of("1"), (x,y)->x+y).nextListOfElements(4), 
        		Arrays.asList("1","11","111","1111"));
        assertEquals(iso.accumulating(iso.cyclic(Arrays.asList(1,2,3)), (x,y)->x*y).nextListOfElements(6), 
        		Arrays.asList(1,1*2,1*2*3,1*2*3*1,1*2*3*1*2,1*2*3*1*2*3));
    }
	    
}
