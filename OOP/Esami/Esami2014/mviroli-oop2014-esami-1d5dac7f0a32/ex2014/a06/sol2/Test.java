package ex2014.a06.sol2;

import static org.junit.Assert.*;

import java.util.*;

public class Test {

	/*
	 * Si consideri l'interfaccia Lamp che modella una lampadina con anche un comando "fail" che modella un fallimento della stessa,
	 * la sua implementazione banale SimpleLamp fornita a mero titolo di esempio, e l'interfaccia LampFactory usata per creare oggetti 
	 * di due tipi specifici di lampadine.
	 * Una LampWithRemainingDuration quando fallisce funzionerà ancora per un numero limitato di accensioni (preso in input).
	 * Una LampWithTemporaneousFailure quando fallisce smette di funzionare per un numero limitato di accensioni (preso in input), poi rifunziona.
	 * 
	 * Attraverso la realizzazione di due diverse implementazioni di Lamp per queste due specializzazioni, implementare LampFactory con un costruttore
	 * vuoto. Si prediligeranno le soluzioni che fattorizzano *tutti* gli aspetti comuni di queste due specializzazioni in una classe astratta.
	 * 
	 * Si tolga il commento ai test qui sotto..
	 */
	
	// Test base su una qualunque lampadina inizialmente off
	private void basicChecks(Lamp l){
		assertFalse(l.isOn());
		l.on();
		assertTrue(l.isOn());
		l.on();
		assertTrue(l.isOn());
		l.off();
		assertFalse(l.isOn());
		l.off();
		assertFalse(l.isOn());
		l.on();
		assertTrue(l.isOn());
	}
	
	// Test della SimpleLamp fornita
	@org.junit.Test
	public void testSimpleLamp() {
		Lamp l = new SimpleLamp();
		basicChecks(l);
	}
	

	// Test di un LampWithRemainingDuration
	@org.junit.Test
	public void testLampWithRemainingDuration() {
		// Usando il factory, creo un lampadina che al fail durerà solo ancora 3 accensioni
		Lamp l = new LampFactoryImpl().createLampWithRemainingDuration(3);
		// controlli base
		basicChecks(l);
		l.on();
		assertTrue(l.isOn());
		// faccio fallire la lampadina
		l.fail();
		// era on e rimane on
		assertTrue(l.isOn());
		// per 3 volte spengo e accendo
		for (int i=0; i<3; i++){
			l.off();
			assertFalse(l.isOn());
			l.on();
			l.on(); // La seconda accensione di fila non ha mai effetto
			assertTrue(l.isOn());
		}
		// spengo
		l.off();
		assertFalse(l.isOn());
		// accendo, e ciò deve causare l'eccezione
		try{
			l.on();
			fail("Can't switch on 3 switches after fail");
		} catch (IllegalStateException e){
		} catch (Exception e){
			fail("Wrong exception thrown");
		}
		// da qui in poi la lampadina è off
		assertFalse(l.isOn());
	}
	
	// Test di un LampWithTemporaneousFailure
	@org.junit.Test
	public void testLampWithTemporaneousFailure() {
		// Usando il factory, creo un lampadina che al fail rimarrà inattiva per 3 accensioni, poi funzionerà bene
		Lamp l = new LampFactoryImpl().createLampWithTemporaneousFailure(3);
		// controlli base
		basicChecks(l);
		l.on();
		assertTrue(l.isOn());
		// faccio fallire la lampadina
		l.fail();
		assertFalse(l.isOn());
		// per 3 volte provo a accenderla, ma fallisce dando l'eccezione
		for (int i=0; i<3; i++){
			try{
				l.on();
				fail("Switch on during failure should raise an exception");
			} catch (IllegalStateException e){
			} catch (Exception e){
				fail("Wrong exception thrown");
			}
			assertFalse(l.isOn());
		}
		// d'ora in poi è tutto ok
		l.on();
		assertTrue(l.isOn());
		l.off();
		basicChecks(l);
	}
}
