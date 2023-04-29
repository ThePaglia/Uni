package ex2016.a02a.e1;

import static org.junit.Assert.*;
import java.util.*;

/**
 * Si consulti la documentazione della interfaccia MultiQueue, che rappresenta una coda (FIFO) multipla, come ad esempio al supermercato
 * dove ci sono più casse con code di persone in attesa (casse che aprono e chiudono). Fornisce operazioni per:
 * - aggiungere un elemento in una coda (enqueue, letto 'enchiù', modella l'arrivo di una nuova persona in una coda)
 * - rimuovere un elemento in coda (per servire in una coda la prossima persona, dequeue)
 * - aprire una nuova coda (cassa che apre)
 * - chiudere una coda esistente (cassa che chiude, e tutte le persone vanno redirette in un'altra coda)
 * - ..e altre
 * 
 * Si implementi questa interfaccia con una classe MultiQueueImpl, con costruttore vuoto.
 *
 * Il problema menziona la classe java.util.Optional<X>, i cui oggetti sono usati per contenere o meno un elemento di tipo X. Si ricorda
 * che se ne costruiscono oggetti con i metodi statici Optional.of e Optional.empty, e se ne legge il contenuto con i metodi istanza 
 * Optional.isPresent e Optional.get.
 * 
 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
 * della totalità del punteggio:
 * - implementazione dei test opzionali (relativi alla gestione delle eccezioni e alla realizzazione del metodo closeQueueAndReallocate)  
 * - la buona progettazione della soluzione
 * 
 * Si osservi con attenzione il test seguente, che assieme ai commenti delle interfacce date
 * costituisce la definizione del problema da risolvere.
 * 
 * Si tolga il commento al codice del test.
 */

public class Test {
    
    // In questi test si considerano solo multi-code con code rappresentate con stringhe, contenenti elementi interi
    // La propria soluzione deve però essere generica!
    
    /*
    @org.junit.Test
    public void testBasic() {
        // Creo le code Q1 e Q2, e metto un elemento in Q2 
        MultiQueue<Integer,String> mq = new MultiQueueImpl<>();
        assertEquals(mq.availableQueues().size(),0);
        mq.openNewQueue("Q1");
        mq.openNewQueue("Q2");
        assertEquals(mq.availableQueues(),new HashSet<>(Arrays.asList("Q1","Q2")));
        mq.enqueue(1000, "Q2");
        // Verifico quali code sono vuote
        assertTrue(mq.isQueueEmpty("Q1"));
        assertFalse(mq.isQueueEmpty("Q2"));
    }
    
    @org.junit.Test
    public void testEnqueue() {
        // Creo le code Q1 e Q2, e ci metto dentro vari elementi 
        MultiQueue<Integer,String> mq = new MultiQueueImpl<>();
        assertEquals(mq.allEnqueuedElements().size(),0);
        mq.openNewQueue("Q1");
        mq.openNewQueue("Q2");
        mq.enqueue(1000, "Q2");
        mq.enqueue(1001, "Q2");
        mq.enqueue(1002, "Q2");
        mq.enqueue(1003, "Q1");
        mq.enqueue(1004, "Q1");
        // Verifico quali elementi sono complessivamente in coda
        assertEquals(mq.allEnqueuedElements(),new HashSet<>(Arrays.asList(1000,1001,1002,1003,1004)));
    }
    
    @org.junit.Test
    public void testDequeue() {
        // Creo le code Q1 e Q2, e ci metto dentro vari elementi 
        MultiQueue<Integer,String> mq = new MultiQueueImpl<>();
        assertEquals(mq.allEnqueuedElements().size(),0);
        mq.openNewQueue("Q1");
        mq.openNewQueue("Q2");
        mq.enqueue(1000, "Q2");
        mq.enqueue(1001, "Q2");
        mq.enqueue(1002, "Q2");
        mq.enqueue(1003, "Q1");
        mq.enqueue(1004, "Q1");
        // Verifico l'ordine di rimozione degli elementi
        assertEquals(mq.dequeue("Q1"),Optional.of(1003));
        assertEquals(mq.dequeue("Q2"),Optional.of(1000));
        assertEquals(mq.dequeue("Q2"),Optional.of(1001));
        assertEquals(mq.dequeue("Q2"),Optional.of(1002));
        assertEquals(mq.dequeue("Q2"),Optional.empty());
        assertEquals(mq.dequeue("Q2"),Optional.empty());
        // Altre aggiunte e rimozioni..
        mq.enqueue(1005, "Q1");
        mq.enqueue(1006, "Q2");
        assertEquals(mq.dequeue("Q2"),Optional.of(1006));
        assertEquals(mq.allEnqueuedElements(),new HashSet<>(Arrays.asList(1004,1005)));        
    }
    

    @org.junit.Test
    public void testFullDequeue() {
        // Creo le code Q1 e Q2, e ci metto dentro vari elementi 
        MultiQueue<Integer,String> mq = new MultiQueueImpl<>();
        assertEquals(mq.allEnqueuedElements().size(),0);
        mq.openNewQueue("Q1");
        mq.openNewQueue("Q2");
        mq.enqueue(1000, "Q2");
        mq.enqueue(1001, "Q2");
        mq.enqueue(1002, "Q2");
        mq.enqueue(1003, "Q1");
        mq.enqueue(1004, "Q1");
        // Rimuovo tutti gli elementi da una coda
        assertEquals(mq.dequeueAllFromQueue("Q2"), Arrays.asList(1000,1001,1002));
        assertEquals(mq.dequeueAllFromQueue("Q2").size(),0);
    }
    
    @org.junit.Test
    public void testDequeueOneFromAll() {
        // Creo le code Q1 e Q2 e Q3, e metto in Q1 e Q2 vari elementi 
        MultiQueue<Integer,String> mq = new MultiQueueImpl<>();
        assertEquals(mq.allEnqueuedElements().size(),0);
        mq.openNewQueue("Q1");
        mq.openNewQueue("Q2");
        mq.openNewQueue("Q3"); 
        mq.enqueue(1000, "Q2");
        mq.enqueue(1001, "Q2");
        mq.enqueue(1002, "Q2");
        mq.enqueue(1003, "Q1");
        mq.enqueue(1004, "Q1");
        // Rimuovo un elemento da ogni coda
        Map<String,Optional<Integer>> map = mq.dequeueOneFromAllQueues();
        assertEquals(map.size(), 3);
        assertEquals(map.get("Q1"), Optional.of(1003));
        assertEquals(map.get("Q2"), Optional.of(1000));
        assertEquals(map.get("Q3"), Optional.empty());
    }
    
    @org.junit.Test
    public void optionalTestCloseAndReallocate() {
        // Creo le code Q1 e Q2 e Q3, e ci metto dentro vari elementi 
        MultiQueue<Integer,String> mq = new MultiQueueImpl<>();
        assertEquals(mq.allEnqueuedElements().size(),0);
        mq.openNewQueue("Q1");
        mq.openNewQueue("Q2");
        mq.openNewQueue("Q3"); 
        mq.enqueue(1000, "Q2");
        mq.enqueue(1001, "Q2");
        mq.enqueue(1002, "Q2");
        mq.enqueue(1003, "Q1");
        mq.enqueue(1004, "Q1");
        mq.enqueue(1005, "Q3");
        assertEquals(mq.availableQueues(),new HashSet<>(Arrays.asList("Q1","Q2","Q3")));
        assertEquals(mq.allEnqueuedElements(),new HashSet<>(Arrays.asList(1000,1001,1002,1003,1004,1005)));    
        // Chiudo Q1, i suoi elementi devono finire in qualche altra coda
        mq.closeQueueAndReallocate("Q1");
        assertEquals(mq.allEnqueuedElements(),new HashSet<>(Arrays.asList(1000,1001,1002,1003,1004,1005)));
        assertEquals(mq.availableQueues(),new HashSet<>(Arrays.asList("Q2","Q3")));
    }
    
    @org.junit.Test
    public void optionalTestExceptions() {
        // Testo e le varie eccezioni
        MultiQueue<Integer,String> mq = new MultiQueueImpl<>();
        assertEquals(mq.allEnqueuedElements().size(),0);
        mq.openNewQueue("Q1");
        mq.openNewQueue("Q3");
        mq.enqueue(1000, "Q1");
        try{
            mq.openNewQueue("Q1");
            fail("can't open Q1 again");
        } catch (IllegalArgumentException e){}
          catch (Exception e){
            fail("wrong exception thrown");
        }
        try{
            mq.isQueueEmpty("Q2");
            fail("can't query a non-existing queue");
        } catch (IllegalArgumentException e){}
          catch (Exception e){
            fail("wrong exception thrown");
        }
        try{
            mq.enqueue(100,"Q2");
            fail("can't add into a non-existing queue");
        } catch (IllegalArgumentException e){}
          catch (Exception e){
            fail("wrong exception thrown");
        }
        try{
            mq.dequeue("Q2");
            fail("can't remove from a non-existing queue");
        } catch (IllegalArgumentException e){}
          catch (Exception e){
            fail("wrong exception thrown");
        }
        try{
            mq.dequeueAllFromQueue("Q2");
            fail("can't remove from a non-existing queue");
        } catch (IllegalArgumentException e){}
          catch (Exception e){
            fail("wrong exception thrown");
        }
        try{
            mq.closeQueueAndReallocate("Q2");
            fail("can't remove from a non-existing queue");
        } catch (IllegalArgumentException e){}
          catch (Exception e){
            fail("wrong exception thrown");
        }
        mq.closeQueueAndReallocate("Q3");
        // Ok, but now we have only Q1 left.
        try{
            mq.closeQueueAndReallocate("Q1");
            fail("can't close if there's no other queue");
        } catch (IllegalStateException e){}
          catch (Exception e){
            fail("wrong exception thrown");
        }
    }
    */
}
