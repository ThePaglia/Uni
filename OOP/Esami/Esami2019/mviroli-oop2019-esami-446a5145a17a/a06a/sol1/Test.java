package a06a.sol1;

import static org.junit.Assert.*;

public class Test {

	/*
	 * Implementare l'interfaccia SRServiceFactory come indicato nel metodo
	 * initFactory qui sotto. Realizza un concetto di factory per SRService, che
	 * a sua volta modella (lo sportello per) un servizio postale, dove ogni cliente
	 * può spedire (Send) o ricevere (Receive) un pacco, ma con certi vincoli su quando si può accedere. 
	 * Ad esempio, un SRService potrebbe essere permissivo e far sempre accedere allo 
	 * sportello tutti in concorrenza, un altro potrebbe consentire solo un accesso alla volta,
	 * eccetera.
	 * 
	 * Il commento alle interfacce fornite, e i metodi di test qui sotto costituiscono 
	 * la necessaria spiegazione del problema.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere
	 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
	 * punteggio: 
	 * - implementazione dei test chiamati optionalTestXYZ (relativi ad alcuni test
	 * sulle eccezioni) 
	 * - la buona progettazione della soluzione, in particolare con
	 * minimizzazione di ripetizioni, ad esempio attraverso il pattern STRATEGY
	 * 
	 * Si tolga il commento dal metodo initFactory.
	 * 
	 * Indicazioni di punteggio: 
	 * - correttezza della parte obbligatoria: 8 punti 
	 * - correttezza della parte opzionale: 4 punti 
	 * - qualità della soluzione: 5 punti
	 * 
	 */

	private SRServiceFactory srf = null;

	@org.junit.Before
	public void initFactory() {
		this.srf = new SRServiceFactoryImpl();
	}

	@org.junit.Test
	public void testMaximumAccess() {
		// test di un service che permette sempre l'accesso
		final SRService sr = this.srf.createMaximumAccess();
		// entrano tre clienti, gli viene dato id 0,1,2
		assertEquals(0, sr.enter());
		assertEquals(1, sr.enter());
		assertEquals(2, sr.enter());

		// 0 vuole ricevere, 1 vuole spedire: ci riescono
		sr.goReceive(0);
		sr.goSend(1);

		// 0 esce dal sistema, concludendo la ricezione
		sr.exit(0);

		// entra un nuovo cliente, 3
		assertEquals(3, sr.enter());

		// 3 vuole spedire, 2 vuole ricevere: ci riescono
		sr.goSend(3);
		sr.goReceive(2);

		// 2,1,3 concludono l'operazione, uscendo
		sr.exit(2);
		sr.exit(1);
		sr.exit(3);
	}

	@org.junit.Test
	public void testNoConcurrentAccess() {
		// test di un service che permette un solo accesso alla volta
		final SRService sr = this.srf.createWithNoConcurrentAccess();

		assertEquals(0, sr.enter());
		assertEquals(1, sr.enter());
		assertEquals(2, sr.enter());

		// 0 riceve ed esce, poi 1 spedisce
		sr.goReceive(0);
		sr.exit(0);
		sr.goReceive(1);

		assertEquals(3, sr.enter());

		// 2 tenta di ricevere ma fallisce, perché il cliente 1 sta ancora spedendo
		try {
			sr.goReceive(2);
			fail();
		} catch (IllegalStateException e) {

		} catch (Exception e) {
			fail("wrong exception thrown");
		}

		// 2 tenta di spedire ma fallisce, perché il cliente 1 sta ancora spedendo
		try {
			sr.goSend(2);
			fail();
		} catch (IllegalStateException e) {

		} catch (Exception e) {
			fail("wrong exception thrown");
		}

		// 1 esce, e a questo punto 2 può accedere
		sr.exit(1);
		sr.goSend(2);
	}

	// non si può far uscire chi non sta eseguendo una operazione
	@org.junit.Test(expected = IllegalStateException.class)
	public void testCantExitIfNotAccessing() {
		final SRService sr = this.srf.createMaximumAccess();

		assertEquals(0, sr.enter());
		sr.exit(0);
	}
	
	@org.junit.Test
	public void testManyReceiveAndOneSend() {
		// test di un serive lascia ricevere più clienti, ma spedire un solo processo
		// alla volta
		final SRService sr = this.srf.createManyReceiveAndMaxTwoSend();

		assertEquals(0, sr.enter());
		assertEquals(1, sr.enter());
		assertEquals(2, sr.enter());

		// 0 e 1 possono ricevere entrambi, 2 può spedire
		sr.goReceive(0);
		sr.goReceive(1);
		sr.goSend(2);

		assertEquals(3, sr.enter());
		assertEquals(4, sr.enter());

		// 2 può ricevere
		sr.goSend(3);

		// 4 non può spedire, perché stanno già spedendo 2 e 3
		try {
			sr.goSend(4);
			fail();
		} catch (IllegalStateException e) {

		} catch (Exception e) {
			fail("wrong exception thrown");
		}

	}

	// non si può far eseguire una operazione a chi lo sta già facendo
	@org.junit.Test(expected = IllegalStateException.class)
	public void testCantAccessIfAlreadyAccessing() {
		final SRService sr = this.srf.createMaximumAccess();

		assertEquals(0, sr.enter());
		sr.goReceive(0);
		sr.goSend(0);
	}

	// non si può far eseguire una operazione a chi non ha avuto un id
	@org.junit.Test(expected = IllegalStateException.class)
	public void optionalTestWrongId() {
		final SRService sr = this.srf.createMaximumAccess();

		assertEquals(0, sr.enter());
		sr.goReceive(1);
	}

	// non si può far eseguire una operazione a chi è già uscito
	@org.junit.Test(expected = IllegalStateException.class)
	public void optionalTestAlreadyExited() {
		final SRService sr = this.srf.createMaximumAccess();

		assertEquals(0, sr.enter());
		sr.goReceive(0);
		sr.exit(0);
		sr.goReceive(0);
	}
}
