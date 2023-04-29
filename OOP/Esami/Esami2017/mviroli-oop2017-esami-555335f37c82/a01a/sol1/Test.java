package a01a.sol1;

import static org.junit.Assert.*;
import java.util.*;

/**
 * Si consulti la documentazione delle interfacce fornite:
 * - InfiniteSequence<X> modella una sorgente infinita e sequenziale di dati di tipo X
 * - InfiniteSequenceOps modella un unsieme di funzionalità per creare, leggere e combinare delle InfiniteSequence 
 * 
 * Implementare InfiniteSequenceOps attraverso una classe InfiniteSequenceOpsImpl con costruttore senza argomenti,
 * in modo che passi tutti i test di cui sotto.
 *  
 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
 * della totalità del punteggio:
 * - implementazione dei test opzionali (relativi ai metodi equalsTwoByTwo e equalsOnEachElement)
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
        final InfiniteSequenceOps iso = new InfiniteSequenceOpsImpl();
        assertEquals(iso.ofValue("a").nextListOfElements(5),Arrays.asList("a","a","a","a","a"));
        assertEquals(iso.ofValue("a").nextListOfElements(1),Arrays.asList("a"));
        assertEquals(iso.ofValue("a").nextListOfElements(10),Arrays.asList("a","a","a","a","a","a","a","a","a","a"));
        assertEquals(iso.ofValue(1).nextListOfElements(10),Arrays.asList(1,1,1,1,1,1,1,1,1,1));
    }
     
	@org.junit.Test
    public void testValues() {
        // Test su sequenze cicliche
        final InfiniteSequenceOps iso = new InfiniteSequenceOpsImpl();
        // sequenza: a,b,a,b,a,b,a,b,a,b,....
        assertEquals(iso.ofValues(Arrays.asList("a","b")).nextListOfElements(5),Arrays.asList("a","b","a","b","a"));
        // sequenza: 1,2,3,1,2,3,1,2,3,1,2,3,1,2,....
        assertEquals(iso.ofValues(Arrays.asList(1,2,3)).nextListOfElements(10),Arrays.asList(1,2,3,1,2,3,1,2,3,1));
    }
	
	@org.junit.Test
    public void testAverage() {
		// Test su calcolo di medie su intervalli
		final InfiniteSequenceOps iso = new InfiniteSequenceOpsImpl();
        assertEquals(iso.averageOnInterval(iso.make(0.0,1.0,2.0,3.0), 1).nextListOfElements(3),
        			 Arrays.asList(0.0,1.0,2.0)); // media di 0, media di 1, media di 2,... 
        // sequenza 0,1,2,3,0,1,2,3,.. divisa quindi negli intervalli [0,1],[2,3],[0,1],[2,3],..
        assertEquals(iso.averageOnInterval(iso.make(0.0,1.0,2.0,3.0), 2).nextListOfElements(3),
   			 		 Arrays.asList(0.5,2.5,0.5)); // media di 0;1, media di 2;3, media di 0;1, media di 2;3,..
        // sequenza 0,1,2,3,0,1,2,3,.. divisa quindi negli intervalli [0,1,2],[3,0,1],[2,3,0],..
        assertEquals(iso.averageOnInterval(iso.make(0.0,1.0,2.0,3.0), 3).nextListOfElements(3),
			 		 Arrays.asList(1.0,4.0/3,5.0/3)); // media di 0-1-2, media di 3-0-1, media di 2-3-0,..
   	}
	
	@org.junit.Test
    public void testOneEach() {
        // Test di sequence campionate a intervalli regolari
        final InfiniteSequenceOps iso = new InfiniteSequenceOpsImpl();
        assertEquals(iso.oneEachInterval(iso.make(0,1,2,3), 1).nextListOfElements(3),
        			 Arrays.asList(0,1,2)); // prendo tutti gli elementi
        assertEquals(iso.oneEachInterval(iso.make(0,1,2,3), 2).nextListOfElements(3),
                     Arrays.asList(1,3,1)); // ne prendo uno ogni due
        // sequenza 0,1,2,3,0,1,2,3,.. divisa quindi negli intervalli [0,1,2],[3,0,1],[2,3,0],..
        assertEquals(iso.oneEachInterval(iso.make(0,1,2,3), 3).nextListOfElements(3),
                	 Arrays.asList(2,1,0)); // ne prendo uno ogni 3
        assertEquals(iso.oneEachInterval(iso.make(0,1,2,3), 4).nextListOfElements(3),
           	 Arrays.asList(3,3,3)); // ne prendo uno ogni 4
   
	}
	
	@org.junit.Test
    public void optionalTestEqualsTwoByTwo() {
		// confronto di elementi a coppie contigue, generando una sequenza di booleani
        final InfiniteSequenceOps iso = new InfiniteSequenceOpsImpl();
        assertEquals(iso.equalsTwoByTwo(iso.make(0,0,1,1,2,2,3,3)).nextListOfElements(6),
        			 Arrays.asList(true,true,true,true,true,true));
        // [0=0], [1!=7], [2=2], [3=3], [0=0], [1!=7],..
        assertEquals(iso.equalsTwoByTwo(iso.make(0,0,1,7,2,2,3,3)).nextListOfElements(6),
   			 		 Arrays.asList(true,false,true,true,true,false));
	}
	
	@org.junit.Test
    public void optionalTestEqualsOnEachElement() {
		// confronto di elementi a due a due in sequenze distinte
        final InfiniteSequenceOps iso = new InfiniteSequenceOpsImpl();
        assertEquals(iso.equalsOnEachElement(iso.make(0,1,2,3),iso.make(0,1,2,3)).nextListOfElements(6),
        			 Arrays.asList(true,true,true,true,true,true));
        assertEquals(iso.equalsOnEachElement(iso.make(0,1,2,3),iso.make(0,10,2,3)).nextListOfElements(6),
   			 Arrays.asList(true,false,true,true,true,false));
        // le due sequenze qui sotto sono identiche!
        assertEquals(iso.equalsOnEachElement(iso.make(0,1,0,1),iso.make(0,1)).nextListOfElements(6),
   			 Arrays.asList(true,true,true,true,true,true));
	}
	
	@org.junit.Test
    public void testToIterator() {
		// creazioni di un iteratore infinito da una sequenza
        final InfiniteSequenceOps iso = new InfiniteSequenceOpsImpl();
        final Iterator<String> it = iso.toIterator(iso.make("a","b"));
        assertEquals(it.next(),"a");
        assertEquals(it.next(),"b");
        assertEquals(it.next(),"a");
        assertEquals(it.next(),"b");
        assertEquals(it.next(),"a");
        assertEquals(it.next(),"b");
        assertEquals(it.next(),"a");
        assertEquals(it.next(),"b");
        assertTrue(it.hasNext());
	}
    
}
