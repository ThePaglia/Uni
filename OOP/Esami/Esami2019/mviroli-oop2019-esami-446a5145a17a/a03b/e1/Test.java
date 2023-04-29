package a03b.e1;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;
import a03b.e1.PostOffice.Operation;

public class Test {
	
	/*
	 * Implementare l'interfaccia PostOfficeFactory come indicato nel metodo initFactory qui sotto.
	 * Realizza un concetto di factory per PostOffice, che a sua volta modella un ufficio postale 
	 * dove persone entrano, prendono un ticket numerato indicando quale operazione vogliono fare 
	 * (spedizione o ricezione), vanno in ordine agli sportelli ('desks') liberi, e poi escono
	 * dall'ufficio postale (potrebbe anche uscire mentre in attesa, senza farsi servire).  
	 * La factory crea due implementazioni, dove gli sportelli impongono vincoli diversi su quale tipo di 
	 * operazione possono effettuare di volta in volta.
	 * 
	 * Il commento alle interfacce fornite, e i metodi di test qui sotto costituiscono la necessaria 
	 * spiegazione del problema.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, 
	 * ma concorrono comunque al raggiungimento della totalità del punteggio:
	 * - implementazione del test chiamato optionalTestXYZ (relativo al metodo 
	 *   PostOffice.createAlternating) 
	 * - la buona progettazione della soluzione, in particolare con minimizzazione di ripetizioni
	 * 
	 * Si tolga il commento dal metodo initFactory.
	 * 
	 * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 9 punti
	 * - correttezza della parte opzionale: 4 punti
	 * - qualità della soluzione: 4 punti
	 * 
	 */

	private PostOfficeFactory pof = null;
	
	@org.junit.Before
	public void initFactory() {
		//this.pof = new PostOfficeFactoryImpl();
	}
	
	// controlli base su un qualunque PostOffice
	private void basicChecks(PostOffice po) {
		// inizialmente tutti gli sportelli sono liberi e nessuno è in coda
		assertEquals(Optional.empty(),po.deskState(0));
		assertEquals(Optional.empty(),po.deskState(1));
		assertEquals(Optional.empty(),po.deskState(2));
		assertEquals(List.of(),po.peopleWaiting());

		// arrivano 3 clienti
		assertEquals(0, po.getTicket(Operation.SEND));
		assertEquals(1, po.getTicket(Operation.RECEIVE));
		assertEquals(2, po.getTicket(Operation.SEND));
		
		// tutti gli sportelli sono liberi, i tre clienti sono in coda
		assertEquals(Optional.empty(),po.deskState(0));
		assertEquals(Optional.empty(),po.deskState(1));
		assertEquals(Optional.empty(),po.deskState(2));
		assertEquals(List.of(0,1,2),po.peopleWaiting());
		
		// il cliente con ticket 0 va allo sportello 0
		po.goToDesk(0, 0);
		
		// il primo sportello sta servendo un SEND, i clienti 1 e 2 sono in coda
		assertEquals(Optional.of(Operation.SEND),po.deskState(0));
		assertEquals(Optional.empty(),po.deskState(1));
		assertEquals(Optional.empty(),po.deskState(2));
		assertEquals(List.of(1,2),po.peopleWaiting());
		
		// il cliente 2 esce, il 3 entra
		po.exitOnWait(2);
		assertEquals(3, po.getTicket(Operation.RECEIVE));
		
		// il primo sportello sta servendo un SEND, i clienti 1 e 3 sono in coda
		assertEquals(Optional.of(Operation.SEND),po.deskState(0));
		assertEquals(Optional.empty(),po.deskState(1));
		assertEquals(Optional.empty(),po.deskState(2));
		assertEquals(List.of(1,3),po.peopleWaiting());
		
		// il cliente con ticket 1 va allo sportello 2
		po.goToDesk(1, 2);
		
		assertEquals(Optional.of(Operation.SEND),po.deskState(0));
		assertEquals(Optional.empty(),po.deskState(1));
		assertEquals(Optional.of(Operation.RECEIVE),po.deskState(2));
		assertEquals(List.of(3),po.peopleWaiting());
		
		// il cliente allo sportello 0 esce
		po.deskReleased(0);
		
		// il terzo sportello sta servendo un RECEIVE, il client 3 è in coda
		assertEquals(Optional.empty(),po.deskState(0));
		assertEquals(Optional.empty(),po.deskState(1));
		assertEquals(Optional.of(Operation.RECEIVE),po.deskState(2));
		assertEquals(List.of(3),po.peopleWaiting());
	}
	
	@org.junit.Test
	public void testFlexibleBasic() {
		final PostOffice po = this.pof.createFlexible(3);
		this.basicChecks(po); // test base, vedi sopra
	}

	@org.junit.Test
	public void testFlexiblePOAllocation() {
		final PostOffice po = this.pof.createFlexible(3);
		
		assertEquals(0, po.getTicket(Operation.SEND));
		assertEquals(1, po.getTicket(Operation.SEND));
		assertEquals(2, po.getTicket(Operation.RECEIVE));
		
		// si verifica che ogni allocazione possibile è corretta, senza eccezioni
		
		po.goToDesk(0, 0);
		po.deskReleased(0);
		
		po.goToDesk(1, 0);
		po.deskReleased(0);
		
		po.goToDesk(2, 0);
		po.deskReleased(0);
	}
	
	@org.junit.Test
	public void optionalTestAlternatingBasic() {
		final PostOffice po = this.pof.createAlternating(3);
		this.basicChecks(po); // test base, vedi sopra
	}
	
	@org.junit.Test
	public void optionalTestAlternatingPOAllocation() {
		final PostOffice po = this.pof.createAlternating(3);
		
		assertEquals(0, po.getTicket(Operation.SEND));
		assertEquals(1, po.getTicket(Operation.SEND));
		assertEquals(2, po.getTicket(Operation.RECEIVE));
		assertEquals(3, po.getTicket(Operation.RECEIVE));
		
		// lo sportello 0 accetta è un SEND
		po.goToDesk(0, 0);
		po.deskReleased(0);
		
		// ora lo sportello 0 non accetta un SEND
		// il test passa solo se viene lanciata una IllegalStateException..
		
		try {
			po.goToDesk(1,0);
			fail("can't use desk 0 for sending again");
		} catch (IllegalStateException e) {
			
		} catch (Exception e) {
			fail("wrong exception raised");
		}
		
		// il cliente 1 può andare allo sportello 1
		po.goToDesk(1,1);
		// il cliente 2, avendo una RECEIVE può andare allo sportello 0
		po.goToDesk(2,0);
		po.deskReleased(0);
		
		// ora lo sportello 0 non accetta un RECEIVE
		try {
			po.goToDesk(3,0);
			fail("can't use desk 0 for receiving again");
		} catch (IllegalStateException e) {
			
		} catch (Exception e) {
			fail("wrong exception raised");
		}
	}
	
	
}

