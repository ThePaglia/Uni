package a03a.e1;

import static org.junit.Assert.*;
import java.util.*;

/**
 * Si consulti la documentazione della interfaccia ParserFactory, che modella
 * una factory per Parser, che a sua volta modella un riconoscitore di certe sequenze (parser)
 * di elementi estratte da un iteratore.
 * 
 * Il commento alle interfacce fornite, e i metodi di test qui sotto costituiscono la necessaria 
 * spiegazione del problema.
 * 
 * Sono considerati opzionali ai fini della possibilità di correggere
 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
 * punteggio: 
 * - implementazione del quinto metodo factory (ossia, a scelta se ne realizzino 4,
 *   ma considerando il primo metodo fromFinitePossibilities() come obbligatorio)
 * - elementi di qualità come concisione del codice, rimozione di ripetizioni, uso parsimonioso della memoria
 *
 * Si tolga il commento dal metodo init.
 * 
 * Indicazioni di punteggio:
 * - correttezza della parte obbligatoria: 10 punti
 * - correttezza della parte opzionale: 4 punti  
 * - qualità della soluzione: 3 punti
 * - bug di programmazione, o violazione di regole base di programmazione Java, comportano decurtamento del punteggio 
 *   complessivo, anche in caso di bonus
 */


public class Test {

	private ParserFactory factory;

	@org.junit.Before
	public void init() {
		this.factory = new ParserFactoryImpl();
	}

	@org.junit.Test
	public void testFinitePossibilities() {
		// un parser che accetta solo (10,20,30,50) o (10,20,50) o (50)
		var parser = this.factory.fromFinitePossibilities(Set.of(
			List.of(10,20,30,50),
			List.of(10,20,50),
			List.of(50)
		));
		assertTrue(parser.accept(List.of(10,20,30,50).iterator()));
		assertTrue(parser.accept(List.of(10,20,50).iterator()));
		assertTrue(parser.accept(List.of(50).iterator()));
		assertFalse(parser.accept(List.of(10,20).iterator()));
		assertFalse(parser.accept(List.of(10,20,30,50,60).iterator()));
		assertFalse(parser.accept(List.<Integer>of().iterator()));
	}

	@org.junit.Test
	public void testRecursive() {
		// un parser che se riceve 1 poi accetta 2,3,4, se riceve 2 poi accetta 3,4; e nient'altro
		var parser = this.factory.recursive(
				x -> x==1 ? Optional.of(this.factory.fromFinitePossibilities(Set.of(List.of(2,3,4))))
					: x==2 ? Optional.of(this.factory.fromFinitePossibilities(Set.of(List.of(3,4))))
					: Optional.empty(), false);
		assertTrue(parser.accept(List.of(1,2,3,4).iterator()));
		assertTrue(parser.accept(List.of(2,3,4).iterator()));
		assertFalse(parser.accept(List.of(1).iterator()));
		assertFalse(parser.accept(List.of(1,2).iterator()));
		assertFalse(parser.accept(List.<Integer>of().iterator()));
	}

	@org.junit.Test
	public void testGraph() {
		// un parser che vuole un 1, dopo un 1 ancora un 1 o un2, dopo un 2 vuole un 2 o un 3, e quindi il 3 deve essere l'ultimo
		var parser = this.factory.fromGraph(
			1,
			Set.of(
				new Pair<>(1,1),
				new Pair<>(1,2),
				new Pair<>(2,2),
				new Pair<>(2,3)),
			Set.of(3));
		assertTrue(parser.accept(List.of(1,1,1,2,2,2,3).iterator()));
		assertTrue(parser.accept(List.of(2,2,2,2,3).iterator()));
		assertFalse(parser.accept(List.of(1,1,1,3).iterator()));
		assertFalse(parser.accept(List.of(1,1,1,2,2,3,3).iterator()));
		assertFalse(parser.accept(List.of(1,2).iterator()));
		assertFalse(parser.accept(List.<Integer>of().iterator()));
	}

	@org.junit.Test
	public void testIteration() {
		// un parser che vuole 0, e poi l'incremento del precedente (1,2,...) fino a raggiungere il 5 escluso
		var parser = this.factory.fromIteration(0, i -> Optional.of(i+1).filter(j -> j<5));
		assertTrue(parser.accept(List.of(0,1,2,3,4).iterator()));
		assertFalse(parser.accept(List.of(0,1,2,3,4,5).iterator()));
		assertFalse(parser.accept(List.of(0,1,2,3).iterator()));
	}

	@org.junit.Test
	public void testWithInitial() {
		// un parser che riceve 1, e poi si comporta come il parser di testFinitePossibilities
		var parser = this.factory.fromParserWithInitial(1,this.factory.fromFinitePossibilities(Set.of(
			List.of(10,20,30,50),
			List.of(10,20,50),
			List.of(50)
		)));
		assertTrue(parser.accept(List.of(1,10,20,30,50).iterator()));
		assertTrue(parser.accept(List.of(1,10,20,50).iterator()));
		assertTrue(parser.accept(List.of(1, 50).iterator()));
		assertFalse(parser.accept(List.of(10,20).iterator()));
		assertFalse(parser.accept(List.of(1,1,10,20,50).iterator()));
		assertFalse(parser.accept(List.of(10,20,30,50).iterator()));
		assertFalse(parser.accept(List.<Integer>of().iterator()));
	}
}
