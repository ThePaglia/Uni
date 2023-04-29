package a01b.sol1;

import static org.junit.Assert.*;
import java.util.*;

/**
 * Si consultino le interfacce fornite (e i test corrispondenti sotto riportati):
 * - ExamResult che modella i possibili risultati di un esame universitario
 * - ExamResultFactory che modella una factory per denotare/creare i possibili risultati d'esame
 * - ExamsManager che modella la gestione dei risultati di esame di un certo corso, negli appelli di un anno 
 * 
 * Implementare ExamResultFactory e ExamsManager attraverso le classi ExamResultFactoryImpl e ExamsManagerImpl con costruttori senza argomenti,
 * in modo che passino tutti i test di cui sotto.
 *  
 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
 * della totalità del punteggio:
 * - implementazione dei test opzionali (relativi al metodo ExamsManager.getBestResultFromStudent e ai comportamenti con eccezione)
 * - la qualità della soluzione, in particolare con minimizzazione di ripetizioni e codice non inutilmente complesso
 * 
 * Indicazioni di punteggio:
 * - correttezza della parte obbligatoria: 9 punti
 * - correttezza della parte opzionale: 3 punti
 * - qualità della soluzione: 5 punti
 * 
 * Si tolga il commento al codice del test.
 */

public class Test {
	
	private ExamResultFactory erf = new ExamResultFactoryImpl();
	private ExamsManager em = new ExamsManagerImpl();
	
	// verifica base di ExamResultFactory
	@org.junit.Test
    public void testExamResultsBasicBehaviour() {
		// esame fallito, non c'è voto
		assertEquals(erf.failed().getKind(), ExamResult.Kind.FAILED);
		assertFalse(erf.failed().getEvaluation().isPresent());
		assertFalse(erf.failed().cumLaude());
		assertEquals(erf.failed().toString(), "FAILED");
		
		// lo studente si è ritirato, non c'è voto
		assertEquals(erf.retired().getKind(), ExamResult.Kind.RETIRED);
		assertFalse(erf.retired().getEvaluation().isPresent());
		assertFalse(erf.retired().cumLaude());
		assertEquals(erf.retired().toString(), "RETIRED");
		
		// 30L
		assertEquals(erf.succeededCumLaude().getKind(), ExamResult.Kind.SUCCEEDED);
		assertEquals(erf.succeededCumLaude().getEvaluation(), Optional.of(30));
		assertTrue(erf.succeededCumLaude().cumLaude());
		assertEquals(erf.succeededCumLaude().toString(), "SUCCEEDED(30L)");
		
		// esame superato, ma non con lode
		assertEquals(erf.succeeded(28).getKind(), ExamResult.Kind.SUCCEEDED);
		assertEquals(erf.succeeded(28).getEvaluation(), Optional.of(28));
		assertFalse(erf.succeeded(28).cumLaude());
		assertEquals(erf.succeeded(28).toString(), "SUCCEEDED(28)");
    }
	
	// verifica eccezione in ExamResultFactory
	@org.junit.Test(expected = IllegalArgumentException.class)
    public void optionalTestEvaluationCantBeGreaterThan30() {
		erf.succeeded(32);
    }
	
	// verifica eccezione in ExamResultFactory
	@org.junit.Test(expected = IllegalArgumentException.class)
    public void optionalTestEvaluationCantBeSmallerThan18() {
		erf.succeeded(17);
    }
	
	// metodo di creazione di una situazione di risultati in 3 appelli
	private void prepareExams() {
		em.createNewCall("gennaio");
		em.createNewCall("febbraio");
		em.createNewCall("marzo");
		
		em.addStudentResult("gennaio", "rossi", erf.failed()); // rossi -> fallito
		em.addStudentResult("gennaio", "bianchi", erf.retired()); // bianchi -> ritirato
		em.addStudentResult("gennaio", "verdi", erf.succeeded(28)); // verdi -> 28
		em.addStudentResult("gennaio", "neri", erf.succeededCumLaude()); // neri -> 30L
		
		em.addStudentResult("febbraio", "rossi", erf.failed()); // etc..
		em.addStudentResult("febbraio", "bianchi", erf.succeeded(20));
		em.addStudentResult("febbraio", "verdi", erf.succeeded(30));
		
		em.addStudentResult("marzo", "rossi", erf.succeeded(25));
		em.addStudentResult("marzo", "bianchi", erf.succeeded(25));
		em.addStudentResult("marzo", "viola", erf.failed());
	}

	// verifica base della parte obbligatoria di ExamManager
	@org.junit.Test
    public void testExamsManagement() {
		this.prepareExams();
		// partecipanti agli appelli di gennaio e marzo
		assertEquals(em.getAllStudentsFromCall("gennaio"),new HashSet<>(Arrays.asList("rossi","bianchi","verdi","neri")));
		assertEquals(em.getAllStudentsFromCall("marzo"),new HashSet<>(Arrays.asList("rossi","bianchi","viola")));
		
		// promossi di gennaio con voto
		assertEquals(em.getEvaluationsMapFromCall("gennaio").size(),2);
		assertEquals(em.getEvaluationsMapFromCall("gennaio").get("verdi").intValue(),28);
		assertEquals(em.getEvaluationsMapFromCall("gennaio").get("neri").intValue(),30);
		// promossi di febbraio con voto
		assertEquals(em.getEvaluationsMapFromCall("febbraio").size(),2);
		assertEquals(em.getEvaluationsMapFromCall("febbraio").get("bianchi").intValue(),20);
		assertEquals(em.getEvaluationsMapFromCall("febbraio").get("verdi").intValue(),30);
		
		// tutti i risultati di rossi (attenzione ai toString!!)
		assertEquals(em.getResultsMapFromStudent("rossi").size(),3);
		assertEquals(em.getResultsMapFromStudent("rossi").get("gennaio"),"FAILED");
		assertEquals(em.getResultsMapFromStudent("rossi").get("febbraio"),"FAILED");
		assertEquals(em.getResultsMapFromStudent("rossi").get("marzo"),"SUCCEEDED(25)");
		// tutti i risultati di bianchi
		assertEquals(em.getResultsMapFromStudent("bianchi").size(),3);
		assertEquals(em.getResultsMapFromStudent("bianchi").get("gennaio"),"RETIRED");
		assertEquals(em.getResultsMapFromStudent("bianchi").get("febbraio"),"SUCCEEDED(20)");
		assertEquals(em.getResultsMapFromStudent("bianchi").get("marzo"),"SUCCEEDED(25)");
		// tutti i risultati di neri
		assertEquals(em.getResultsMapFromStudent("neri").size(),1);
		assertEquals(em.getResultsMapFromStudent("neri").get("gennaio"),"SUCCEEDED(30L)");

	}
	
	// verifica del metodo ExamManager.getBestResultFromStudent
	@org.junit.Test
    public void optionalTestExamsManagement() {
		this.prepareExams();
		// miglior voto acquisito da ogni studente, o vuoto..
		assertEquals(em.getBestResultFromStudent("rossi"),Optional.of(25));
		assertEquals(em.getBestResultFromStudent("bianchi"),Optional.of(25));
		assertEquals(em.getBestResultFromStudent("neri"),Optional.of(30));
		assertEquals(em.getBestResultFromStudent("viola"),Optional.empty());
	}
	
	
	@org.junit.Test(expected = IllegalArgumentException.class)
    public void optionalTestCantCreateACallTwice() {
		this.prepareExams();
		em.createNewCall("marzo");
    }
	
	@org.junit.Test(expected = IllegalArgumentException.class)
    public void optionalTestCantRegisterAnEvaluationTwice() {
		this.prepareExams();
		em.addStudentResult("gennaio", "verdi", erf.failed());
    }	
}
