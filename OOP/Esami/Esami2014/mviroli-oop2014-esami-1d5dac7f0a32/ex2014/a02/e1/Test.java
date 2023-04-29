package ex2014.a02.e1;

import static org.junit.Assert.*;
import java.util.*;

public class Test {
	
	/*
	 * Implementare l'interfaccia Scheduler data tramite una classe SchedulerImpl con costruttore senza argomenti.
	 * Questo scheduler esegue processi uno alla volta, ha una coda di processi in attesa, e una coda di processi
	 * stoppato (ossia che non possono ripartire finché non vengono risvegliati).
	 * Il commento al codice di Scheduler, e i metodi di test qui sotto costituiscono la necessaria spiegazione del 
	 * problema.
	 * Si tolga il commento ai test..
	 */

	/*
	// Questo test verifica che l'esecuzione dei processi sia circolare
	public void testExec() {  
		Scheduler<String> sch = new SchedulerImpl<>();
		sch.add("a");
		sch.add("b");
		sch.add("c");
		sch.add("d");  
		assertFalse(sch.isExecuting()); // nessun task in esecuzione al momento
		assertEquals(sch.allWaiting(),new HashSet<>(Arrays.asList("a","b","c","d"))); // task in attesa: a,b,c,d
		assertEquals(sch.allStopped(),new HashSet<>(Arrays.asList())); // task stoppati, nessuno
		
		// eseguo il primo, cioè "a"
		sch.executeNext(); 
		assertTrue(sch.isExecuting());
		assertEquals(sch.getExecutingTask(),"a");
		assertEquals(sch.allWaiting(),new HashSet<>(Arrays.asList("b","c","d"))); // task in attesa
		assertEquals(sch.allStopped(),new HashSet<>(Arrays.asList())); // task stoppati
		
		// rilascio l'esecuzione di "a", che torna in attesa
		sch.preempt();
		assertFalse(sch.isExecuting()); // nessun task in esecuzione
		assertEquals(sch.allWaiting(),new HashSet<>(Arrays.asList("a","b","c","d"))); // task in attesa
		assertEquals(sch.allStopped(),new HashSet<>(Arrays.asList())); // task stoppati
		
		// eseguo e rilascio di seguito b,c,d, poi si torna ad eseguire a, e così via
		sch.executeNext();
		assertEquals(sch.getExecutingTask(),"b");
		sch.preempt();
		sch.executeNext();
		assertEquals(sch.getExecutingTask(),"c");		
		sch.preempt();
		sch.executeNext();
		assertEquals(sch.getExecutingTask(),"d");
		sch.preempt();
		sch.executeNext();
		assertEquals(sch.getExecutingTask(),"a");
		assertTrue(sch.isExecuting()); // task in esecuzione
		assertEquals(sch.allWaiting(),new HashSet<>(Arrays.asList("b","c","d"))); // task in attesa	
	}

	// Questo test verifica che lo stopping dei processi li sposti nella giusta coda
	@org.junit.Test
	public void testStopping() {
		Scheduler<String> sch = new SchedulerImpl<>();
		sch.add("a");
		sch.add("b");
		sch.add("c");
		sch.add("d");  
		
		// eseguo il primo, cioè a
		sch.executeNext();
		assertEquals(sch.getExecutingTask(),"a");
		assertTrue(sch.isExecuting()); // nessun task in esecuzione
		assertEquals(sch.allWaiting(),new HashSet<>(Arrays.asList("b","c","d"))); // task in attesa
		assertEquals(sch.allStopped(),new HashSet<>(Arrays.asList())); // task stoppati
		
		// stoppo il task in esecuzione, che finisce nella coda stoppati
		sch.stop();
		assertFalse(sch.isExecuting()); // nessun task in esecuzione
		assertEquals(sch.allWaiting(),new HashSet<>(Arrays.asList("b","c","d"))); // task in attesa
		assertEquals(sch.allStopped(),new HashSet<>(Arrays.asList("a"))); // task stoppati
		
		// eseguo il prossimo, che è b
		sch.executeNext();
		assertEquals(sch.getExecutingTask(),"b");
		
		// stoppo il task in esecuzione, finisce nella coda stoppati
		sch.stop();
		assertFalse(sch.isExecuting()); // nessun task in esecuzione
		assertEquals(sch.allWaiting(),new HashSet<>(Arrays.asList("c","d"))); // task in attesa
		assertEquals(sch.allStopped(),new HashSet<>(Arrays.asList("a","b"))); // task stoppati
		
		// ora "a" può proseguire, passa nella coda in attesa, ma in fondo!
		sch.unStop("a");
		assertFalse(sch.isExecuting()); // nessun task in esecuzione
		assertEquals(sch.allWaiting(),new HashSet<>(Arrays.asList("a","c","d"))); // task in attesa
		assertEquals(sch.allStopped(),new HashSet<>(Arrays.asList("b"))); // task stoppati
		
		// i prossimi task ad eseguire sono c,d,a
		sch.executeNext();
		assertEquals(sch.getExecutingTask(),"c");
		sch.preempt();
		sch.executeNext();
		assertEquals(sch.getExecutingTask(),"d");
		sch.preempt();
		sch.executeNext();
		assertEquals(sch.getExecutingTask(),"a");		
		
	}
	
	// Questo test verifica l'uscita dei processi dal sistema
	@org.junit.Test
	public void testFinishing() {
		Scheduler<String> sch = new SchedulerImpl<>();
		sch.add("a");
		sch.add("b");
		sch.add("c");
		sch.add("d");  
		
		// eseguo il primo, cioè a
		sch.executeNext();
		assertEquals(sch.getExecutingTask(),"a");
		assertTrue(sch.isExecuting()); // nessun task in esecuzione
		assertEquals(sch.allWaiting(),new HashSet<>(Arrays.asList("b","c","d"))); // task in attesa
		assertEquals(sch.allStopped(),new HashSet<>(Arrays.asList())); // task stoppati
		
		// a ha completato l'esecuzione, ece dal sistema
		sch.complete();
		assertFalse(sch.isExecuting()); // nessun task in esecuzione
		assertEquals(sch.allWaiting(),new HashSet<>(Arrays.asList("b","c","d"))); // task in attesa
		assertEquals(sch.allStopped(),new HashSet<>(Arrays.asList())); // task stoppati	
	}
	
	
	@org.junit.Test
	public void testExceptions() {
		Scheduler<String> sch = new SchedulerImpl<>();
		try{
			sch.executeNext();
			fail("No task available!");
		} catch (NoSuchElementException e){
		} catch (Exception e){
			fail("Wrong exception thrown");
		}
		sch.add("a");
		try{
			sch.add("a");
			fail("Not two equivalent tasks!");
		} catch (IllegalArgumentException e){
		} catch (Exception e){
			fail("Wrong exception thrown");
		}
		try{
			sch.getExecutingTask();
			fail("No task in execution");
		} catch (NoSuchElementException e){
		} catch (Exception e){
			fail("Wrong exception thrown");
		}
		try{
			sch.stop();
			fail("No task in execution");
		} catch (IllegalStateException e){
		} catch (Exception e){
			fail("Wrong exception thrown");
		}
		try{
			sch.complete();
			fail("No task in execution");
		} catch (IllegalStateException e){
		} catch (Exception e){
			fail("Wrong exception thrown");
		}
	}
	*/
}
