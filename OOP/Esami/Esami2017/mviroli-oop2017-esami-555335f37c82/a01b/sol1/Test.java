package a01b.sol1;

import static org.junit.Assert.*;
import java.util.*;

/**
 * Si consulti la documentazione delle interfacce/classe fornite:
 * - Event<X> modella un evento temporale con valore X
 * - EventImpl<X> è una sua implementazione banale ed utilizzabile
 * - Trace<X> modella una sorgente finita temporale di eventi di tipo X
 * - TraceFactory modella una factory per vari tipi di trace 
 * 
 * Implementare TraceFactory attraverso una classe TraceFactoryImpl con costruttore senza argomenti,
 * in modo che passi tutti i test di cui sotto.
 *  
 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
 * della totalità del punteggio:
 * - implementazione dei test opzionali (relativi al metodo Trace.dropValues)
 * - la buona progettazione della soluzione, in particolare con minimizzazione di ripetizioni
 * - la scelta di una implementazione "lazy" per i Trace (ipotizzando cioè che nelle factory, 
 *   size possa essere molto grande, e quindi non si debbano costruire mai strutture dati intermedie)
 * 
 * Si osservi con attenzione il test seguente, che assieme ai commenti delle interfacce date
 * costituisce la definizione del problema da risolvere.
 * 
 * Si tolga il commento al codice del test.
 */

public class Test {
	
	// funzionalità a fini interni, che estrae tempi e valori da un trace
	private <X> Pair<List<Integer>,List<X>> traceToPairs(Trace<X> t){
		Iterator<Event<X>> it = t.iterator();
		List<Integer> times = new ArrayList<>();
		List<X> values = new ArrayList<>();
		while (it.hasNext()) {
			Event<X> e = it.next();
			times.add(e.getTime());
			values.add(e.getValue());
		}
		return new Pair<>(times,values);
	} 
	
	@org.junit.Test
    public void testNextEvent() {
        // Test su creazione discrete
        final Trace<String> t = new TraceFactoryImpl().discrete(()->"a", 2); // 0:a ; 1:a
        Optional<Event<String>> e;
        e = t.nextEvent();
        assertTrue(e.isPresent() && e.get().getTime()==0 && e.get().getValue().equals("a"));
        e = t.nextEvent();
        assertTrue(e.isPresent() && e.get().getTime()==1 && e.get().getValue().equals("a"));
        e = t.nextEvent();
        assertFalse(e.isPresent());
    }	

	@org.junit.Test
    public void testDiscrete() {
        // Test su creazione discrete
        final Trace<String> t = new TraceFactoryImpl().discrete(()->"a", 5); // 0:a ; 1:a ; .. 4;a
        final Pair<List<Integer>,List<String>> p = traceToPairs(t);
        assertEquals(p.getFst(),Arrays.asList(0,1,2,3,4));
        assertEquals(p.getSnd(),Arrays.asList("a","a","a","a","a"));
    }
	
	@org.junit.Test
    public void testConstant() {
        // Test su creazione constant
        final Trace<String> t = new TraceFactoryImpl().constant(()->2, "a", 5); // 0:a ; 2:a ; 4:a ; .. ; 8:a
        final Pair<List<Integer>,List<String>> p = traceToPairs(t);
        assertEquals(p.getFst(),Arrays.asList(0,2,4,6,8));
        assertEquals(p.getSnd(),Arrays.asList("a","a","a","a","a"));
    }  
	
	@org.junit.Test
    public void testFromSuppliers() {
        // Test su creazione da suppliers
        final Trace<String> t = new TraceFactoryImpl().fromSuppliers(()->2, ()->Math.random()>0.5?"a":"b", 5);
        // esempio: 0:a ; 2:b ; 4:b ; 6:b ; 8:a
        final Pair<List<Integer>,List<String>> p = traceToPairs(t);
        assertEquals(p.getFst(),Arrays.asList(0,2,4,6,8));
        assertEquals(p.getSnd().size(),5);
        assertTrue(p.getSnd().get(0).equals("a") || p.getSnd().get(0).equals("b"));
        assertTrue(p.getSnd().get(1).equals("a") || p.getSnd().get(1).equals("b"));
        assertTrue(p.getSnd().get(4).equals("a") || p.getSnd().get(4).equals("b"));
    }
	
	@org.junit.Test
    public void testSkip() {
        // Test metodo skip
		final Trace<String> t = new TraceFactoryImpl().fromSuppliers(()->2, ()->"a", 5);
		// t rappresenta 0:a ; 2:b ; 4:b ; 6:b ; 8:a
        t.skipAfter(2);
		// t rappresenta 4:b ; 6:b ; 8:a
        final Pair<List<Integer>,List<String>> p = traceToPairs(t);
        assertEquals(p.getFst(),Arrays.asList(4,6,8));
        assertEquals(p.getSnd(),Arrays.asList("a","a","a"));
    }
	
	@org.junit.Test
    public void testSkipOver() {
        // Test metodo skip
		final Trace<String> t = new TraceFactoryImpl().fromSuppliers(()->2, ()->"a", 5);
		// t rappresenta 0:a ; 2:b ; 4:b ; 6:b ; 8:a
        t.skipAfter(10);
		// t è vuoto
        final Pair<List<Integer>,List<String>> p = traceToPairs(t);
        assertEquals(p.getFst().size(),0);
        assertEquals(p.getSnd().size(),0);
    }
	
	@org.junit.Test
    public void testCombine() {
        // Test metodo combine
		final Trace<String> t = new TraceFactoryImpl().fromSuppliers(()->2, ()->"a", 3); //t è 0:a,2:a,4:a
		t.nextEvent(); // ora t è 2:a,4:a
		final Trace<String> t2 = new TraceFactoryImpl().fromSuppliers(()->5, ()->"b", 2); //t2: 0:b,5:b
		final Trace<String> t3 = t2.combineWith(t); // 0:b, 2:a, 4:a, 5:b 
        final Pair<List<Integer>,List<String>> p = traceToPairs(t3);
        assertEquals(p.getFst(),Arrays.asList(0,2,4,5));
        assertEquals(p.getSnd(),Arrays.asList("b","a","a","b"));
    }
	
	@org.junit.Test
    public void optionalTestDropValues() {
        // Test metodo combine
		final Trace<String> t = new TraceFactoryImpl().fromSuppliers(()->2, ()->"a", 3); //t è 0:a,2:a,4:a
		t.nextEvent(); // ora t è 2:a,4:a
		final Trace<String> t2 = new TraceFactoryImpl().fromSuppliers(()->5, ()->"b", 2); //t2: 0:b,5:b
		final Trace<String> t3 = t2.combineWith(t); // 0:b, 2:a, 4:a, 5:b 
        final Trace<String> t4 = t3.dropValues("a"); // 0:b, 5:b
        final Pair<List<Integer>,List<String>> p = traceToPairs(t4);
        assertEquals(p.getFst(),Arrays.asList(0,5));
        assertEquals(p.getSnd(),Arrays.asList("b","b"));
    }
}
