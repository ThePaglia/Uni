package a06.sol1;

import static org.junit.Assert.*;
import java.util.*;

/**
 * Si consulti la documentazione delle interfacce fornite:
 * - Parser modella un parser, ossia un "consumatore" di stringhe (token)
 * - ParserFactory modella un unsieme di funzionalità per creare Parser 
 * 
 * Implementare ParserFactory attraverso una classe ParserFactoryImple con costruttore senza argomenti,
 * in modo che passi tutti i test di cui sotto.
 *  
 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
 * della totalità del punteggio:
 * - implementazione a scelta o dei primi 4 metodi della factory (one,oneOf,many,sequence), o dell'ultimo (fullSequence)
 * - la buona progettazione della soluzione, in particolare con minimizzazione di ripetizioni
 * 
 * Si osservi con attenzione il test seguente, che assieme ai commenti delle interfacce date
 * costituisce la definizione del problema da risolvere.
 * 
 * Si tolga il commento al codice del test.
 */

public class Test {
	
	private ParserFactory pf;
	
	@org.junit.Before
	public void init() {
		this.pf = new ParserFactoryImpl();
	}
	
	@org.junit.Test
    public void testOne() {
        // Test su un parser che accetta solo una "a"
        final Parser parser = this.pf.one("a");
        assertTrue(parser.acceptToken("a")); // prende "a"
        assertTrue(parser.inputCompleted()); // ha finito
        assertFalse(parser.acceptToken("a")); // non prende un'altra "a"
        parser.reset(); // si riparte, e funziona come prima
        assertTrue(parser.acceptToken("a"));
        assertTrue(parser.inputCompleted());
        assertFalse(parser.acceptToken("a"));
        parser.reset(); // si riparte
        assertFalse(parser.inputCompleted()); // non ha finito
        assertTrue(parser.acceptToken("a")); // prende "a"
        assertTrue(parser.inputCompleted()); // ha finito
        parser.reset();
        assertTrue(parser.acceptAllToEnd(Arrays.asList("a"))); // consuma la sequenza fatta di una "a"
        parser.reset();
        assertFalse(parser.acceptAllToEnd(Arrays.asList("a","a"))); // questa non la consuma
        parser.reset();
        assertFalse(parser.acceptAllToEnd(Arrays.asList("b"))); // questa non la consuma
    }

	@org.junit.Test
    public void testOneOf() {
        // Test su un parser che accetta solo una "a", o solo una "b", o solo una "c"
        final Parser parser = this.pf.oneOf(new HashSet<>(Arrays.asList("a","b","c")));
        assertTrue(parser.acceptToken("a"));
        assertTrue(parser.inputCompleted());
        assertFalse(parser.acceptToken("a"));
        parser.reset();
        assertTrue(parser.acceptToken("b"));
        assertTrue(parser.inputCompleted());
        assertFalse(parser.acceptToken("a"));
        parser.reset();
        assertFalse(parser.inputCompleted());
        assertTrue(parser.acceptToken("c"));
        assertTrue(parser.inputCompleted());
        parser.reset();
        assertTrue(parser.acceptAllToEnd(Arrays.asList("a")));
        parser.reset();
        assertFalse(parser.acceptAllToEnd(Arrays.asList("a","a")));
        parser.reset();
        assertTrue(parser.acceptAllToEnd(Arrays.asList("b")));
        parser.reset();
        assertFalse(parser.acceptAllToEnd(Arrays.asList()));
    }
	
	@org.junit.Test
    public void testZeroOrMany() {
        // Test su parser che accettano 0 o più copie di "a"
        Parser parser = this.pf.many("a",0); // 0 copie di "a"
        assertTrue(parser.inputCompleted()); // già finito
        parser = this.pf.many("a",1); // 1 copia di "a"
        assertTrue(parser.acceptToken("a"));
        assertTrue(parser.inputCompleted());
        parser = this.pf.many("a",3); // 3 copie di "a"
        assertTrue(parser.acceptToken("a"));
        assertTrue(parser.acceptToken("a"));
        assertTrue(parser.acceptToken("a"));        
        assertTrue(parser.inputCompleted());
        parser = this.pf.many("a",0); // altri test usando acceptAllToEnd
        assertTrue(parser.acceptAllToEnd(Arrays.asList()));
        parser = this.pf.many("a",1);
        assertTrue(parser.acceptAllToEnd(Arrays.asList("a")));
        parser = this.pf.many("a",3);
        assertTrue(parser.acceptAllToEnd(Arrays.asList("a","a","a")));
        parser.reset();
        assertFalse(parser.acceptAllToEnd(Arrays.asList("a","a","b")));
    }
	
	@org.junit.Test
    public void testSequence() {
        // Test su un parser che accetta una "a" e poi una "b", e poi basta
        final Parser parser = this.pf.sequence("a","b");
        parser.reset();
        assertFalse(parser.acceptAllToEnd(Arrays.asList()));
        parser.reset();
        assertTrue(parser.acceptAllToEnd(Arrays.asList("a","b")));
        parser.reset();
        assertFalse(parser.acceptAllToEnd(Arrays.asList("a")));
        parser.reset();
        assertFalse(parser.acceptAllToEnd(Arrays.asList("a","b","a","b")));
    }
	
	@org.junit.Test
    public void testFullSequence() {
        Parser parser = this.pf.fullSequence("[",new HashSet<>(Arrays.asList("a","b")),";","]",1);
        assertTrue(parser.acceptToken("["));   // riconosco [ a ]     
        assertTrue(parser.acceptToken("a"));        
        assertTrue(parser.acceptToken("]"));        
        assertTrue(parser.inputCompleted());        
        parser = this.pf.fullSequence("[",new HashSet<>(Arrays.asList("a","b")),";","]",1);
        assertTrue(parser.acceptAllToEnd(Arrays.asList("[","a","]"))); // riconosco [ a ]
        parser = this.pf.fullSequence("[",new HashSet<>(Arrays.asList("a","b")),";","]",2);
        assertTrue(parser.acceptAllToEnd(Arrays.asList("[","a",";","b","]"))); // riconosco [ a ; b ]
        parser = this.pf.fullSequence("[",new HashSet<>(Arrays.asList("a","b")),";","]",3);
        assertTrue(parser.acceptAllToEnd(Arrays.asList("[","a",";","b",";","a","]"))); // riconosco [ a ; b ; a ]
        parser.reset();
        assertFalse(parser.acceptAllToEnd(Arrays.asList("[","]"))); // non riconosco [ ]
        parser = this.pf.fullSequence("[",new HashSet<>(Arrays.asList("a","b")),";","]",3);
        assertFalse(parser.acceptAllToEnd(Arrays.asList("[","a","b",";","a","]"))); // non riconosco [ a b ; a ]
    }

}
