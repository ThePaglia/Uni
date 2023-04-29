package a02b.sol1;

import static org.junit.Assert.*;
import java.util.*;
import static a02b.sol1.ExamsFactory.SimpleExamActivities.*;
import static a02b.sol1.ExamsFactory.OOPExamActivities.*;

public class Test {
	
	/*
	 * Implementare l'interfaccia ExamsFactory come indicato nel metodo initFactory qui sotto.
	 * Realizza un concetto di factory per ExamCourse<A>, che a loro volta modella le attività (di tipo A) da eseguire
	 * per passare un esame universitario, e le dipendenze fra loro:
	 * - ogni attività può essere eseguita una volta sola
	 * - quando tutte le attività sono eseguite l'esame è terminato
	 * - esistono specifici vincoli sul quando una attività può essere eseguita, espressi in termini dell'avvenuta esecuzione di altre attività
	 * Ad esempio, una modalità d'esame comune prevede prima lo scritto, poi l'orale, e quindi la registrazione del voto; in altre (come OOP) ci 
	 * sono invece prove diverse realizzabili in qualunque ordine (progetto e pratico sono in parallelo, e solo quando ho svolto entrambi posso registrare).
	 * 
	 * Il commento alle interfacce fornite, e i metodi di test qui sotto costituiscono la necessaria spiegazione del 
	 * problema.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
	 * della totalità del punteggio:
	 * - implementazione dei test chiamati optionalTestXYZ (relativi al metodo ExamsFactory.fullOopExam) 
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

	
	private ExamsFactory wf = null;
	
	@org.junit.Before
	public void initFactory() {
		this.wf = new ExamsFactoryImpl();
	}

	@org.junit.Test
	public void testSimpleExam() {
		// Un esame in qui prima va fatto "WRITTEN", poi "ORAL", infine "REGISTER"
		final CourseExam<ExamsFactory.SimpleExamActivities> w = this.wf.simpleExam();
				
		assertEquals(Set.of(WRITTEN,ORAL,REGISTER),w.activities()); // le attività da svolgere		
		assertFalse(w.examOver());									// l'esame non è finito
		assertEquals(Set.of(WRITTEN),w.getPendingActivities());		// le prossime attività da svolgere
		
		// completo WRITTEN, esame non ancora finito, il prossimo è ORAL
		w.completed(WRITTEN);
		assertFalse(w.examOver());
		assertEquals(Set.of(ORAL),w.getPendingActivities());
		
		// completo ORAL, esame non ancora finito, il prossimo è REGISTER
		w.completed(ORAL);
		assertFalse(w.examOver());
		assertEquals(Set.of(REGISTER),w.getPendingActivities());
		
		// completo REGISTER, esame finito
		w.completed(REGISTER);
		assertTrue(w.examOver());
		assertEquals(Set.of(),w.getPendingActivities());
	}
	
	@org.junit.Test
	public void testSimpleOOPExam() { 
		// Un esame tipo OOP
		final CourseExam<ExamsFactory.OOPExamActivities> w = this.wf.simpleOopExam();
				
		assertEquals(Set.of(LAB_REGISTER,LAB_EXAM,PROJ_PROPOSE,PROJ_SUBMIT,PROJ_EXAM,FINAL_EVALUATION),w.activities());		
		assertFalse(w.examOver());
		assertEquals(Set.of(LAB_REGISTER,PROJ_PROPOSE),w.getPendingActivities());
		
		// completo LAB_REGISTER, esame non ancora finito, ora devo fare LAB_EXAM, ma anche PROJ_PROPOSE
		w.completed(LAB_REGISTER);
		assertFalse(w.examOver());
		assertEquals(Set.of(LAB_EXAM,PROJ_PROPOSE),w.getPendingActivities());
		
		// completo PROJ_PROPOSE, esame non ancora finito, ora devo fare LAB_EXAM, ma anche PROJ_SUBMIT
		w.completed(PROJ_PROPOSE);
		assertFalse(w.examOver());
		assertEquals(Set.of(LAB_EXAM,PROJ_SUBMIT),w.getPendingActivities());
		
		// completo LAB_EXAM, esame non ancora finito, ora devo fare PROJ_SUBMIT
		w.completed(LAB_EXAM);
		assertFalse(w.examOver());
		assertEquals(Set.of(PROJ_SUBMIT),w.getPendingActivities());
		
		// completo PROJ_SUBMIT, esame non ancora finito, ora devo fare PROJ_EXAM
		w.completed(PROJ_SUBMIT);
		assertFalse(w.examOver());
		assertEquals(Set.of(PROJ_EXAM),w.getPendingActivities());
		
		// completo PROJ_EXAM, esame non ancora finito, ora devo fare FINAL_EVALUATION
		w.completed(PROJ_EXAM);
		assertFalse(w.examOver());
		assertEquals(Set.of(FINAL_EVALUATION),w.getPendingActivities());
		
		// completo PROJ_EXAM, esame finito
		w.completed(FINAL_EVALUATION);
		assertTrue(w.examOver());
		assertEquals(Set.of(),w.getPendingActivities());
	}
	
	@org.junit.Test
	public void optionalTestFullOOPExam1() {
		// Una variante estesa dell'esame OOP di cui sopra
		final CourseExam<ExamsFactory.OOPExamActivities> w = this.wf.fullOopExam();
		
		// in più: STUDY e CSHARP_TASK.. si parte con STUDY
		assertEquals(Set.of(STUDY,LAB_REGISTER,LAB_EXAM,PROJ_PROPOSE,PROJ_SUBMIT,PROJ_EXAM,CSHARP_TASK,FINAL_EVALUATION),w.activities());		
		assertFalse(w.examOver());
		assertEquals(Set.of(STUDY),w.getPendingActivities());
		
		w.completed(STUDY);
		assertFalse(w.examOver());
		assertEquals(Set.of(LAB_REGISTER,PROJ_PROPOSE),w.getPendingActivities());
		
		w.completed(LAB_REGISTER);
		assertFalse(w.examOver());
		assertEquals(Set.of(LAB_EXAM,PROJ_PROPOSE),w.getPendingActivities());
		
		w.completed(PROJ_PROPOSE);
		assertFalse(w.examOver());
		assertEquals(Set.of(LAB_EXAM,PROJ_SUBMIT),w.getPendingActivities());
		
		w.completed(LAB_EXAM);
		assertFalse(w.examOver());
		assertEquals(Set.of(PROJ_SUBMIT),w.getPendingActivities());
		
		// Sottoposto il progetto, ora si può lavorare anche al task Csharp
		w.completed(PROJ_SUBMIT);
		assertFalse(w.examOver());
		assertEquals(Set.of(PROJ_EXAM,CSHARP_TASK),w.getPendingActivities());
		
		// Completato il task Csharp, rimane solo PROJ_EXAM1
		w.completed(CSHARP_TASK);
		assertFalse(w.examOver());
		assertEquals(Set.of(PROJ_EXAM),w.getPendingActivities());
		
		w.completed(PROJ_EXAM);
		assertFalse(w.examOver());
		assertEquals(Set.of(FINAL_EVALUATION),w.getPendingActivities());
		
		w.completed(FINAL_EVALUATION);
		assertTrue(w.examOver());
		assertEquals(Set.of(),w.getPendingActivities());
	}
	
	@org.junit.Test
	public void optionalTestFullOOPExam2() {
		// Come prima, ma ora si esegue il task C# dopo aver completato il progetto
		final CourseExam<ExamsFactory.OOPExamActivities> w = this.wf.fullOopExam();
		
		// in più: STUDY e CSHARP_TASK.. si parte con STUDY
		assertEquals(Set.of(STUDY,LAB_REGISTER,LAB_EXAM,PROJ_PROPOSE,PROJ_SUBMIT,PROJ_EXAM,CSHARP_TASK,FINAL_EVALUATION),w.activities());		
		assertFalse(w.examOver());
		assertEquals(Set.of(STUDY),w.getPendingActivities());
		
		w.completed(STUDY);
		assertFalse(w.examOver());
		assertEquals(Set.of(LAB_REGISTER,PROJ_PROPOSE),w.getPendingActivities());
		
		w.completed(LAB_REGISTER);
		assertFalse(w.examOver());
		assertEquals(Set.of(LAB_EXAM,PROJ_PROPOSE),w.getPendingActivities());
		
		w.completed(PROJ_PROPOSE);
		assertFalse(w.examOver());
		assertEquals(Set.of(LAB_EXAM,PROJ_SUBMIT),w.getPendingActivities());
		
		w.completed(LAB_EXAM);
		assertFalse(w.examOver());
		assertEquals(Set.of(PROJ_SUBMIT),w.getPendingActivities());
		
		// Sottoposto il progetto, ora si può lavorare anche al task Csharp
		w.completed(PROJ_SUBMIT);
		assertFalse(w.examOver());
		assertEquals(Set.of(PROJ_EXAM,CSHARP_TASK),w.getPendingActivities());
		
		// Completato il progetto, rimane il task csharp 
		w.completed(PROJ_EXAM);
		assertFalse(w.examOver());
		assertEquals(Set.of(CSHARP_TASK),w.getPendingActivities());
		
		w.completed(CSHARP_TASK);
		assertFalse(w.examOver());
		assertEquals(Set.of(FINAL_EVALUATION),w.getPendingActivities());
				
		
		w.completed(FINAL_EVALUATION);
		assertTrue(w.examOver());
		assertEquals(Set.of(),w.getPendingActivities());
	}
}

