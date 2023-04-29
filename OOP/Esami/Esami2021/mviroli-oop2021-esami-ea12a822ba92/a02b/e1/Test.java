package a02b.e1;

import static a02b.e1.UniversityProgram.Sector.*;
import static org.junit.Assert.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

public class Test {

	/*
	 * Implementare l'interfaccia UniversityProgramFactory come indicato nel metodo
	 * initFactory qui sotto. Realizza una factory per un concetto di corso di laurea (program), 
	 * catturato dall'interfaccia UniversityProgram.
	 * 
	 * Come si può vedere nel metodo fillProgram qui sotto, ad un CDL si
	 * associa innanzitutto un insieme di corsi, ognuno con un certo numero di credi e un settore,
	 * ad esempio: ("OOP", COMPUTER_ENGINEERING, 12)
	 * 
	 * A quel punto il CDL ha un metodo per stabilire se una certa selezione di corsi (ossia
	 * un curriculum presentato da uno studente) soddisfa o meno i requisiti, espressi da una logica
	 * CDL-specifica, in termini di numero di crediti (minimi e/o massimi) per ogni settore o 
	 * gruppo di settori.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere
	 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
	 * punteggio: 
	 * 
	 * - implementazione dei quattro metodi della factory (ossia, nella parte obbligatoria è sufficiente 
	 * implementarne 3 a piacimento) 
	 * - la buona progettazione della soluzione, utilizzando patterns che portino a codice succinto 
	 * che evita ripetizioni
	 * 
	 * Si tolga il commento dal metodo initFactory.
	 * 
	 * Indicazioni di punteggio: 
	 * - correttezza della parte obbligatoria: 10 punti 
	 * - correttezza della parte opzionale: 3 punti (ulteriore metodo della factory) 
	 * - qualità della soluzione: 4 punti (per buon design)
	 * 
	 */

	private UniversityProgramFactory factory = null;
	
	@org.junit.Before
	public void initFactory() {
		// this.factory = new UniversityProgramFactoryImpl();
	}
	
	private void fillProgram(UniversityProgram program) {
		program.addCourse("PRG", COMPUTER_SCIENCE, 12);
		program.addCourse("ALG", COMPUTER_SCIENCE, 12);
		program.addCourse("SISOP", COMPUTER_SCIENCE, 12);
		program.addCourse("WEB", COMPUTER_SCIENCE, 6);
		
		program.addCourse("ARCH", COMPUTER_ENGINEERING, 12);
		program.addCourse("OOP", COMPUTER_ENGINEERING, 12);
		program.addCourse("NETWORKS", COMPUTER_ENGINEERING, 6);
		program.addCourse("SOFTENG", COMPUTER_ENGINEERING, 6);
		
		program.addCourse("MAT1", MATHEMATICS, 6);
		program.addCourse("MAT2", MATHEMATICS, 6);
		
		program.addCourse("FIS1", PHYSICS, 6);
		program.addCourse("FIS2", PHYSICS, 6);
		
		program.addCourse("THESIS-", THESIS,6);
		program.addCourse("THESIS", THESIS, 12);
		program.addCourse("THESIS++", THESIS, 24);
	}
	
	@org.junit.Test
	public void testFlexible() {
		var program = this.factory.flexible();
		fillProgram(program);
		assertTrue(program.isValid(Set.of("PRG","ALG","ARCH","MAT1","FIS1","THESIS"))); // 60 cfu
		assertTrue(program.isValid(Set.of("PRG","ALG","WEB","SOFTENG", "MAT1","FIS1","THESIS"))); // 60 cfu
		assertFalse(program.isValid(Set.of("PRG","ALG","WEB","SOFTENG", "MAT1","FIS1","THESIS++"))); // 66 cfu
		assertFalse(program.isValid(Set.of("PRG","ALG","WEB"))); // 36 cfu
		assertFalse(program.isValid(Set.of())); // 0 cfu
	}
	
	@org.junit.Test
	public void testScientific() {
		var program = this.factory.scientific();
		fillProgram(program);
		assertTrue(program.isValid(Set.of("PRG","ALG","MAT1","MAT2","FIS1","FIS2","OOP"))); // ok
		assertFalse(program.isValid(Set.of("PRG","ALG","MAT1","WEB","FIS1","FIS2","OOP"))); // not enough Math
		assertFalse(program.isValid(Set.of("PRG","ALG","MAT1","MAT2","WEB","FIS2","OOP"))); // not enough Phys
		assertFalse(program.isValid(Set.of("OOP","SOFTENG","NETWORKS","ARCH","MAT1","MAT2","FIS1","FIS2","THESIS"))); // too much CFUs
	}
	
	@org.junit.Test
	public void testShortCS() {
		var program = this.factory.shortComputerScience();
		fillProgram(program);
		assertTrue(program.isValid(Set.of("PRG","ALG","ARCH","THESIS"))); // ok
		assertTrue(program.isValid(Set.of("PRG","ALG","ARCH","THESIS","MAT1"))); // ok
		assertFalse(program.isValid(Set.of("PRG","ALG","THESIS","MAT1","MAT2"))); // not enough Computers
		assertFalse(program.isValid(Set.of("PRG","ALG","THESIS"))); // not enough CFUs
	}
	
	@org.junit.Test
	public void testRealistic() {
		var program = this.factory.realistic();
		fillProgram(program);
		assertTrue(program.isValid(Set.of("PRG","ALG","ARCH","OOP","SISOP","WEB","SOFTENG",
				"NETWORKS","MAT1","MAT2","FIS1","THESIS++"))); // ok
		assertFalse(program.isValid(Set.of("PRG","ALG","ARCH","OOP","SISOP","WEB","SOFTENG",
				"NETWORKS","MAT1","MAT2","FIS1","THESIS"))); // not enough CFUs
		assertFalse(program.isValid(Set.of("PRG","ALG","ARCH","OOP","SISOP","WEB","SOFTENG",
				"MAT1","MAT2","FIS1","FIS2","THESIS++"))); // too much MATH+FIS
	}
}
