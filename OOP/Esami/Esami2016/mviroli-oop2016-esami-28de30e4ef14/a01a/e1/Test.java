package ex2016.a01a.e1;

import static org.junit.Assert.*;
import java.util.*;

/**
 * Si consulti la documentazione delle interfacce SequenceBuilder e Sequence. 
 * 
 * SequenceBuilder (da implementare in una classe SequenceBuilderImpl con costruttore senza argomenti) modella un builder
 * per oggetti di tipo Sequence.
 *  
 * Sequence modella una sequenza di elementi immutabile 
 * (con metodi per leggere lunghezza, elementi, convertire in lista, e eseguire azioni su tutti gli elementi).
 * 
 * SequenceBuilder ha metodi per aggiungere, togliere e invertire gli elementi, e per generare via 'map' nuovi builder.
 * Al momento di costruire la sequenza col metodo build, si può accettare un filtro per verificare che tutti gli elementi 
 * abbiano certe caratteristiche.
 * 
 * Le interfacce menzionano la classe java.util.Optional<X>, usata per contenere o meno un elemento di tipo X. Si ricorda
 * che se ne costruiscono oggetti con i metodi statici Optional.of e Optional.empty, e se ne legge il contenuto con
 * i metodi istanza Optional.isPresent e Optional.get.
 * 
 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
 * della totalità del punteggio:
 * - implementazione dei test opzionali (relativi ai metodi SequenceBuilder.mapToNewBuilder e SequenceBuilder.buildWithFilter) 
 * - la buona progettazione della soluzione
 * 
 * Si osservi con attenzione il test seguente, che assieme ai commenti delle interfacce date
 * costituisce la definizione del problema da risolvere.
 * 
 * Si tolga il commento al codice del test.
 */

public class Test {
    
    /*
    @org.junit.Test
    public void testBasic() {
        // Un builder usato per creare la lista immutabile di elementi "a","b","c" 
        SequenceBuilder<String> b1 = new SequenceBuilderImpl<>();
        b1.addElement("a");
        b1.addElement("b");
        b1.addElement("c");
        // Costruisco la sequenza (senza errori, l'opzionale è presente)
        final Sequence<String> is = b1.build().get(); 
        assertEquals(is.asList(), Arrays.asList("a","b","c"));
        assertEquals(is.size(), 3);
        assertEquals(is.getAtPosition(0).get(), "a");
        assertEquals(is.getAtPosition(1).get(), "b");
        assertEquals(is.getAtPosition(2).get(), "c");
        assertFalse(is.getAtPosition(3).isPresent());
        List<String> ls = new ArrayList<>();
        is.executeOnAllElements(s -> ls.add(s)); // per ogni elemento di is, lo aggiungo a ls
        assertEquals(ls, Arrays.asList("a","b","c"));
        // Dopo aver fatto una build, non la si può rifare..
        assertFalse(b1.build().isPresent());
    }
    
    @org.junit.Test
    public void testRemove() {
        SequenceBuilder<String> b1 = new SequenceBuilderImpl<>();
        b1.addElement("a");
        b1.addElement("b");
        b1.addElement("c");
        b1.removeElement(1);
        assertEquals(b1.build().get().asList(), Arrays.asList("a","c"));
    }
    
    
    @org.junit.Test
    public void testReverse() {
        SequenceBuilder<String> b1 = new SequenceBuilderImpl<>();
        b1.addElement("a");
        b1.addElement("b");
        b1.addElement("c");
        b1.reverse();
        assertEquals(b1.build().get().asList(), Arrays.asList("c","b","a"));
    }
    
    @org.junit.Test
    public void testClear() {
        SequenceBuilder<String> b1 = new SequenceBuilderImpl<>();
        b1.addElement("a");
        b1.addElement("b");
        b1.addElement("c");
        b1.clear();
        assertEquals(b1.build().get().asList(), Arrays.asList());
    }

    @org.junit.Test
    public void optionalTestMap() {
        SequenceBuilder<String> b1 = new SequenceBuilderImpl<>();
        b1.addElement("a");
        b1.addElement("bb");
        b1.addElement("ccc");
        SequenceBuilder<Integer> b2 = b1.mapToNewBuilder(s -> s.length()*10);
        assertEquals(b2.build().get().asList(), Arrays.asList(10,20,30));
    }
    
    @org.junit.Test
    public void optionalTestBuildWithFilter() {
        SequenceBuilder<String> b1 = new SequenceBuilderImpl<>();
        b1.addElement("a");
        b1.addElement("bb");
        b1.addElement("c");
        assertFalse(b1.buildWithFilter(s -> s.length() == 1).isPresent());
        b1.removeElement(1);
        Optional<Sequence<String>> opt = b1.buildWithFilter(s -> s.length() == 1); 
        assertTrue(opt.isPresent());
        assertEquals(opt.get().asList(), Arrays.asList("a","c"));
    }
    */
}
