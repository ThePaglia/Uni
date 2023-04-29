package pr2016.a06.e1;

import static org.junit.Assert.*;
import java.util.*;

/**
 * Si consulti la documentazione della interfaccia IntIteratorsFactory: modella una factory per
 * creare vari oggetti di tipo java.util.Iterator<Integer>. 
 * Si implementi la factory con una classe IntIteratorsFactoryImpl in modo che passi i test qui sotto.
 *  
 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
 * della totalità del punteggio:
 * - implementazione del test opzionale (l'ultimo, denominato 'optionalTestAlternateOneAndZeroIndefinitely') 
 * - minimizzazione di ripetizioni
 * - minimizzazione di sprechi di memoria, allocando oggetti di possibili grosse dimensioni inutilmente
 * 
 * Si osservi con attenzione il test seguente, che assieme ai commenti delle interfacce date
 * costituisce la definizione del problema da risolvere.
 * 
 * Si tolga il commento al codice del test.
 */


public class Test {
    
    /*
     * Una funzionalità di utilità per il test sotto, che converte un iteratore (finito) in lista
     */
    private <T> List<T> iteratorToList(Iterator<T> it){
        final List<T> l = new LinkedList<T>();
        while (it.hasNext()){
            l.add(it.next());
        }
        return l;
    }

    /*
    @org.junit.Test
    public void testEmpty() {
        // Funzionamento di un iteratore vuoto
        final IntIteratorsFactory factory = new IntIteratorsFactoryImpl();
        Iterator<Integer> empty = factory.empty();
        assertFalse(empty.hasNext());
    }
    
    @org.junit.Test
    public void testZeros() {
        // Funzionamento di un iteratore di zeri
        final IntIteratorsFactory factory = new IntIteratorsFactoryImpl();
        final Iterator<Integer> zeros = factory.zeros();
        assertTrue(zeros.hasNext());
        assertSame(zeros.next(),0);
        assertTrue(zeros.hasNext());
        assertSame(zeros.next(),0);
        assertTrue(zeros.hasNext());
        assertSame(zeros.next(),0);
        assertTrue(zeros.hasNext());
        assertSame(zeros.next(),0);
        assertTrue(zeros.hasNext());
        // ad infinitum
    }
    
    @org.junit.Test
    public void testFromTo() {
        // Funzionamento di un iteratore da 1 a 5
        final IntIteratorsFactory factory = new IntIteratorsFactoryImpl();
        final Iterator<Integer> oneToFive = factory.fromTo(1, 5);
        assertEquals(Arrays.asList(1,2,3,4,5),iteratorToList(oneToFive));
    }
    

    @org.junit.Test
    public void testFromList() {
        // Funzionamento di un iteratore da lista
        final IntIteratorsFactory factory = new IntIteratorsFactoryImpl();
        List<Integer> list = Arrays.asList(10,20,30,40,5,6,7,100);
        assertEquals(list,iteratorToList(factory.fromList(list)));
    }
    
    
    @org.junit.Test
    public void testFromToIndefinitely() {
        // Funzionamento di un iteratore da 0 a 2 a ripetizione
        final IntIteratorsFactory factory = new IntIteratorsFactoryImpl();
        final Iterator<Integer> zeroToTwoInd = factory.fromToIndefinitely(0, 2);
        assertTrue(zeroToTwoInd.hasNext());
        assertSame(zeroToTwoInd.next(),0);
        assertTrue(zeroToTwoInd.hasNext());
        assertSame(zeroToTwoInd.next(),1);
        assertTrue(zeroToTwoInd.hasNext());
        assertSame(zeroToTwoInd.next(),2);
        assertTrue(zeroToTwoInd.hasNext());
        assertSame(zeroToTwoInd.next(),0);
        assertTrue(zeroToTwoInd.hasNext());
        assertSame(zeroToTwoInd.next(),1);
        assertTrue(zeroToTwoInd.hasNext());
        assertSame(zeroToTwoInd.next(),2);
        assertTrue(zeroToTwoInd.hasNext());
        assertSame(zeroToTwoInd.next(),0);
        assertTrue(zeroToTwoInd.hasNext());
        assertSame(zeroToTwoInd.next(),1);
        assertTrue(zeroToTwoInd.hasNext());
        assertSame(zeroToTwoInd.next(),2);
        // ad infinitum
    }
    
    @org.junit.Test
    public void optionalTestAlternateOneAndZeroIndefinitely() {
        // Funzionamento di un iteratore di zero, uno, zero, uno,..
        final IntIteratorsFactory factory = new IntIteratorsFactoryImpl();
        final Iterator<Integer> zeroOneInd = factory.alternateOneAndZeroIndefinitely();
        assertTrue(zeroOneInd.hasNext());
        assertSame(zeroOneInd.next(),0);
        assertTrue(zeroOneInd.hasNext());
        assertSame(zeroOneInd.next(),1);
        assertTrue(zeroOneInd.hasNext());
        assertSame(zeroOneInd.next(),0);
        assertTrue(zeroOneInd.hasNext());
        assertSame(zeroOneInd.next(),1);
        assertTrue(zeroOneInd.hasNext());
        assertSame(zeroOneInd.next(),0);
        assertTrue(zeroOneInd.hasNext());
        // ad infinitum
    }
    */
    
}
