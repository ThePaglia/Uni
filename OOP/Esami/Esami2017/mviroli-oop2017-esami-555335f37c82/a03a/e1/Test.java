package a03a.e1;

import static org.junit.Assert.*;
import java.util.*;

public class Test {
	
	/*
	 * Implementare l'interfaccia ExamsManagement fornita tramite una classe ExamsManagementImpl con costruttore senza argomenti.
	 * Realizza un concetto di gestore degli appelli (chiamati Exam) di un corso universitario.
	 * Fornisce funzionalità per:
	 * - creare studenti e appelli (gli appelli hanno un oridne temporale segnato dal loro id numerico); 
	 * - registrare studenti ad appelli;
	 * - per far partire un appello (rispettando l'ordine temporale), registrare voti per questo appello, e poi terminare l'appello;
	 * - per ottenere informazioni storiche
	 * 
	 * Si noti che i test con annotazione @org.junit.Test(expected = ...) passano solo se viene lanciata l'eccezione indicata nei ...
	 * Si noti che i test con annotazione @org.junit.Test passano se non viene lanciata alcuna eccezione
	 * Si noti che i metodi con annotazione @org.junit.Before vengono eseguiti sempre prima di aver eseguito ogni test, e servono
	 * a preparare una configurazione "nuova" (sempre la stessa) prima di eseguire un test.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
	 * della totalità del punteggio:
	 * - implementazione dei test chiamati optionalTestXYZ (relativi al metodo examEvaluationToCount e alla gestione delle eccezioni) 
	 * - la buona progettazione della soluzione, in particolare con minimizzazione di ripetizioni
	 * 
	 */
	
	/* Si tolga questo commento!
	private ExamsManagement data;
	
	@org.junit.Before
	public void createData() {
		// inizializzazione prima di eseguire un qualunque test
		this.data = new ExamsManagementImpl();
		data.createStudent(10, "Mario");
		data.createStudent(20, "Lucia");
		data.createStudent(30, "Carlo");
		data.createStudent(40, "Anna");
		data.createStudent(50, "Ugo");
		data.createStudent(60, "Silvia"); // creo 6 studenti
		data.createExam("01-Gennaio-2018", 201801);
		data.createExam("02-Febbraio-2018", 201802);
		data.createExam("03-Febbraio-2018", 201803); // creo 3 appelli con id incrementale
		data.registerStudent("01-Gennaio-2018", 10);
		data.registerStudent("01-Gennaio-2018", 20);
		data.registerStudent("01-Gennaio-2018", 30);
		data.registerStudent("01-Gennaio-2018", 40); 
		data.registerStudent("02-Febbraio-2018", 50);
		data.registerStudent("02-Febbraio-2018", 10); // registro 4 studenti al primo appello, 2 al secondo
		data.examStarted("01-Gennaio-2018"); // faccio partire il primo appello
		data.registerEvaluation(10, 18);
		data.registerEvaluation(40, 25);
		data.registerEvaluation(20, 25); // registro 3 voti (18,25,25) a 3 studenti (con id/matricola 10,40,20)
		data.examFinished(); // concludo l'appello
		data.examStarted("02-Febbraio-2018"); // faccio partire il secondo appello.. etc..
		data.registerEvaluation(10, 30);
		data.registerEvaluation(50, 28);
		data.examFinished();
	}
	
	@org.junit.Test
	public void testExamList() {
		// verifiche su id degli studenti iscritti ad ogni appello
		assertEquals(data.examList("01-Gennaio-2018"),new HashSet<>(Arrays.asList(10,20,30,40)));
		assertEquals(data.examList("02-Febbraio-2018"),new HashSet<>(Arrays.asList(50,10)));
		assertEquals(data.examList("03-Febbraio-2018"),new HashSet<>(Arrays.asList()));
	}
	
	@org.junit.Test
	public void testLastEvaluation() {
		// verifiche sull'ultimo voto conseguito da vari studenti (indicati per id/matricola)
		assertEquals(data.lastEvaluation(10),Optional.of(30));
		assertEquals(data.lastEvaluation(20),Optional.of(25));
		assertEquals(data.lastEvaluation(30),Optional.empty());
		assertEquals(data.lastEvaluation(40),Optional.of(25));
		assertEquals(data.lastEvaluation(50),Optional.of(28));
	}
	
	@org.junit.Test
	public void testExamStudentToEvaluation() {
		// verifiche sui voti conseguiti ad un certo appello, ritrovati per nome appello e quindi nome studente
		assertEquals(data.examStudentToEvaluation("01-Gennaio-2018").size(),3);
		assertEquals(data.examStudentToEvaluation("01-Gennaio-2018").get("Mario").intValue(),18);
		assertEquals(data.examStudentToEvaluation("01-Gennaio-2018").get("Anna").intValue(),25);
		assertEquals(data.examStudentToEvaluation("02-Febbraio-2018").size(),2);
		assertEquals(data.examStudentToEvaluation("02-Febbraio-2018").get("Mario").intValue(),30);
		assertEquals(data.examStudentToEvaluation("02-Febbraio-2018").get("Ugo").intValue(),28);
	}
	
	@org.junit.Test
	public void optionalTestEvaluationToCount() {
		// verifiche su quanti studenti hanno preso un certo voto in un certo appello
		assertEquals(data.examEvaluationToCount("01-Gennaio-2018").get(25).intValue(),2); // 2 studenti hanno preso 25 nell'appello di gennaio 2018
		assertEquals(data.examEvaluationToCount("01-Gennaio-2018").get(18).intValue(),1);
		assertEquals(data.examEvaluationToCount("02-Febbraio-2018").get(30).intValue(),1);
		assertEquals(data.examEvaluationToCount("02-Febbraio-2018").get(28).intValue(),1);
	}
	
	@org.junit.Test(expected = IllegalStateException.class)
	public void optionalTestExamOrderedStarting() {
		data.createExam("03-Febbraio-2017", 201703); 
		data.examStarted("03-Febbraio-2017"); // non si può far partire un appello precedente a uno già concluso
	}
	
	@org.junit.Test(expected = IllegalStateException.class)
	public void optionalTestExamNotFinished() {
		data.createExam("04-Giugno-2018", 201804);
		data.examStarted("03-Febbraio-2018");
		data.examStarted("04-Giugno-2018"); // non si può far partire un appello prima di averne concluso uno precedente
	}
	
	@org.junit.Test(expected = IllegalStateException.class)
	public void optionalTestExamNotStarted() {
		data.examFinished(); // non si può concludere un appello non fatto partire
	}
	
	@org.junit.Test(expected = IllegalStateException.class)
	public void optionalTestExamNotStarted2() {
		data.registerEvaluation(50, 28); // non si può registrare un voto se non ci sono appelli aperti
	}
	*/
}

