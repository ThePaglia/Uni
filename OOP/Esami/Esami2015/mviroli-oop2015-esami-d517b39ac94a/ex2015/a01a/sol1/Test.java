package ex2015.a01a.sol1;

import static org.junit.Assert.*;
import static ex2015.a01a.sol1.CoursesCalendar.Day.*;
import static ex2015.a01a.sol1.CoursesCalendar.Room.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.*;

public class Test {
	
	/*
	 * Implementare l'interfaccia CoursesCalendar data tramite una classe CoursesCalendarImpl con costruttore senza argomenti.
	 * Modella il gestore di un orario settimanale delle lezioni, con un metodo per fare una prenotazione, e metodi
	 * per estrarre informazioni.
	 * Il commento al codice di CoursesCalendar, e i metodi di test qui sotto costituiscono la necessaria spiegazione del 
	 * problema.
	 * I test il cui nome comincia con 'optional' sono considerati opzionali ai fini della possibilità di correggere
	 * l'esercizio -- anche se concorrono alla definizione del punteggio.
	 * Si tolga il commento ai test..
	 */

	@org.junit.Test
	public void testPossibleSlots() {
		// slot disponibili per la prenotazione in generale: esattamente 9,10,11,12,14,15,16,17
		CoursesCalendar cc = new CoursesCalendarImpl();
		assertEquals(cc.possibleSlots().size(),8);
		assertThat(cc.possibleSlots(),hasItems(9,10,11,12,14,15,16,17));
	}
	
	@org.junit.Test
	public void testBasicBooking() {  
		// prenotazione quasi standard per OOP and SISOP
		CoursesCalendar cc = new CoursesCalendarImpl();
		cc.bookRoom(MONDAY, VELA, 10, 3, "OOP");
		cc.bookRoom(TUESDAY, VELA, 14, 3, "OOP");
		cc.bookRoom(WEDNESDAY, MAGNA, 10, 3, "OOP");
		cc.bookRoom(THURSDAY, MAGNA, 14, 3, "OOP");
		cc.bookRoom(MONDAY, VELA, 14, 3, "SISOP");
		cc.bookRoom(TUESDAY, VELA, 9, 3, "SISOP");
		cc.bookRoom(WEDNESDAY, MAGNA, 14, 3, "SISOP");
		cc.bookRoom(FRIDAY, C_ALDO_MORO, 10, 3, "SISOP");
		
		// prenotazioni in VELA al lunedì
		Set<Pair<Integer,String>> set = cc.dayRoomSlots(MONDAY, VELA);
		assertEquals(set.size(),6);
		assertTrue(set.contains(new Pair<>(10,"OOP")));
		assertTrue(set.contains(new Pair<>(11,"OOP")));
		assertTrue(set.contains(new Pair<>(12,"OOP")));
		assertTrue(set.contains(new Pair<>(14,"SISOP")));
		assertTrue(set.contains(new Pair<>(15,"SISOP")));
		assertTrue(set.contains(new Pair<>(16,"SISOP")));
		
		// nessuna in MAGNA al lunedì
		assertEquals(cc.dayRoomSlots(MONDAY, MAGNA).size(),0);
		
		// prenotazioni di OOP: quali il giovedì in MAGNA?
		Set<Integer> set2 = cc.courseSlots("OOP").get(new Pair<>(THURSDAY,MAGNA));
		assertEquals(set2.size(),3);
		assertThat(set2,hasItems(14,15,16));
	}
	
	@org.junit.Test
	public void optionalTestJumpBooking() {
		// una prenotazione di 6 ore a cavallo del pranzo
		CoursesCalendar cc = new CoursesCalendarImpl();
		cc.bookRoom(MONDAY, VELA, 10, 6, "OOP");
		
		Set<Pair<Integer,String>> set = cc.dayRoomSlots(MONDAY, VELA);
		assertEquals(set.size(),6);
		assertTrue(set.contains(new Pair<>(10,"OOP")));
		assertTrue(set.contains(new Pair<>(11,"OOP")));
		assertTrue(set.contains(new Pair<>(12,"OOP")));
		assertTrue(set.contains(new Pair<>(14,"OOP")));
		assertTrue(set.contains(new Pair<>(15,"OOP")));
		assertTrue(set.contains(new Pair<>(16,"OOP")));
	}
	
	@org.junit.Test
	public void testConflictingBooking() {  
		// esempio di due prenotazioni in conflitto
		CoursesCalendar cc = new CoursesCalendarImpl();
		cc.bookRoom(MONDAY, VELA, 10, 6, "OOP");
		assertEquals(cc.dayRoomSlots(MONDAY, VELA).size(),6);
		try{
			cc.bookRoom(MONDAY, VELA, 9, 2, "SISOP");
			fail("cannot book with conflict from 16 to 17");
		} catch (IllegalStateException e){}
		  catch (Exception e){
			  fail("wrong exception thrown");
		}
		// essendo in conflitto, SISOP non prenota niente!!
		assertEquals(cc.dayRoomSlots(MONDAY, VELA).size(),6);
	}
	
	
}
