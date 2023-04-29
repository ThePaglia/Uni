package a03b.e1;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Test {

	/*
	 * Implementare l'interfaccia EquivalenceFactory come indicato nel metodo initFactory
	 * qui sotto. Realizza una factory per delle Equivalence, ossia degli oggetti che implementano
	 * delle relazioni binarie di equivalenza fra valori. Si ricordi che una relazione di equivalenza definisce
	 * in pratica una partizione sui valori, definendo dei sottogruppi (che non si intersecano): gli elementi
	 * di ogni sottogruppo sono considerati "equivalenti fra loro", gli altri no.
	 * 
	 * Il commento alle interfacce fornite, e i metodi di test qui sotto
	 * costituiscono la necessaria spiegazione del problema.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere
	 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
	 * punteggio: 
	 * 
	 * - implementazione dei test chiamati optionalTestXYZ (relativi a
	 * Equivalence.smallerThan e a EquivalenceFactory.fromFunction) 
	 * 
	 * - la buona progettazione della soluzione, in particolare utilizzando codice succinto e senza ripetizioni
	 * 
	 * Si tolga il commento dal metodo initFactory.
	 * 
	 * Indicazioni di punteggio: 
	 * 
	 * - correttezza della parte obbligatoria: 10 punti 
	 * 
	 * - correttezza della parte opzionale: 4 punti 
	 * 
	 * - qualità della soluzione: 3 punti
	 * 
	 */

	private EquivalenceFactory factory = null;

	@org.junit.Before
	public void initFactory() {
		// this.factory = new EquivalenceFactoryImpl();
	}
	
	@org.junit.Test
	public void testFromPartition() {
		// equivalenza su [0..9], "siamo equivalenti se abbiamo lo stesso resto divendendoci per 3" 
		Set<Set<Integer>> partition = Set.of(
				Set.of(0,3,6,9), // primo equivalence set
				Set.of(1,4,7),	// secondo equivalence set
				Set.of(2,5,8)	// terzo equivalence set
		);
		Equivalence<Integer> equivalence = this.factory.fromPartition(partition);
		
		assertEquals(partition, equivalence.partition());
		
		// set dei valori
		assertEquals(Set.of(0,1,2,3,4,5,6,7,8,9), equivalence.domain());
		
		assertEquals(Set.of(0,3,6,9), equivalence.equivalenceSet(6)); // chi è equivalente a 6?
		assertEquals(Set.of(1,4,7), equivalence.equivalenceSet(1)); // chi è equivalente a 1?
		
		assertTrue(equivalence.areEquivalent(0, 6)); // 0 e 6 sono equivalenti?
		assertTrue(equivalence.areEquivalent(1, 4)); // ..
		assertTrue(equivalence.areEquivalent(5, 8));
		
		assertFalse(equivalence.areEquivalent(5, 7));
		assertFalse(equivalence.areEquivalent(0, 1));
	}
	
	@org.junit.Test
	public void testFromPredicate() {
		Set<String> domain = Set.of("","a","b","c","ab","ac","bc","abc");
		// siamo equivalenti se abbiamo la stessa lunghezza 
		Equivalence<String> equivalence = this.factory.fromPredicate(domain, (s1,s2)->s1.length()==s2.length());
		
		assertEquals(domain, equivalence.domain());
		
		// quattro equivalence set
		assertEquals(
				Set.of(
						Set.of(""), Set.of("a","b","c"),Set.of("ab","ac","bc"),Set.of("abc")
				),equivalence.partition());
		
				
		assertEquals(Set.of("a","b","c"), equivalence.equivalenceSet("a")); // chi è equivalente ad "a"?
		assertEquals(Set.of("abc"), equivalence.equivalenceSet("abc")); // ...
		
		assertTrue(equivalence.areEquivalent("a", "c")); // "a" e "c" sono equivalenti?
		assertTrue(equivalence.areEquivalent("ab", "ac")); // ..
		
		assertFalse(equivalence.areEquivalent("ab", "abc"));
		assertFalse(equivalence.areEquivalent("", "a"));
	}
	
	@org.junit.Test
	public void testFromPairs() {
		// siamo equivalenti se come coppia siamo in questo set...
		Set<Pair<String,String>> pairs = Set.of(
				new Pair<>("a","a"),
				new Pair<>("a","b"),
				new Pair<>("b","a"),
				new Pair<>("b","b"),
				new Pair<>("aa","bb"),
				new Pair<>("bb","aa"),
				new Pair<>("aa","aa"),
				new Pair<>("bb","bb"));
		Equivalence<String> equivalence = this.factory.fromPairs(pairs);
		
		// dominio
		assertEquals(Set.of("a","b","aa","bb"), equivalence.domain());
		
		// due set di equivalenza
		assertEquals(Set.of(Set.of("aa","bb"), Set.of("a","b")),equivalence.partition());
				
		assertEquals(Set.of("a","b"), equivalence.equivalenceSet("a"));
		assertEquals(Set.of("bb","aa"), equivalence.equivalenceSet("bb"));
		
		assertTrue(equivalence.areEquivalent("a", "b"));
		assertTrue(equivalence.areEquivalent("aa", "bb"));
		
		assertFalse(equivalence.areEquivalent("a", "aa"));
		assertFalse(equivalence.areEquivalent("aa", "ab"));
	}
	
	@org.junit.Test
	public void optionalTestFromFunction() {
		Set<String> domain = Set.of("","a","b","c","ab","ac","bc","abc");
		// siamo equivalenti se abbiamo la stessa lunghezza...
		// test equivalente a testFromPredicate qui sopra
		Equivalence<String> equivalence = this.factory.fromFunction(domain, s->s.length());
		
		assertEquals(domain, equivalence.domain());
		
		assertEquals(
				Set.of(
						Set.of(""), Set.of("a","b","c"),Set.of("ab","ac","bc"),Set.of("abc")
				),equivalence.partition());
				
		assertEquals(Set.of("a","b","c"), equivalence.equivalenceSet("a"));
		assertEquals(Set.of("abc"), equivalence.equivalenceSet("abc"));
		
		assertTrue(equivalence.areEquivalent("a", "c"));
		assertTrue(equivalence.areEquivalent("ab", "ac"));
		
		assertFalse(equivalence.areEquivalent("ab", "abc"));
		assertFalse(equivalence.areEquivalent("", "a"));
	}
	
	
	@org.junit.Test
	public void optionalTestSmaller() {
		Set<Set<Integer>> partition = Set.of(
				Set.of(0,3,6,9),
				Set.of(1,4,7),
				Set.of(2,5,8)
		);
		Equivalence<Integer> equivalence = this.factory.fromPartition(partition);
		
		Set<Set<Integer>> partition2 = Set.of(
				Set.of(0,3),
				Set.of(6,9),
				Set.of(1,4),
				Set.of(7),
				Set.of(2,5),
				Set.of(8)
		);
		Equivalence<Integer> equivalence2 = this.factory.fromPartition(partition2);
		
		Set<Set<Integer>> partition3 = Set.of(
				Set.of(0,3,7),
				Set.of(6,9),
				Set.of(1,4),
				Set.of(2,5),
				Set.of(8)
		);
		Equivalence<Integer> equivalence3 = this.factory.fromPartition(partition3);
		// eq2 è ottenuta "spezzando i set" di eq, e quindi è più fine, ossia "smaller", eccetera per gli altri
		assertTrue(equivalence2.smallerThan(equivalence));
		assertTrue(equivalence2.smallerThan(equivalence3));
		
		assertFalse(equivalence3.smallerThan(equivalence2));
		assertFalse(equivalence3.smallerThan(equivalence2));
		assertFalse(equivalence.smallerThan(equivalence3));
	}
}
