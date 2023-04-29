package ex2014.a02b.e1;

import static org.junit.Assert.*;

import java.util.*;

public class Test {
	
	/*
	 * Implementare l'interfaccia TicketDispenser data tramite una classe TicketDispenser con costruttore che accetta
	 * un argomento di tipo intero, che rappresenta il numero di "casse" che offrono un servizio coordinato
	 * da un dispenser di ticket (con valori numerici incrementali) -- come alle poste per interderci. Un utente (Agent)
	 * all'arrivo strappa il suo biglietto (Ticket), e rimane in attesa. Quando una cassa (CashDesk) è libera l'utente 
	 * con numero di ticket più basso la usa: quando la rilascia esce dal sistema.
	 * Il commento al codice di TicketDispenser, e i metodi di test qui sotto costituiscono la necessaria spiegazione del 
	 * problema.
	 * Si tolga il commento ai test..
	 */
	
	/*

	// Questo test verifica il comportamento corretto del sistema
	@org.junit.Test
	public void testExec() {  
		// Sistema con 3 casse: 0,1,2; gli agenti sono stringhe
		TicketDispenser<String> disp = new TicketDispenserImpl<>(3);
		
		assertEquals(disp.getNextServingTicket(),0); 	  // prossimo ticket da servire: 0
		assertEquals(disp.getNextAvailableTicket(),0);	  // prossimo ticket da prelevare: 0
		assertEquals(disp.allCurrentlyServed().size(),0); // agenti serviti al momento: nessuno
		assertEquals(disp.allWaiting().size(),0);		  // agenti in attesa: nessuno
		
		// arrivano 5 agenti e prelevano il ticket, si noti l'incrementalità
		assertEquals(disp.giveNextTicketToAgent("a"),0);
		assertEquals(disp.giveNextTicketToAgent("b"),1);
		assertEquals(disp.giveNextTicketToAgent("c"),2);
		assertEquals(disp.giveNextTicketToAgent("d"),3);
		assertEquals(disp.giveNextTicketToAgent("e"),4);
		
		// nuova situazione
		assertEquals(disp.getNextServingTicket(),0); 		// prossimo ticket da servire: 0
		assertEquals(disp.getNextAvailableTicket(),5);		// prossimo ticket da prelevare: 5
		assertEquals(disp.allCurrentlyServed().size(),0);	// agenti serviti al momento: nessuno
		assertEquals(disp.allWaiting(),new HashSet<>(Arrays.asList("a","b","c","d","e"))); // agenti in attesa: a,b,c,d,e
		
		// i primi due agenti in coda sono accettati al desk 0 e 2
		disp.useCashDesk(0); // l'agente a (tocca a lui!) va al desk 0
		disp.useCashDesk(2); // l'agente b (tocca a lui!) va al desk 0
		
		assertEquals(disp.allCurrentlyServed(),new HashSet<>(Arrays.asList("a","b"))); // a,b serviti
		assertEquals(disp.allWaiting(),new HashSet<>(Arrays.asList("c","d","e"))); // c,d,e in attesa
		assertEquals(disp.getNextServingTicket(),2);  // il prossimo a essere servito è l'agente col 2
		assertEquals(disp.getNextAvailableTicket(),5); // il prossimo ticket da strappare è il 5
		assertEquals(disp.getAgentUsingCashDesk(0),"a"); // a usa la cassa 0
		assertEquals(disp.getAgentUsingCashDesk(2),"b"); // b usa la cassa 2
		assertTrue(disp.isCashDeskServing(0));
		assertTrue(disp.isCashDeskServing(2));
		assertFalse(disp.isCashDeskServing(1));	// la cassa 1 non è usata
		
		// b se ne va dalla cassa 2
		disp.releaseCashDesk(2);
		// la cassa 1 chiede un nuovo cliente: è c!
		disp.useCashDesk(1);
		assertEquals(disp.allCurrentlyServed(),new HashSet<>(Arrays.asList("a","c")));
		assertEquals(disp.allWaiting(),new HashSet<>(Arrays.asList("d","e")));
		assertEquals(disp.getNextServingTicket(),3);
		assertEquals(disp.getNextAvailableTicket(),5);	
		assertEquals(disp.getAgentUsingCashDesk(0),"a");
		assertEquals(disp.getAgentUsingCashDesk(1),"c");
		assertTrue(disp.isCashDeskServing(0));
		assertTrue(disp.isCashDeskServing(1));
		assertFalse(disp.isCashDeskServing(2));
		
		// arriva un nuovo agente: f
		assertEquals(disp.giveNextTicketToAgent("f"),5);
		assertEquals(disp.getNextServingTicket(),3);
		assertEquals(disp.getNextAvailableTicket(),6);
	}
	
	// Test delle eccezioni
	@org.junit.Test
	public void testExceptions() {
		TicketDispenser<String> disp = new TicketDispenserImpl<>(3);
		
		disp.giveNextTicketToAgent("a");
		try{
			disp.giveNextTicketToAgent("a");
			fail("No using an agent twice");
		} catch (IllegalArgumentException e){
		} catch (Exception e){
			fail("Wrong exception thrown");
		}
		disp.giveNextTicketToAgent("b");
		disp.useCashDesk(0);
		try{
			disp.useCashDesk(0);
			fail("Cannot use cashdesk twice");
		} catch (IllegalStateException e){
		} catch (Exception e){
			fail("Wrong exception thrown");
		}
		disp.useCashDesk(1);
		try{
			disp.useCashDesk(2);
			fail("No waiting agent");
		} catch (IllegalStateException e){
		} catch (Exception e){
			fail("Wrong exception thrown");
		}
		try{
			disp.getAgentUsingCashDesk(2);
			fail("No agent at cashdesk 2 now");
		} catch (NoSuchElementException e){
		} catch (Exception e){
			fail("Wrong exception thrown");
		}
	}
	
	*/
	
}
