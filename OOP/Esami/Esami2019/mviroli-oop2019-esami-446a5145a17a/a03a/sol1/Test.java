package a03a.sol1;

import static org.junit.Assert.*;

public class Test {
	
	/*
	 * Implementare l'interfaccia RWControllerFactory come indicato nel metodo initFactory qui sotto.
	 * Realizza un concetto di factory per RWController, che a sua volta modella il controllore di una 
	 * risorsa, accessibile in lettura o scrittura, ma con certi vincoli su quando si può accedere.
	 * Ad esempio, un RWController potrebbe essere permissivo e far sempre accedere alla risorsa,
	 * un altro potrebbe applicare "mutual exclusion", consentendo solo un accesso alla volta.
	 * 
	 * Il commento alle interfacce fornite, e i metodi di test qui sotto costituiscono la necessaria 
	 * spiegazione del problema.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, 
	 * ma concorrono comunque al raggiungimento della totalità del punteggio:
	 * - implementazione dei test chiamati optionalTestXYZ (relativi al metodo 
	 *   RWControllerFactory.createManyReadersOrOneWriterExam, e ad alcuni test sulle eccezioni) 
	 * - la buona progettazione della soluzione, in particolare con minimizzazione di ripetizioni attraverso 
	 *   il pattern STRATEGY
	 * 
	 * Si tolga il commento dal metodo initFactory.
	 * 
	 * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 9 punti
	 * - correttezza della parte opzionale: 4 punti
	 * - qualità della soluzione: 4 punti
	 * 
	 */

	
	private RWControllersFactory rwf = null;
	
	@org.junit.Before
	public void initFactory() {
		this.rwf = new RWControllersFactoryImpl();
	}

	@org.junit.Test
	public void testPermissiveRW() {
		// test di un RWController che permette sempre l'accesso
		final RWController rw = this.rwf.createPermissive();
		// entrano tre processi, gli viene dato id 0,1,2
		assertEquals(0, rw.enter());
		assertEquals(1, rw.enter());
		assertEquals(2, rw.enter());
		
		// 0 vuole leggere, 1 vuole scrivere: ci riescono
		rw.goRead(0);
		rw.goWrite(1);
		
		// 0 esce dal sistema, smettendo di leggere
		rw.exit(0);
		
		// entra un nuovo processo, 3
		assertEquals(3, rw.enter());
		
		// 3 vuole scrivere, 2 vuole leggere: ci riescono
		rw.goWrite(3);
		rw.goRead(2);
		
		// 2,1,3 smettono di accedere, uscendo
		rw.exit(2);
		rw.exit(1);
		rw.exit(3);
	}
	
	@org.junit.Test
	public void testOnlyRead() {
		// test di un RWController che funziona come Permissive, ma permette solo le letture
		final RWController rw = this.rwf.createOnlyRead();
		
		assertEquals(0, rw.enter());
		assertEquals(1, rw.enter());
		assertEquals(2, rw.enter());
		
		// 0 e 1 leggono con successo, ed escono
		rw.goRead(0);
		rw.goRead(1);
		rw.exit(0);
		rw.exit(1);
		
		assertEquals(3, rw.enter());
		
		// 2 tenta di scrivere ma fallisce generando la IllegalStateException
		try {
			rw.goWrite(2);
			fail(); // se si arriva qui vuol dire che l'eccezione non è stata generata
		} catch (IllegalStateException e) {
			
		} catch (Exception e) {
			fail("wrong exception thrown");
		}
	}
	
	@org.junit.Test
	public void testMutualExclusion() {
		// test di un RWController che funziona come Permissive, ma permette un solo accesso alla volta
		final RWController rw = this.rwf.createMutualExclusion();
		
		assertEquals(0, rw.enter());
		assertEquals(1, rw.enter());
		assertEquals(2, rw.enter());

		// 0 legge ed esce, 1 legge
		rw.goRead(0);
		rw.exit(0);
		rw.goRead(1);
		
		assertEquals(3, rw.enter());
		
		// 2 tenta di leggere ma fallisce, perché sta ancora leggendo 1
		try {
			rw.goRead(2);
			fail();
		} catch (IllegalStateException e) {
			
		} catch (Exception e) {
			fail("wrong exception thrown");
		}
		
		// 2 tenta di scrivere ma fallisce, perché sta ancora leggendo 1
		try {
			rw.goWrite(2);
			fail();
		} catch (IllegalStateException e) {
			
		} catch (Exception e) {
			fail("wrong exception thrown");
		}
		
		// 1 esce, e a questo punto 2 può accedere
		rw.exit(1);
		rw.goWrite(2);
	}
	
	// non si può far uscire chi non sta leggendo/scrivendo
		@org.junit.Test(expected = IllegalStateException.class)
		public void testCantExitIfNotAccessing() {
			final RWController rw = this.rwf.createPermissive();
			
			assertEquals(0, rw.enter());
			rw.exit(0);
		}
		
		// non si può far leggere/scrivere chi lo sta già facendo
		@org.junit.Test(expected = IllegalStateException.class)
		public void testCantAccessIfAlreadyAccessing() {
			final RWController rw = this.rwf.createPermissive();
			
			assertEquals(0, rw.enter());
			rw.goRead(0);
			rw.goWrite(0);
		}
		
		// non si può far leggere/scrivere chi non ha avuto un id
		@org.junit.Test(expected = IllegalStateException.class)
		public void optionalTestWrongId() {
			final RWController rw = this.rwf.createPermissive();
			
			assertEquals(0, rw.enter());
			rw.goRead(1);
		}
		
		// non si può far leggere/scrivere chi è già uscito
		@org.junit.Test(expected = IllegalStateException.class)
		public void optionalTestAlreadyExited() {
			final RWController rw = this.rwf.createPermissive();
			
			assertEquals(0, rw.enter());
			rw.goRead(0);
			rw.exit(0);
			rw.goRead(0);
		}

	
	@org.junit.Test
	public void optionalTestManyReadersOneWriter() {
		// test di un RWController che o lascia leggere più processi, o lascia scrivere un processo
		final RWController rw = this.rwf.createManyReadersOrOneWriter();
		
		assertEquals(0, rw.enter());
		assertEquals(1, rw.enter());
		assertEquals(2, rw.enter());
		
		// 0 e 1 possono leggere entrambi
		rw.goRead(0);
		rw.goRead(1);
		rw.exit(0);
		rw.exit(1);
		
		assertEquals(3, rw.enter());
		
		// 2 legge
		rw.goRead(2);
		
		// 3 non può scrivere, perché o si legge o si scrive
		try {
			rw.goWrite(3);
			fail();
		} catch (IllegalStateException e) {
			
		} catch (Exception e) {
			fail("wrong exception thrown");
		}
		
		// 2 esce, 3 entra e scrive
		rw.exit(2);
		rw.goWrite(3);
		
		
		assertEquals(4, rw.enter());
		
		// 4 non può scrivere, perché uno solo alla volta scrive
		try {
			rw.goWrite(4);
			fail();
		} catch (IllegalStateException e) {
			
		} catch (Exception e) {
			fail("wrong exception thrown");
		}
		
		// 4 non può neanche leggere
		try {
			rw.goRead(4);
			fail();
		} catch (IllegalStateException e) {
			
		} catch (Exception e) {
			fail("wrong exception thrown");
		}
		
	}
	
}

