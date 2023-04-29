package a02b.sol1;

import static org.junit.Assert.*;
import java.util.*;

/**
 * Si consulti la documentazione della interfaccia PatternExtractorFactory, che modella
 * una factory per PatternExtractor, che a sua volta modella un trasformatore da liste a
 * liste, che funziona isolando nell'input pezzi di lista i cui valori hanno certe caratteristiche
 * e producendo conseguentemente una informazione riassuntiva in uscita per ognuno di questi pezzi. 
 * 
 * Il commento alle interfacce fornite, e i metodi di test qui sotto costituiscono la necessaria 
 * spiegazione del problema.
 * 
 * Sono considerati opzionali ai fini della possibilità di correggere
 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
 * punteggio: 
 * - implementazione dei test opzionali (denominati 'optionalTestXYZ', ossia quelli relativi a 
 *   PatternExtractorFactory.sumNumericStrings) 
 * - concisione del codice e rimozione di tutte le ripetizioni
 *
 * Si tolga il commento dal metodo init.
 * 
 * Indicazioni di punteggio:
 * - correttezza della parte obbligatoria: 10 punti
 * - correttezza della parte opzionale: 4 punti  
 * - qualità della soluzione: 3 punti
 * 
 */

public class Test {

	private PatternExtractorFactory factory;

	@org.junit.Before
	public void init() {
		this.factory = new PatternExtractorFactoryImpl();
	}

	@org.junit.Test
	public void testConsecutiveZeros() {
		var pe = this.factory.countConsecutiveZeros();
		assertEquals(List.of(2,3), pe.extract(List.of(1,0,0,2,3,0,0,0,4)));
		
	}
	
	@org.junit.Test
	public void testAverage() {
		var pe = this.factory.averageConsecutiveInRange(0.0, 10.0);
		assertEquals(List.of(5.5,2.0), pe.extract(List.of(5.0,6.0,-1.0,1.0,2.0,3.0,-4.0,-5.0)));
	}
	
	@org.junit.Test
	public void testConcatenate() {
		var pe = this.factory.concatenateBySeparator(":");
		assertEquals(List.of("ab","cde","f"), pe.extract(List.of("a","b",":","c","d","e",":",":","f")));
	}
	
	@org.junit.Test
	public void optionalTestNumericStrings() {
		var pe = this.factory.sumNumericStrings();
		assertEquals(List.of(30.0,30.0,150.0), pe.extract(List.of("10","20","a","30","d","40","50","60")));
	}
}
