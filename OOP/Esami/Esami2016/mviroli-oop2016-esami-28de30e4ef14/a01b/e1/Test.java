package ex2016.a01b.e1;

import static org.junit.Assert.*;

import java.util.*;

/**
 * Si consulti la documentazione delle interfacce Builders e ListBuilder. 
 * 
 * Builders (da implementare in una class BuildersImpl con costruttore senza argomenti) modella una factory per oggetti 
 * di tipo ListBuilder.
 *  
 * ListBuilder modella un builder per liste immutabili, ossia un oggetto che si usa per indicare passo passo 
 * una lista di elementi a proprio piacimento, per poi generarne una lista immutabile da produrre in uscita (si ricordi
 * l'uso del metodo Collections.unmodifiableList).
 * 
 * Si noti che Builders richiedere di produrre 4 builder di tipo diverso: la cosa va fatta cercando di evitare al massimo le
 * duplicazioni di codice.
 * 
 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
 * della totalità del punteggio:
 * - implementazione dell'ultimo builder (il suo test è indicato come opzionale)
 * - la non duplicazione di codice, e in generale, la buona progettazione della soluzione
 * 
 * Si osservi con attenzione il test seguente, che assieme ai commenti delle interfacce date
 * costituisce la definizione del problema da risolvere.
 * 
 * Si tolga il commento al codice del test.
 */

public class Test {
    
    /*
     
    @org.junit.Test
    public void testBasicBuilder() {
        // Istanzio la factory per i builder
        final Builders builders = new BuildersImpl();
        // Un builder usato per creare la lista immutabile di elementi "a","b","c" 
        final ListBuilder<String> b1 = builders.makeBasicBuilder();
        b1.addElement("a");
        b1.addElement("b");
        b1.addElement("c");
        assertEquals(b1.build(), Arrays.asList("a","b","c"));
        // Esempio simile, ma con gli Integer
        final ListBuilder<Integer> b2 = builders.makeBasicBuilder();
        b2.addElement(10);
        b2.addElement(50);
        b2.addElement(20);
        b2.addElement(30);
        assertEquals(b2.build(), Arrays.asList(10,50,20,30));
        // Nota, dopo aver chiamato la build, il builder non è più usabile
        try {
            b2.addElement(40);
            fail("cannot add after build");
        } catch (IllegalStateException e){}
        catch (Exception e){
            fail("should throw an IllegalStateException, not a "+e.getClass());
        }
        // ..né si possono fare altre build
        try{
            b2.build();
            fail("cannot build after building!");
        } catch (IllegalStateException e){}
          catch (Exception e){
              fail("should throw an IllegalStateException, not a "+e.getClass());
          }
    }
    
    @org.junit.Test
    public void testBuilderWithSize() {
        // Un builder per stringhe di 3 elementi, né più né meno
        final Builders builders = new BuildersImpl();
        final ListBuilder<String> b1 = builders.makeBuilderWithSize(3);
        b1.addElement("a");
        b1.addElement("b");
        b1.addElement("c");
        assertEquals(b1.build(), Arrays.asList("a","b","c"));
        // Se si prova a costruire una lista con meno o più elementi.. fallisce
        final ListBuilder<String> b2 = builders.makeBuilderWithSize(3);
        b2.addElement("a");
        b2.addElement("b");
        try{
            b2.build();
            fail("cannot build, we need 3 elements, not 2!");
        } catch (IllegalStateException e){}
          catch (Exception e){
              fail("should throw an IllegalStateException, not a "+e.getClass());
          }
        // in caso di tentativo di build errato si può andare avanti!
        b2.addElement("c");
        assertEquals(b2.build(), Arrays.asList("a","b","c"));
    }
    
    @org.junit.Test
    public void testBuilderFromElements() {
        final Builders builders = new BuildersImpl();
        final ListBuilder<String> b1 = builders.makeBuilderFromElements(Arrays.asList("0","1"));
        // Un builder per sole stringhe "0" e "1"
        b1.addElement("0");
        b1.addElement("1");
        b1.addElement("1");
        assertEquals(b1.build(), Arrays.asList("0","1","1"));
        final ListBuilder<String> b2 = builders.makeBuilderFromElements(Arrays.asList("0","1"));
        b2.addElement("0");
        // provare a inserire un "2" genera un errore
        try{
            b2.addElement("2");
            fail("cannot a 2, just a 0 or 1");
        } catch (IllegalArgumentException e){}
          catch (Exception e){
              fail("should throw an IllegalArgumentException, not a "+e.getClass());
          }
        //.. ma poi si può andare avanti
        b2.addElement("1");
        assertEquals(b2.build(), Arrays.asList("0","1"));
    }
    
    @org.junit.Test
    public void optionalTestBuilderFromElementsAndWithSize() {
        final Builders builders = new BuildersImpl();
        final ListBuilder<String> b1 = builders.makeBuilderFromElementsAndWithSize(Arrays.asList("0","1"),4);
        // Un builder per stringhe di 4 elementi, ciascuno "0" o "1"
        b1.addElement("0");
        b1.addElement("1");
        b1.addElement("1");
        b1.addElement("0");
        assertEquals(b1.build(), Arrays.asList("0","1","1","0"));
        ListBuilder<String> b2 = builders.makeBuilderFromElementsAndWithSize(Arrays.asList("0","1"),4);
        b2.addElement("0");
        try{
            b2.addElement("2");
            fail("cannot add 2, just a 0 or 1");
        } catch (IllegalArgumentException e){}
          catch (Exception e){
              fail("should throw an IllegalArgumentException, not a "+e.getClass());
          }
        b2.addElement("1");
        try{
            b2.build();
            fail("cannot build, we need 4 elements, not 2!");
        } catch (IllegalStateException e){}
          catch (Exception e){
              fail("should throw an IllegalStateException, not a "+e.getClass());
          }
        b2.addElement("0");
        b2.addElement("0");
        assertEquals(b2.build(), Arrays.asList("0","1","0","0"));
    }
    */
}
