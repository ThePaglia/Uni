package a02a.sol1;

import static org.junit.Assert.*;
import java.util.*;

public class Test {
	
	/*
	 * Implementare l'interfaccia WorkflowsFactory come indicato nel metodo initFactory qui sotto.
	 * Realizza un concetto di factory per workflow, modellati dall'interfaccia Workflow<T>. Un Workflow è
	 * un insieme di task (di tipo T) da eseguire:
	 * - ognuno può essere eseguito una volta sola
	 * - quando tutti sono eseguiti il workflow è terminato
	 * - esistono specifici vincoli sul quando un task può essere eseguito, espressi in termini dell'avvenuta esecuzione di altri task
	 * 
	 * Il commento alle interfacce fornite, e i metodi di test qui sotto costituiscono la necessaria spiegazione del 
	 * problema.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
	 * della totalità del punteggio:
	 * - implementazione dei test chiamati optionalTestXYZ (relativi ai metodi WorkflowsFactory.concat() e alla gestione degli errori) 
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
	
	
	private WorkflowsFactory wf = null;
	
	@org.junit.Before
	public void initFactory() {
		this.wf = new WorkflowsFactoryImpl();
	}

	@org.junit.Test
	public void testSingleTask() {
		// un workflow costituito dal solo task 'a'
		final Workflow<String> w = this.wf.singleTask("a");
		
		assertEquals(Set.of("a"),w.getTasks()); 		// 'a' è l'unico task		
		assertFalse(w.isCompleted());					// il worflow non è completato
		assertEquals(Set.of("a"),w.getNextTasksToDo()); // 'a' è il solo prossimo task da eseguire
		
		w.doTask("a");									// eseguo 'a'
		assertTrue(w.isCompleted()); 					// il worflow è completato
		assertEquals(Set.of(),w.getNextTasksToDo()); 	// non ci sono più task da eseguire
	}

	
	@org.junit.Test
	public void testTaskSequence() { 
		// un workflow che prevede si eseguire 'a', poi 'b', poi 'c', poi 'd' (in sintesi: a->b;b->c;c->d)
		final Workflow<String> w = this.wf.tasksSequence(List.of("a","b","c","d"));
		assertEquals(Set.of("a","b","c","d"),w.getTasks());		
		assertFalse(w.isCompleted());
		assertEquals(Set.of("a"),w.getNextTasksToDo());
		
		// eseguito 'a' poi non avrò finito, dovrò eseguire 'b'
		w.doTask("a");
		assertFalse(w.isCompleted());
		assertEquals(Set.of("b"),w.getNextTasksToDo());
		
		// eseguito 'b' poi non avrò finito, dovrò eseguire 'c'
		w.doTask("b");
		assertFalse(w.isCompleted());
		assertEquals(Set.of("c"),w.getNextTasksToDo());
		
		// eseguito 'c' poi non avrò finito, dovrò eseguire 'd'
		w.doTask("c");
		assertFalse(w.isCompleted());
		assertEquals(Set.of("d"),w.getNextTasksToDo());
		
		// eseguito 'd' il workflow è terminato
		w.doTask("d");
		assertTrue(w.isCompleted());
		assertEquals(Set.of(),w.getNextTasksToDo());
	}
	
	@org.junit.Test
	public void testTaskJoin() { 
		// un workflow che prevede di eseguire '10', '20' e '30' (in qualunque ordine), e solo dopo '0' (in sintesi: 10->0;20->0;30->0)
		final Workflow<Integer> w = this.wf.tasksJoin(Set.of(10,20,30),0);
		assertEquals(Set.of(10,20,30,0),w.getTasks());		
		assertFalse(w.isCompleted());
		assertEquals(Set.of(10,20,30),w.getNextTasksToDo());
		
		// eseguito '30' poi non avrò finito, dovrò eseguire '10' e '20'
		w.doTask(30);
		assertFalse(w.isCompleted());
		assertEquals(Set.of(10,20),w.getNextTasksToDo());
		
		// eseguito '10' poi non avrò finito, dovrò eseguire '20'
		w.doTask(10);
		assertFalse(w.isCompleted());
		assertEquals(Set.of(20),w.getNextTasksToDo());
		
		// eseguito '20' poi non avrò finito, dovrò eseguire '0'
		w.doTask(20);
		assertFalse(w.isCompleted());
		assertEquals(Set.of(0),w.getNextTasksToDo());
		
		// eseguito '0' il workflow è terminato
		w.doTask(0);
		assertTrue(w.isCompleted());
		assertEquals(Set.of(),w.getNextTasksToDo());
	}
	
	@org.junit.Test
	public void testTaskFork() { 
		// un workflow che prevede di eseguire '0', e quindi '10', '20' e '30' in qualunque ordine (in sintesi: 0->10;0->20;0->30)
		final Workflow<Integer> w = this.wf.tasksFork(0,Set.of(10,20,30));
		assertEquals(Set.of(10,20,30,0),w.getTasks());		
		assertFalse(w.isCompleted());
		assertEquals(Set.of(0),w.getNextTasksToDo());
		
		// eseguito '0' poi non avrò finito, dovrò eseguire '10', '20' e '30'
		w.doTask(0);
		assertFalse(w.isCompleted());
		assertEquals(Set.of(10,20,30),w.getNextTasksToDo());
		
		// eseguito '10' poi non avrò finito, dovrò eseguire '20' e '30'
		w.doTask(10);
		assertFalse(w.isCompleted());
		assertEquals(Set.of(20,30),w.getNextTasksToDo());
		
		// eseguito '30' poi non avrò finito, dovrò eseguire '20'
		w.doTask(30);
		assertFalse(w.isCompleted());
		assertEquals(Set.of(20),w.getNextTasksToDo());
		
		// eseguito '20' il workflow è terminato
		w.doTask(20);
		assertTrue(w.isCompleted());
		assertEquals(Set.of(),w.getNextTasksToDo());
	}
	
	@org.junit.Test
	public void optionalTestConcatenation() { 
		final Workflow<Integer> w1 = this.wf.tasksJoin(Set.of(10,20),0); 		// 10->0, 20->0
		final Workflow<Integer> w2 = this.wf.tasksSequence(List.of(1,2));		// 1->2
		// la concatenazione di w1 e w2, ossia finito w1 parte w2				// 10->0, 20->0, 0->1, 1->2
		final Workflow<Integer> w = this.wf.concat(w1, w2);
				
		assertEquals(Set.of(10,20,0,1,2),w.getTasks());		
		assertFalse(w.isCompleted());
		assertEquals(Set.of(10,20),w.getNextTasksToDo());
		
		w.doTask(10);
		assertFalse(w.isCompleted());
		assertEquals(Set.of(20),w.getNextTasksToDo());
		
		w.doTask(20);
		assertFalse(w.isCompleted());
		assertEquals(Set.of(0),w.getNextTasksToDo());
		
		w.doTask(0);
		assertFalse(w.isCompleted());
		assertEquals(Set.of(1),w.getNextTasksToDo());
		
		w.doTask(1);
		assertFalse(w.isCompleted());
		assertEquals(Set.of(2),w.getNextTasksToDo());
		
		w.doTask(2);
		assertTrue(w.isCompleted());
		assertEquals(Set.of(),w.getNextTasksToDo());
	}
	
	@org.junit.Test(expected = IllegalStateException.class)
	public void optionalTestWrongTask() { 
		final Workflow<String> w = this.wf.tasksSequence(List.of("a","b","c","d"));
		assertEquals(Set.of("a","b","c","d"),w.getTasks());		
		assertFalse(w.isCompleted());
		assertEquals(Set.of("a"),w.getNextTasksToDo());
				
		w.doTask("a");
		assertFalse(w.isCompleted());
		assertEquals(Set.of("b"),w.getNextTasksToDo());
		
		// NON E' IL MOMENTO DI ESEGUIRE 'c': IllegalStateException 
		w.doTask("c");
	}
	
	@org.junit.Test(expected = IllegalArgumentException.class)
	public void optionalTestUnknownTask() { 
		final Workflow<String> w = this.wf.tasksSequence(List.of("a","b","c","d"));
		assertEquals(Set.of("a","b","c","d"),w.getTasks());		
		assertFalse(w.isCompleted());
		assertEquals(Set.of("a"),w.getNextTasksToDo());
				
		w.doTask("a");
		assertFalse(w.isCompleted());
		assertEquals(Set.of("b"),w.getNextTasksToDo());
				
		// 'pippo' non è un task eseguibile: IllegalArgumentException 
		w.doTask("pippo");
	}
	
}

