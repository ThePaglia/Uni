package ex2016.a03a.sol1;

import static org.junit.Assert.*;
import java.util.*;

/**
 * Si consulti la documentazione delle interfacce Bag<X> e BagFactory. 
 * 
 * BagFactory (da implementare in una classe BagFactory con costruttore senza argomenti) modella una factory per
 * creare vari oggetti di tipo Bag<X>, ossia multiset (set con ripetizioni) immutabili di elementi di tipo X.
 *  
 * Bag<X> ha come operazione fondametale quella di ottenere quante copie di un certo elemento sono presenti (0 se non c'è). 
 * BagFactory ha metodi per creare Bag vuoti, Bag da liste, Bag da set, Bag da iterazioni, e Bag da "suppliers".
 * 
 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
 * della totalità del punteggio:
 * - implementazione dei test opzionali (relativi ai metodi BagFactory.bySupplier e BagFactory.byIteration) 
 * - la buona progettazione della soluzione, in particolare con minimizzazione di ripetizioni
 * 
 * Si osservi con attenzione il test seguente, che assieme ai commenti delle interfacce date
 * costituisce la definizione del problema da risolvere.
 * 
 * Si tolga il commento al codice del test.
 */

public class Test {
    
    @org.junit.Test
    public void testEmpty() {
        // Funzionamento di un bag vuoto
        final BagFactory factory = new BagFactoryImpl();
        final Bag<String> bag = factory.empty();
        assertEquals(bag.size(),0);
        assertEquals(bag.numberOfCopies("a"),0);
        assertEquals(bag.numberOfCopies("b"),0);
        assertEquals(bag.toList(), Collections.EMPTY_LIST);
    }
    
    // Funzione di utilità per creare un set da una lista
    private <X> Set<X> listToSet(List<X> list){
        return new HashSet<>(list);
    }
    
    @org.junit.Test
    public void testFromSet() {
        // Funzionamento del bag {10,20,30}
        final BagFactory factory = new BagFactoryImpl();
        final Bag<Integer> bag = factory.fromSet(this.listToSet(Arrays.asList(10,20,30)));
        assertEquals(bag.size(),3);
        assertEquals(bag.numberOfCopies(10),1);
        assertEquals(bag.numberOfCopies(20),1);
        assertEquals(bag.numberOfCopies(40),0);
        // il toList su questo bag, convertito a set, mi da 10,20,30
        // ossia contiene 10,20,30 in qualunque ordine
        assertEquals(this.listToSet(bag.toList()), this.listToSet(Arrays.asList(10,20,30)));
    }
    
    // Funzione di utilità per tonrare la versione ordinata di una lista
    private <X> List<X> sorted(List<X> list){
        return list.stream().sorted().collect(java.util.stream.Collectors.toList());
    }
    
    @org.junit.Test
    public void testFromList() {
        // Funzionamento del bag {10,10,20}
        final BagFactory factory = new BagFactoryImpl();
        final Bag<Integer> bag = factory.fromList(Arrays.asList(10,20,20));
        assertEquals(bag.size(),3);
        assertEquals(bag.numberOfCopies(10),1);
        assertEquals(bag.numberOfCopies(20),2);
        assertEquals(bag.numberOfCopies(40),0);
        // il toList su questo bag, ordinato, mi da 10,20,20
        assertEquals(sorted(bag.toList()), Arrays.asList(10,20,20));
    }
    
    @org.junit.Test
    public void testBySupplier() {
        // Funzionamento di un bag di 10 numeri random, ognuno presente in 5 copie
        final BagFactory factory = new BagFactoryImpl();
        final Bag<Double> bag = factory.bySupplier(()->Math.random(),10,d->5);
        assertEquals(bag.size(), 50);
        final List<Double> elems = bag.toList();
        assertEquals(elems.size(), 50);
        // Per ognuno dei 50 elementi del bag (ottenuti dal suo toList), verifico che sia presente in 5 copie
        for (double d: elems){
            assertEquals(bag.numberOfCopies(d),5);
        }
    }
    
    @org.junit.Test
    public void testByIteration() {
        // Funzionamento del bag {0,0,1,1,2,2,3,3,..,8,8,9,9}
        final BagFactory factory = new BagFactoryImpl();
        final Bag<Integer> bag = factory.byIteration(0,x->x+1,10,d->2);
        assertEquals(bag.size(), 20);
        for (int i=0;i<10;i++){
            assertEquals(bag.numberOfCopies(i),2);
        }
    }
    
    
}
