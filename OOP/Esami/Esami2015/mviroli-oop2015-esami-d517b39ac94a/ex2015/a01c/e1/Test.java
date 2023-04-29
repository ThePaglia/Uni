package ex2015.a01c.e1;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.*;

public class Test {
	
	/*
	 * Implementare l'interfaccia MultiMap tramite una classe MultiMapImpl con costruttore senza argomenti.
	 * Modella un tipo di dato multi-mappa, ossia una mappa chiave->valore, in cui una chiave può comparire 
	 * più volte, ossia può avere più valori associati (senza ordine).
	 * In più implementare anche MapFactory tramite una classe MapFactoryImpl con costruttore senza argomenti.
	 * Questa modella una factory per generare due multi-mappe, una vuota, e una non modificabile a partire
	 * da una modificabile -- ossia deve lanciare una UnsupportedOperationException se uno tenta di aggiungere
	 * elementi.
	 * Il commento al codice fornito, e i metodi di test qui sotto costituiscono la necessaria spiegazione del 
	 * problema.
	 * I test il cui nome comincia con 'optional' sono considerati opzionali ai fini della possibilità di correggere
	 * l'esercizio -- anche se concorrono alla definizione del punteggio.
	 * Si tolga il commento ai test..
	 */

	/*
	@org.junit.Test
	public void testKeys() {
		// adding few associations and testing if keys() works
		MultiMap<String,Integer> mmap = new MultiMapImpl<>();
		assertTrue(mmap.keys().isEmpty());
		mmap.add("a", 10);
		mmap.add("a", 20);
		mmap.add("a", 30);
		mmap.add("b", 10);
		mmap.add("b", 11);
		assertEquals(mmap.keys().size(),2);
		assertThat(mmap.keys(),hasItems("a","b"));
	}
	
	@org.junit.Test
	public void testGet() {
		// adding few associations and testing if gets() works
		MultiMap<String,Integer> mmap = new MultiMapImpl<>();
		mmap.add("a", 10);
		mmap.add("a", 20);
		mmap.add("a", 30);
		mmap.add("b", 10);
		mmap.add("b", 11);
		assertEquals(mmap.get("a").size(),3);
		assertThat(mmap.get("a"),hasItems(10,20,30));
		assertEquals(mmap.get("b").size(),2);
		assertThat(mmap.get("b"),hasItems(10,11));
		// adding twice an association has no effect
		mmap.add("b", 11);
		assertEquals(mmap.get("b").size(),2);
		assertThat(mmap.get("b"),hasItems(10,11));
	}
	
	@SuppressWarnings("unchecked")
	@org.junit.Test
	public void testEntries() {
		// adding few associations and testing if entrySet() works
		MultiMap<String,Integer> mmap = new MultiMapImpl<>();
		mmap.add("a", 10);
		mmap.add("a", 20);
		mmap.add("a", 30);
		mmap.add("b", 10);
		mmap.add("b", 11);
		assertEquals(mmap.entrySet().size(),5);
		assertThat(mmap.entrySet(),hasItems(
				new Pair<>("a",10),
				new Pair<>("a",20),
				new Pair<>("a",30),
				new Pair<>("b",10),
				new Pair<>("b",11)));		
	}
	
	@org.junit.Test
	public void testGetIsDefended() {
		// The set provided by get should be a defensive copy!!
		MultiMap<String,Integer> mmap = new MultiMapImpl<>();
		mmap.add("a", 10);
		mmap.add("a", 20);
		mmap.add("a", 30);
		mmap.add("b", 10);
		mmap.add("b", 11);
		Set<Integer> set = mmap.get("a");
		assertEquals(set.size(),3);
		assertThat(set,hasItems(10,20,30));
		// adding 40 to set has no effect on mmap
		set.add(40);
		assertEquals(mmap.entrySet().size(),5);
	}
	
	@org.junit.Test
	public void optionalTestEmpty() {
		// Simple test of empty on the factory
		MapFactory fact = new MapFactoryImpl();
		MultiMap<String,Integer> mmap = fact.empty();
		assertEquals(mmap.entrySet().size(),0);
	}
	

	
	@org.junit.Test
	public void optionalTestUnmodifiable() {
		// Testing cloning and unmodifiability of the factory method
		MultiMap<String,Integer> mmap = new MultiMapImpl<>();
		mmap.add("a", 10);
		mmap.add("a", 20);
		mmap.add("a", 30);
		mmap.add("b", 10);
		mmap.add("b", 11);
		MapFactory fact = new MapFactoryImpl();
		// mmap2 as an unmodificable clone of mmap
		MultiMap<String,Integer> mmap2 = fact.unmodifiable(mmap);
		// changing mmap has no effect on mmap2
		mmap.add("b", 12);
		assertEquals(mmap.entrySet().size(),6);
		assertEquals(mmap2.entrySet().size(),5);
		// trying to add something to mmap2 throws an exception
		try{
			mmap2.add("b", 13);
			fail("adding to an unmodifiable-multi-map should raise an exception!");
		} catch (UnsupportedOperationException e){}
		  catch (Exception e){
			  fail("wrong exception thrown");
		  }
		// Obviously, the set you obtain by get() is again a defensive copy 
		Set<Integer> s = mmap2.get("a");
		assertEquals(s.size(),3);
		s.add(100);
		Set<Integer> s2 = mmap2.get("a");
		assertEquals(s2.size(),3);
	}
	*/
}
