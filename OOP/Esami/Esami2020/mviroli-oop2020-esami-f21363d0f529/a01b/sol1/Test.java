package a01b.sol1;

import static org.junit.Assert.*;
import java.util.*;

/**
 * Si consulti la documentazione della interfaccia MathematicalFunctionFactor, che modella
 * una factory per MathematicalFunction, che a sua volta modella una funzione matematica,
 * che mappa elementi di un dominio (che potrebbe essere infinito) su un codominio.
 * 
 * Il commento alle interfacce fornite, e i metodi di test qui sotto costituiscono la necessaria 
 * spiegazione del problema.
 * 
 * Sono considerati opzionali ai fini della possibilità di correggere
 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
 * punteggio: 
 * - implementazione dei test opzionali (denominati 'optionalTestXYZ', ossia quelli relativi a 
 *   MathematicalFunction.restrict e MathematicalFunctionFactory.fromMap 
 * - minimizzazione di ripetizioni 
 * - concisione del codice
 *
 * Si tolga il commento dal metodo init.
 * 
 * Indicazioni di punteggio:
 * - correttezza della parte obbligatoria: 10 punti
 * - correttezza della parte opzionale: 4 punti (2 per restrict, 2 per fromMap) 
 * - qualità della soluzione: 3 punti
 * 
 */

public class Test {

	private MathematicalFunctionsFactory factory;

	@org.junit.Before
	public void init() {
		this.factory = new MathematicalFunctionsFactoryImpl();
	}

	@org.junit.Test
	public void testBasicConstant() {
		// Funzione che associa ad ogni intero in [0,1,2,...,10] il reale 1.0
		MathematicalFunction<Integer, Double> constant = this.factory.constant(i -> i >= 0 && i <= 10, 1.0);
		assertTrue(constant.inDomain(0));
		assertTrue(constant.inDomain(1));
		assertTrue(constant.inDomain(2));
		assertTrue(constant.inDomain(10));
		assertFalse(constant.inDomain(11));
		assertFalse(constant.inDomain(-1));
		assertEquals(Optional.of(1.0), constant.apply(0));
		assertEquals(Optional.of(1.0), constant.apply(1));
		assertEquals(Optional.of(1.0), constant.apply(9));
		assertEquals(Optional.of(1.0), constant.apply(10));
		assertEquals(Optional.empty(), constant.apply(11));
		assertEquals(Optional.empty(), constant.apply(-1));
	}
	
	@org.junit.Test
	public void testBasicIdentity() {
		// Funzione che associa ogni intero in [0,1,2,...,10] se stesso
		MathematicalFunction<Integer, Integer> constant = this.factory.identity(i -> i >= 0 && i <= 10);
		assertTrue(constant.inDomain(0));
		assertTrue(constant.inDomain(1));
		assertTrue(constant.inDomain(2));
		assertTrue(constant.inDomain(10));
		assertFalse(constant.inDomain(11));
		assertFalse(constant.inDomain(-1));
		assertEquals(Optional.of(0), constant.apply(0));
		assertEquals(Optional.of(1), constant.apply(1));
		assertEquals(Optional.of(9), constant.apply(9));
		assertEquals(Optional.of(10), constant.apply(10));
		assertEquals(Optional.empty(), constant.apply(11));
		assertEquals(Optional.empty(), constant.apply(-1));
	}
	
	@org.junit.Test
	public void testBasicFunction() {
		// Funzione che associa ogni intero 'i' in [0,1,2,...,10] l'intero 'i+1'
		MathematicalFunction<Double, Double> function = this.factory.fromFunction(i -> i >= 0 && i <= 10, i->i+1);
		assertTrue(function.inDomain(0.0));
		assertTrue(function.inDomain(1.5));
		assertTrue(function.inDomain(2.9));
		assertTrue(function.inDomain(9.99));
		assertFalse(function.inDomain(10.1));
		assertFalse(function.inDomain(-0.1));
		assertEquals(Optional.of(1.0), function.apply(0.0));
		assertEquals(Optional.of(2.5), function.apply(1.5));
		assertEquals(Optional.of(3.9), function.apply(2.9));
		assertEquals(Optional.of(10.99), function.apply(9.99));
		assertEquals(Optional.empty(), function.apply(10.1));
		assertEquals(Optional.empty(), function.apply(-0.1));
	}
	
	@org.junit.Test
	public void testBasicFunction2() {
		// Funzione che associa ad ogni intero 'i' (qualsiasi) l'intero 'i+1'
		MathematicalFunction<Integer, Integer> function = this.factory.fromFunction(i -> true, i->i+1);
		assertTrue(function.inDomain(0));
		assertTrue(function.inDomain(1));
		assertTrue(function.inDomain(10000));
		assertTrue(function.inDomain(-20000));
		assertEquals(Optional.of(1), function.apply(0));
		assertEquals(Optional.of(3), function.apply(2));
		assertEquals(Optional.of(10001), function.apply(10000));
	}
	
	@org.junit.Test
	public void testWithUpdated1() {
		// prende una costante e la aggiorna col mapping 0->1
		// Dà una funzione che è 0 ovunque, tranne in 0, dove dà 1 (una specie di delta di dirac)"
		MathematicalFunction<Integer, Integer> constant = this.factory.constant(a->true, 0);
		MathematicalFunction<Integer, Integer> diracLike= constant.withUpdatedValue(0, 1);
		assertTrue(diracLike.inDomain(0));
		assertTrue(diracLike.inDomain(1));
		assertTrue(diracLike.inDomain(2));
		assertTrue(diracLike.inDomain(-10000));
		assertEquals(Optional.of(1), diracLike.apply(0));
		assertEquals(Optional.of(0), diracLike.apply(1));
		assertEquals(Optional.of(0), diracLike.apply(2));
		assertEquals(Optional.of(0), diracLike.apply(-10000));
	}
	
	@org.junit.Test
	public void testWithUpdated2() {
		// Prende la funzione "+1" sul dominio [0,..,10], e gli aggiunge il mapping -10->-10
		MathematicalFunction<Double, Double> function = this.factory.fromFunction(i -> i >= 0 && i <= 10, i->i+1);
		MathematicalFunction<Double, Double> strange = function.withUpdatedValue(0.0, 0.0).withUpdatedValue(-10.0, -10.0);
		assertTrue(strange.inDomain(0.0));
		assertTrue(strange.inDomain(5.0));
		assertTrue(strange.inDomain(10.0));
		assertTrue(strange.inDomain(-10.0));
		assertFalse(strange.inDomain(-9.9));
		assertFalse(strange.inDomain(20.0));
		assertEquals(Optional.of(0.0), strange.apply(0.0));
		assertEquals(Optional.of(2.0), strange.apply(1.0));
		assertEquals(Optional.of(11.0), strange.apply(10.0));
		assertEquals(Optional.of(3.5), strange.apply(2.5));
		assertEquals(Optional.of(-10.0), strange.apply(-10.0));
		assertEquals(Optional.empty(), strange.apply(-9.9));
		assertEquals(Optional.empty(), strange.apply(11.0));
	}
	
	@org.junit.Test
	public void testComposition1() {
		// Compone la costante 1 con la funzione "+1", dando la costante 2
		MathematicalFunction<Integer, Integer> f1 = this.factory.constant(a->true,1);
		MathematicalFunction<Integer, Integer> f2 = this.factory.fromFunction(a->true,i->i+1);
		MathematicalFunction<Integer, Integer> f3 = f1.composeWith(f2);
		assertTrue(f3.inDomain(0));
		assertTrue(f3.inDomain(1));
		assertTrue(f3.inDomain(2));
		assertTrue(f3.inDomain(-1));
		assertEquals(Optional.of(2), f3.apply(0));
		assertEquals(Optional.of(2), f3.apply(1));
		assertEquals(Optional.of(2), f3.apply(2));
		assertEquals(Optional.of(2), f3.apply(10));
	}
	
	@org.junit.Test
	public void testComposition2() {
		// Compone la funzione "+1" con la costante 1, dando la costante 1
		MathematicalFunction<Integer, Integer> f1 = this.factory.constant(a->true,1);
		MathematicalFunction<Integer, Integer> f2 = this.factory.fromFunction(a->true,i->i+1);
		MathematicalFunction<Integer, Integer> f3 = f2.composeWith(f1);
		assertTrue(f3.inDomain(0));
		assertTrue(f3.inDomain(1));
		assertTrue(f3.inDomain(2));
		assertTrue(f3.inDomain(-1));
		assertEquals(Optional.of(1), f3.apply(0));
		assertEquals(Optional.of(1), f3.apply(1));
		assertEquals(Optional.of(1), f3.apply(2));
		assertEquals(Optional.of(1), f3.apply(10));
	}
	
	@org.junit.Test
	public void optionalTestFromMap() {
		Map<Integer,String> map = new HashMap<>();
		map.put(0, "a");
		map.put(1, "b");
		map.put(2, "c");
		// Funzione che associa ad ogni intero 'i' una string 's', proprio come farebbe la map 
		MathematicalFunction<Integer, String> mapFunction = this.factory.fromMap(map);
		assertTrue(mapFunction.inDomain(0));
		assertTrue(mapFunction.inDomain(1));
		assertTrue(mapFunction.inDomain(2));
		assertFalse(mapFunction.inDomain(3));
		assertFalse(mapFunction.inDomain(-1));
		assertEquals(Optional.of("a"), mapFunction.apply(0));
		assertEquals(Optional.of("b"), mapFunction.apply(1));
		assertEquals(Optional.of("c"), mapFunction.apply(2));
		assertEquals(Optional.empty(), mapFunction.apply(3));
		assertEquals(Optional.empty(), mapFunction.apply(-1));
		assertEquals(Optional.empty(), mapFunction.apply(-1));
	}
	
	@org.junit.Test
	public void optionalTestRestrict() {
		// prende una costante e la restringe al dominio coi soli valori 0,1,2,3
		MathematicalFunction<Integer, Integer> constant = this.factory.constant(a->true, 0);
		MathematicalFunction<Integer, Integer> fun = constant.restrict(Set.of(0,1,2,3));
		assertTrue(fun.inDomain(0));
		assertTrue(fun.inDomain(1));
		assertTrue(fun.inDomain(2));
		assertTrue(fun.inDomain(3));
		assertFalse(fun.inDomain(4));
		assertFalse(fun.inDomain(-1));
		assertEquals(Optional.of(0), fun.apply(0));
		assertEquals(Optional.of(0), fun.apply(1));
		assertEquals(Optional.of(0), fun.apply(2));
		assertEquals(Optional.empty(), fun.apply(-10000));
	}
}
