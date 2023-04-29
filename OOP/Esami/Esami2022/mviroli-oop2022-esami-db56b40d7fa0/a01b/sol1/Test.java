package a01b.sol1;

import static org.junit.Assert.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Si consulti la documentazione della interfaccia FlattenerFactory, che modella
 * una factory per Flattener, che a sua volta modella un trasformatore da liste di liste a 
 * liste, che funziona ricombinando le inner list in input producendo (alla fine, o mano a mano) 
 * elementi in uscita.
 * 
 * Il commento alle interfacce fornite, e i metodi di test qui sotto costituiscono la necessaria 
 * spiegazione del problema.
 * 
 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono 
 * comunque al raggiungimento della totalità del punteggio: 
 * - implementazione del quinto metodo della factory (ossia, a scelta se ne realizzino 4, il quinto è opzionale)
 * - concisione del codice e rimozione di tutte le ripetizioni con eventuale uso appropriato di pattern
 *
 * Si tolga il commento dal metodo init.
 * 
 * Indicazioni di punteggio:
 * - correttezza della parte obbligatoria: 10 punti
 * - correttezza della parte opzionale: 3 punti  
 * - qualità della soluzione (eliminando ripetizioni di codice): 4 punti
 * - errori di programmazione comportano decurtamento del punteggio complessivo
 */

public class Test {

	private FlattenerFactory factory;

	@org.junit.Before
	public void init() {
		this.factory = new FlattenerFactoryImpl();
	}

	@org.junit.Test
	public void testSumEach() {
		Flattener<Integer,Integer> f = this.factory.sumEach();
		// ogni inner list produce in uscita la somma dei suoi elementi, ossia 1+2+3+4, 0, 20, 10+10+10, 0
		assertEquals(List.of(10, 0, 20, 30, 0),
			f.flatten(List.of(List.of(1,2,3,4), List.of(), List.of(20), List.of(10,10,10), List.of(0))));
	}

	@org.junit.Test
	public void testFlattenAll() {
		Flattener<String,String> f = this.factory.flattenAll();
		// tutte le inner list vengono appese l'un l'altra producendo la lista in output
		assertEquals(List.of("a","b","c","d","e"),
			f.flatten(List.of(List.of("a","b"), List.of(), List.of("c"), List.of("d","e"))));
	}

	@org.junit.Test
	public void testConcatPairs() {
		Flattener<String,String> f = this.factory.concatPairs();
		// le inner list vengono prese a coppie: per ogni coppia si uniscono tutte le loro stringhe
		// se c'è una inner list finale la si tratta da sola
		assertEquals(List.of("abc","cdef","gh"),
			f.flatten(List.of(List.of("a","b"), List.of("c"), List.of("c","d","e"),List.of("f"),List.of("g","h"))));
		assertEquals(List.of("abc","cdef"),
			f.flatten(List.of(List.of("a","b"), List.of("c"), List.of("c","d"),List.of("e", "f"))));
	}

	@org.junit.Test
	public void testEach() {
		Flattener<String,String> f = this.factory.each(l -> l.stream().collect(Collectors.joining()));
		// ogni inner list produce la concatenazione dei suoi elementi -- che sono stringhe
		assertEquals(List.of("ab","c","cde","f","g"),
			f.flatten(List.of(List.of("a","b"), List.of("c"), List.of("c","d","e"),List.of("f"),List.of("g"))));
		Flattener<String,Integer> f2 = this.factory.each(l -> l.size());
		// ogni inner list produce la sua lunghezza
		assertEquals(List.of(2,1,3,1,1),
			f2.flatten(List.of(List.of("a","b"), List.of("c"), List.of("c","d","e"),List.of("f"),List.of("g"))));
	}

	@org.junit.Test
	public void testSumVectors() {
		Flattener<Integer,Integer> f = this.factory.sumVectors();
		// l'output è prodotto a fine iterazione: le inner list sono considerate come vettori (nel senso dell'algebra) da sommare
		assertEquals(List.of(1111, 2222, 3333),
			f.flatten(List.of(List.of(1,2,3), List.of(10,20,30), List.of(100,200,300), List.of(1000,2000,3000))));
	}
}
