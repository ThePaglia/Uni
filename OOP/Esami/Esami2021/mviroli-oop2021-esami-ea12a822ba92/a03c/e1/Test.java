package a03c.e1;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;

public class Test {

	/*
	 * Implementare l'interfaccia DeterministicParserFactory come indicato nel metodo
	 * initFactory qui sotto. Realizza una factory per dei DeterministicParser, ossia
	 * dei parser di vario tipo, che analizzano liste di token (stringhe) per capire
	 * se hanno la struttura ricercata. Si noti che il design di questi parser è pensato per
	 * poterli facilmente comporre sequenzialmente.
	 * 
	 * Il commento alle interfacce fornite, e i metodi di test qui sotto
	 * costituiscono la necessaria spiegazione del problema.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere
	 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
	 * punteggio: 
	 * 
	 * - implementazione dei cinque metodi nella factory (ossia, nella parte obbligatoria è sufficiente 
	 * implementarne 4 a piacimento) 
	 * 
	 * - la buona progettazione della soluzione, evitando ripetizioni
	 * 
	 * Si tolga il commento dal metodo initFactory.
	 * 
	 * Indicazioni di punteggio: 
	 * 
	 * - correttezza della parte obbligatoria: 10 punti (2+2+3+3 per i metodi di factory necessari) 
	 * 
	 * - correttezza della parte opzionale: 3 punti (metodo ulteriore)
	 * 
	 * - qualità della soluzione: 4 punti (per soluzione che minimizza le ripetizioni e che segue buone prassi di programmazione)
	 * 
	 */

	private DeterministicParserFactory factory = null;
	
	@org.junit.Before
	public void initFactory() {
		//this.factory = new DeterministicParserFactoryImpl();
	}
	
	@org.junit.Test
	public void testOneSymbol() {
		var dp = this.factory.oneSymbol("a");
		// con a,b,c si ha successo, tornando b,c
		assertEquals(Optional.of(List.of("b","c")),dp.accepts(List.of("a","b","c")));
		// con b,b,c si fallisce
		assertEquals(Optional.empty(),dp.accepts(List.of("b","b","c")));
	}
	
	@org.junit.Test
	public void testTwoSymbols() {
		var dp = this.factory.twoSymbols("a","b");
		// con a,b,c si ha successo, tornando c
		assertEquals(Optional.of(List.of("c")),dp.accepts(List.of("a","b","c")));
		// con a,a,c si fallisce
		assertEquals(Optional.empty(),dp.accepts(List.of("a","a","c")));
	}
	
	@org.junit.Test
	public void testIncreasingSequence() {
		var dp = this.factory.possiblyEmptyIncreasingSequenceOfPositiveNumbers();
		// con 10,20,30,29,7 si consuma solo 10,20,30
		assertEquals(Optional.of(List.of("29","7")),dp.accepts(List.of("10","20","30","29","7")));
		// qui non si consuma nulla, ma non si fallisce, perché è una sequenza vuota
		assertEquals(Optional.of(List.of("-5","20","30","-5","7")),dp.accepts(List.of("-5","20","30","-5","7")));
		
	}
	
	@org.junit.Test
	public void testSequence() {
		var dp = this.factory.sequence(this.factory.oneSymbol("a"), this.factory.twoSymbols("b","c"));
		// si consuma prima a e poi b,c
		assertEquals(Optional.of(List.of("d")),dp.accepts(List.of("a","b","c","d")));
		// qui fallisce
		assertEquals(Optional.empty(),dp.accepts(List.of("a","b","d","d")));
	}
	
	@org.junit.Test
	public void testSequenceWithDelimiters() {
		var dp = this.factory.sequenceOfParsersWithDelimiter("[", "]", ";", this.factory.oneSymbol("a"));
		// esempi di accettazione
		assertEquals(Optional.of(List.of("-")),dp.accepts(List.of("[","a",";","a",";","a","]","-")));
		assertEquals(Optional.of(List.of("-")),dp.accepts(List.of("[","a","]","-")));
		// start sbagliato
		assertEquals(Optional.empty(),dp.accepts(List.of("<","a",";","a",";","a","]","-")));
		// stop sbagliato
		assertEquals(Optional.empty(),dp.accepts(List.of("[","a",";","a",";","a",">","-")));
		// delimiter sbagliato
		assertEquals(Optional.empty(),dp.accepts(List.of("[","a",";","a",";;","a","]","-")));
		// element sbagliato
		assertEquals(Optional.empty(),dp.accepts(List.of("[","a",";","a","b",";","a","]","-")));
		// almeno un element ci vuole
		assertEquals(Optional.empty(),dp.accepts(List.of("[","]","-")));
	}
}
