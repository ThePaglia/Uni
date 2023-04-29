package ex2016.a04.sol1;

import static org.junit.Assert.*;
import java.util.*;
import static ex2016.a04.sol1.BitIteratorsFactory.*;
import static ex2016.a04.sol1.BitIteratorsFactory.Bit.*;

/**
 * Si consulti la documentazione della interfaccia BitIteratorsFactory: modella una factory per
 * creare vari oggetti di tipo java.util.Iterator<Bit>, dove Bit è una enum data (innestata a factory).
 *  
 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
 * della totalità del punteggio:
 * - implementazione dei test opzionali (denominati 'optionalTestXYZ') 
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
    
    @org.junit.Test
    public void testEmpty() {
        // Funzionamento di un iteratore vuoto
        final BitIteratorsFactory factory = new BitIteratorsFactoryImpl();
        Iterator<BitIteratorsFactory.Bit> empty = factory.empty();
        assertFalse(empty.hasNext());
    }
    
    @org.junit.Test
    public void testZeros() {
        // Funzionamento di un iteratore di zeri
        final BitIteratorsFactory factory = new BitIteratorsFactoryImpl();
        final Iterator<BitIteratorsFactory.Bit> zeros = factory.zeros();
        assertTrue(zeros.hasNext());
        assertEquals(zeros.next(),ZERO);
        assertTrue(zeros.hasNext());
        assertEquals(zeros.next(),ZERO);
        assertTrue(zeros.hasNext());
        assertEquals(zeros.next(),ZERO);
        assertTrue(zeros.hasNext());
        assertEquals(zeros.next(),ZERO);
        assertTrue(zeros.hasNext());
        // ad infinitum
    }
    
    @org.junit.Test
    public void testOnes() {
        // Funzionamento di un iteratore di uno
        final BitIteratorsFactory factory = new BitIteratorsFactoryImpl();
        final Iterator<BitIteratorsFactory.Bit> ones = factory.ones();
        assertTrue(ones.hasNext());
        assertEquals(ones.next(),ONE);
        assertTrue(ones.hasNext());
        assertEquals(ones.next(),ONE);
        assertTrue(ones.hasNext());
        assertEquals(ones.next(),ONE);
        assertTrue(ones.hasNext());
        assertEquals(ones.next(),ONE);
        assertTrue(ones.hasNext());
        // ad infinitum
    }
    
    @org.junit.Test
    public void testFromByte() {
        // Funzionamento di un iteratore dei bit di un byte, da LSB a MSB
        final BitIteratorsFactory factory = new BitIteratorsFactoryImpl();
        assertEquals(this.iteratorToList(factory.fromByteStartingWithLSB(0)), // 00000000
                Arrays.asList(ZERO,ZERO,ZERO,ZERO,ZERO,ZERO,ZERO,ZERO));
        assertEquals(this.iteratorToList(factory.fromByteStartingWithLSB(1)), // 00000001
                Arrays.asList(ONE,ZERO,ZERO,ZERO,ZERO,ZERO,ZERO,ZERO));
        assertEquals(this.iteratorToList(factory.fromByteStartingWithLSB(15)),// 00001111
                Arrays.asList(ONE,ONE,ONE,ONE,ZERO,ZERO,ZERO,ZERO));
        assertEquals(this.iteratorToList(factory.fromByteStartingWithLSB(16)),// 00010000
                Arrays.asList(ZERO,ZERO,ZERO,ZERO,ONE,ZERO,ZERO,ZERO));
        assertEquals(this.iteratorToList(factory.fromByteStartingWithLSB(255)),//11111111
                Arrays.asList(ONE,ONE,ONE,ONE,ONE,ONE,ONE,ONE));
    }
    
    @org.junit.Test
    public void testFromBitList() {
        // Funzionamento di un iteratore da lista di bit
        final BitIteratorsFactory factory = new BitIteratorsFactoryImpl();
        assertEquals(this.iteratorToList(factory.fromBitList(Arrays.asList(ONE,ZERO,ONE))),
                Arrays.asList(ONE,ZERO,ONE));
        assertEquals(this.iteratorToList(factory.fromBitList(Arrays.asList(ONE,ZERO,ONE,ONE))),
                Arrays.asList(ONE,ZERO,ONE,ONE));
        assertEquals(this.iteratorToList(factory.fromBitList(Arrays.asList(ONE,ZERO))),
                Arrays.asList(ONE,ZERO));
    }
    
    @org.junit.Test
    public void optionalTestFromBooleanList() {
        // Funzionamento di un iteratore da lista di boolean
        final BitIteratorsFactory factory = new BitIteratorsFactoryImpl();
        assertEquals(this.iteratorToList(factory.fromBooleanList(Arrays.asList(true,false,true))),
                Arrays.asList(ONE,ZERO,ONE));
        assertEquals(this.iteratorToList(factory.fromBooleanList(Arrays.asList(true,false,true,true))),
                Arrays.asList(ONE,ZERO,ONE,ONE));
        assertEquals(this.iteratorToList(factory.fromBooleanList(Arrays.asList(true,false))),
                Arrays.asList(ONE,ZERO));
    }
    
    @org.junit.Test
    public void optionalTestExceptions() {
        // Test di eccezioni..
        final BitIteratorsFactory factory = new BitIteratorsFactoryImpl();
        try{
            factory.empty().next();
            fail("Empty should fail at first next");
        } catch (NoSuchElementException e){}
        catch (Exception e){
            fail("Should raise a NoSuchElementException");
        }
        
        Iterator<Bit> it = factory.fromByteStartingWithLSB(0);
        it.next();
        it.next();
        it.next();
        it.next();
        it.next();
        it.next();
        it.next();
        it.next();
        try{
            it.next();
            fail("Should fail at ninth call");
        } catch (NoSuchElementException e){}
        catch (Exception e){
            fail("Should raise a NoSuchElementException");
        }
        
        it = factory.fromBitList(Arrays.asList(ZERO,ZERO,ONE));
        it.next();
        it.next();
        it.next();
        try{
            it.next();
            fail("Should fail at fourth call");
        } catch (NoSuchElementException e){}
        catch (Exception e){
            fail("Should raise a NoSuchElementException");
        }
    }
    
    
}
