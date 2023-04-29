package a03b.sol1;

import static org.junit.Assert.*;
import java.util.*;

public class Test {
	
	/*
	 * Implementare l'interfaccia ExamsManagement fornita tramite una classe ExamsManagementImpl con costruttore senza argomenti.
	 * Realizza un concetto di gestore dei voti di questo corso OOP.
	 * Fornisce funzionalità per:
	 * - creare studenti 
	 * - registrare i voti degli studenti ai vari appelli in lab (appelli numerati con id incrementale);
	 * - registrare il voto del progetto (definito da un nome)
	 * - calcolare il voto finale come media pesata (60% al migliore - 40% al peggiore), fra il voto in lab (l'ultimo
	 * conseguito) e quello al progetto
	 * - per ottenere informazioni storiche
	 * 
	 * Si noti che i test con annotazione @org.junit.Test(expected = ...) passano solo se viene lanciata l'eccezione indicata nei ...
	 * Si noti che i test con annotazione @org.junit.Test passano se non viene lanciata alcuna eccezione
	 * Si noti che i metodi con annotazione @org.junit.Before vengono eseguiti sempre prima di aver eseguito ogni test, e servono
	 * a preparare una configurazione "nuova" (sempre la stessa) prima di eseguire un test.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
	 * della totalità del punteggio:
	 * - implementazione dei test chiamati optionalTestXYZ (relativi al metodo projectEvaluation e alla gestione delle eccezioni) 
	 * - la buona progettazione della soluzione, in particolare con minimizzazione di ripetizioni
	 * 
	 */
	
	private ExamsManagement data;
	
	@org.junit.Before
	public void createData() {
		this.data = new ExamsManagementImpl();
		data.createStudent(10, "Mario");
		data.createStudent(20, "Lucia");
		data.createStudent(30, "Carlo");
		data.createStudent(40, "Anna");
		data.createStudent(50, "Ugo");
		data.createStudent(60, "Silvia");
		data.registerLabEvaluation(10, 30, 1);
		data.registerLabEvaluation(20, 18, 1);
		data.registerLabEvaluation(30, 25, 1);
		data.registerLabEvaluation(40, 16, 1);
		
		data.registerProjectEvaluation(20, 30, "Pacman");
		data.registerProjectEvaluation(40, 29, "Pacman");
		
		data.registerLabEvaluation(20, 24, 2);
		data.registerLabEvaluation(40, 15, 2);
				
		data.registerLabEvaluation(50, 21, 3);
		data.registerLabEvaluation(60, 21, 3);
		
		data.registerProjectEvaluation(10, 28, "MarioBros");
		data.registerProjectEvaluation(30, 28, "MarioBros");
		
	}
	
	@org.junit.Test
	public void testFinalEvaluation() {
		assertEquals(data.finalEvaluation(10),Optional.of(29));
		assertEquals(data.finalEvaluation(20),Optional.of(28));
		assertEquals(data.finalEvaluation(30),Optional.of(27));
		assertEquals(data.finalEvaluation(40),Optional.of(23));
		assertEquals(data.finalEvaluation(50),Optional.empty());
		assertEquals(data.finalEvaluation(60),Optional.empty());
	}
	
	
	@org.junit.Test
	public void testLabExamStudentToEvaluation() {
		assertEquals(data.labExamStudentToEvaluation(1).size(),4);
		assertEquals(data.labExamStudentToEvaluation(1).get("Mario").intValue(),30);
		assertEquals(data.labExamStudentToEvaluation(1).get("Lucia").intValue(),18);
		assertEquals(data.labExamStudentToEvaluation(1).get("Carlo").intValue(),25);
		assertEquals(data.labExamStudentToEvaluation(1).get("Anna").intValue(),16);
		assertEquals(data.labExamStudentToEvaluation(2).size(),2);
		assertEquals(data.labExamStudentToEvaluation(2).get("Lucia").intValue(),24);
		assertEquals(data.labExamStudentToEvaluation(2).get("Anna").intValue(),15);
	}
	
	@org.junit.Test
	public void testAllLabExamStudentToFinalEvaluation() {
		assertEquals(data.allLabExamStudentToFinalEvaluation().size(),4);
		assertEquals(data.allLabExamStudentToFinalEvaluation().get("Mario").intValue(),29);
		assertEquals(data.allLabExamStudentToFinalEvaluation().get("Lucia").intValue(),28);
		assertEquals(data.allLabExamStudentToFinalEvaluation().get("Carlo").intValue(),27);
		assertEquals(data.allLabExamStudentToFinalEvaluation().get("Anna").intValue(),23);	
	}
	
	@org.junit.Test
	public void optionalTestProjectEvaluation() {
		assertEquals(data.projectEvaluation("MarioBros").get("Mario").intValue(),28);
		assertEquals(data.projectEvaluation("MarioBros").get("Carlo").intValue(),28);
		assertEquals(data.projectEvaluation("Pacman").get("Lucia").intValue(),30);
		assertEquals(data.projectEvaluation("Pacman").get("Anna").intValue(),29);
		
	}
	
	
	@org.junit.Test(expected = IllegalStateException.class)
	public void optionalTestStudentDoesNotExist() {
		data.registerLabEvaluation(70, 20, 1);
	}
	
	@org.junit.Test(expected = IllegalStateException.class)
	public void optionalTestStudentShouldNotExist() {
		data.createStudent(10, "Pippo");
	}
	
	@org.junit.Test(expected = IllegalStateException.class)
	public void optionalTestCantRegisterProjectsTwice() {
		data.registerProjectEvaluation(40, 10, "Arkanoid");
	}
	
	@org.junit.Test(expected = IllegalStateException.class)
	public void testCantRegisterSameLabTwice() {
		data.registerLabEvaluation(60, 18, 3);
	}
}