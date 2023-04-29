package a03a.sol1;

import static org.junit.Assert.*;
import java.util.*;

/**
 * Si consulti la documentazione delle interfacce fornite:
 * - Evaluation modella i risultati del questionario studenti di UNIBO per un certo insieme di corsi
 * - EvaluationBuilder modella un builder per Evaluation, che consente di caricare tutti i dati prima di interpellare i metodi di ricerca 
 * 
 * Implementare EvaluationBuilder attraverso una classe EvaluationBuilderImpl con costruttore senza argomenti,
 * in modo che passi tutti i test di cui sotto, realizzati per essere autoesplicativi.
 *  
 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
 * della totalità del punteggio:
 * - implementazione dei test opzionali (relativi ad alcuni controlli per le eccezioni e al metodo coursePositiveResultsRatio)
 * - la qualità della soluzione, in particolare con minimizzazione di ripetizioni e codice non inutilmente complesso
 * 
 * Indicazioni di punteggio:
 * - correttezza della parte obbligatoria: 9 punti
 * - correttezza della parte opzionale: 4 punti
 * - qualità della soluzione: 4 punti
 * 
 * Si tolga il commento al codice del test.
 */

import a03a.sol1.Evaluation.*;
import static a03a.sol1.Evaluation.Result.*;

public class Test {
	
	private EvaluationBuilder builder;
	private Evaluation ev;
	
	// questo metodo istanzia il builder e lo usa per caricare vari questionari studenti 
	@org.junit.Before
	public void init() {
		this.builder = new EvaluationBuilderImpl();
		// lo studente con matricola 1 compila il questionario per il corso "PPS"
		// .. dando "decidamente si" a "complessivamente soddisfatto" e "qualità del docente"
		// .. e "più si che no" a "sei interessato al corso"
		// e simile per gli altri
		builder.addEvaluationByResults("PPS", 1, FULLY_POSITIVE, WEAKLY_POSITIVE, FULLY_POSITIVE)
			   .addEvaluationByResults("PPS", 2, WEAKLY_POSITIVE, WEAKLY_POSITIVE, WEAKLY_NEGATIVE)
		       .addEvaluationByResults("PPS", 3, FULLY_POSITIVE, FULLY_POSITIVE, FULLY_POSITIVE)
		       .addEvaluationByResults("LPMC", 1, FULLY_POSITIVE, FULLY_POSITIVE, FULLY_POSITIVE)
		       .addEvaluationByResults("LPMC", 2, FULLY_NEGATIVE, FULLY_NEGATIVE, FULLY_NEGATIVE)
		       .addEvaluationByResults("LPMC", 3, FULLY_POSITIVE, FULLY_POSITIVE, FULLY_NEGATIVE);
		// carico un altro questionario, con diversa ma equivalente modalità
		Map<Question,Result> results = new EnumMap<>(Question.class);
		results.put(Question.OVERALL, FULLY_POSITIVE);
		results.put(Question.INTEREST, FULLY_NEGATIVE);
		results.put(Question.CLARITY, WEAKLY_POSITIVE);
		builder.addEvaluationByMap("LPMC", 4, results); // studente 4 sul corso "LPMC"
		this. ev = builder.build();
	}
	
	@org.junit.Test
    public void testResults() {
		// lo studente 1, sul corso "PPS", ha risposto "decidamente si" alla domanda "sei complessivamente soddisfatto"
		assertEquals(ev.results("PPS", 1).get(Question.OVERALL),FULLY_POSITIVE);
		// etc..
		assertEquals(ev.results("LPMC", 3).get(Question.CLARITY),FULLY_NEGATIVE);
		assertEquals(ev.results("LPMC", 4).get(Question.INTEREST),FULLY_NEGATIVE);
		assertTrue(ev.results("LPMC", 5).isEmpty());
	}
	
	@org.junit.Test
    public void testResultsCountForCourseAndCriterion() {
		// il corso "PPS" ha ricevuto due risposte "decidamente si" alla domanda "sei complessivamente soddisfatto"
		assertEquals(ev.resultsCountForCourseAndQuestion("PPS", Question.OVERALL).get(FULLY_POSITIVE).longValue(),2);
		// etc..
		assertEquals(ev.resultsCountForCourseAndQuestion("PPS", Question.OVERALL).get(WEAKLY_POSITIVE).longValue(),1);
		assertEquals(ev.resultsCountForCourseAndQuestion("LPMC", Question.INTEREST).get(FULLY_POSITIVE).longValue(),2);
		assertEquals(ev.resultsCountForCourseAndQuestion("LPMC", Question.INTEREST).get(FULLY_NEGATIVE).longValue(),2);
	}
	
	@org.junit.Test
    public void testResultsCountForStudent() {
		// lo student con matricola 1 ha risposto per 5 volte "decidamente si" in tutti i questionari che ha compilato
		assertEquals(ev.resultsCountForStudent(1).get(FULLY_POSITIVE).longValue(),5);
		// etc..
		assertEquals(ev.resultsCountForStudent(1).get(WEAKLY_POSITIVE).longValue(),1);
		assertEquals(ev.resultsCountForStudent(4).get(FULLY_POSITIVE).longValue(),1);
		assertEquals(ev.resultsCountForStudent(4).get(WEAKLY_POSITIVE).longValue(),1);
		assertEquals(ev.resultsCountForStudent(4).get(FULLY_NEGATIVE).longValue(),1);
	}
	
	@org.junit.Test(expected = IllegalStateException.class)
	public void testCantBuildTwice() {
		builder.build(); // già chiamato build..
    }
	
	// un giudizio è positivo se "decisamente si" o "più si che no"
	@org.junit.Test
    public void optionalTestCoursePositiveResultsRatio() {
		// il corso "PPS" ha il 100% (ossia 1.0, con scarto massimo 0.01) di giudizi positivi alla domanda "sei decidamente soddisfatto"
		assertEquals(ev.coursePositiveResultsRatio("PPS", Question.OVERALL),1.0,0.01); // 0.01 of accuracy 
		// il corso "PPS" ha il 67% (ossia 1.0, con scarto massimo 0.01) di giudizi positivi alla domanda "il docente spiega in modo chiaro"
		assertEquals(ev.coursePositiveResultsRatio("PPS", Question.CLARITY),0.67,0.01); // 0.01 of accuracy
		// etc..
		assertEquals(ev.coursePositiveResultsRatio("LPMC", Question.OVERALL),0.75,0.01); // 0.01 of accuracy
	}
	
	@org.junit.Test(expected = IllegalArgumentException.class)
	public void optionalTestIncompleteResults() {
		builder.addEvaluationByMap("LPMC", 10, new HashMap<>()); // risultati vuoti, o comunque incompleti
    }
	
	@org.junit.Test(expected = IllegalStateException.class)
	public void optionalTestResultAlreadyLoaded() {
		Map<Question,Result> results = new EnumMap<>(Question.class);
		results.put(Question.OVERALL, FULLY_POSITIVE);
		results.put(Question.INTEREST, FULLY_NEGATIVE);
		results.put(Question.CLARITY, WEAKLY_POSITIVE);
		builder.addEvaluationByMap("LPMC", 4, results); // già caricato i dati dello studente 4 per LPMC
    }
}
