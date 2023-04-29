package ex2015.a05.sol1;

import static org.junit.Assert.*;

import java.util.*;

/**
 * Implementare l'interfaccia CListFactory con una classe CListFactoryImpl con costruttore vuoto,
 * che realizzi una Factory per liste cicliche immutabili, che aderiscano all'interfaccia generica CList fornita
 * (ad esempio, lo si faccia costruendo anche una class CListImpl che implementi CList).
 * Una lista ciclica immutabile è una lista il cui ultimo elemento ha come prossimo il primo elemento della lista. 
 * La factory ha un metodo per generare liste cicliche vuote, e uno per generare una lista ciclica a partire da
 * una lista. 
 * 
 * Si osservi con attenzione il test seguente, che assieme ai commenti delle interfacce date
 * costituisce la definizione del problema da risolvere.
 * 
 * Il secondo test sia considerato opzionale ai fini della possibilità di correggere
 * l'esercizio -- anche se concorre alla definizione del punteggio. Inoltre, si cerchi
 * di adottare il più possibile la condivisione di strutture fra liste cicliche diverse.
 * 
 * Si tolga il commento al codice del test.
 */

public class Test {    
    
    @org.junit.Test
    public void testBasic() {
        CListFactory<String> cf = new CListFactoryImpl<>();
        
        CList<String> cl = cf.consCList(Arrays.asList("a","b","c")); // a,b,c,a,b,c,a,b,c,..
        
        assertEquals(cl.size(),3);
        assertEquals(cl.getElem(0),"a");
        assertEquals(cl.getElem(1),"b");
        assertEquals(cl.getElem(2),"c");
        assertEquals(cl.getElem(3),"a");
        assertEquals(cl.getElem(4),"b");
        assertEquals(cl.getElem(9),"a");
        
        assertTrue(cl.contains("a"));
        assertTrue(cl.contains("b"));
        assertTrue(cl.contains("c"));
        assertFalse(cl.contains("d"));
        
        assertEquals(cl.toList(),Arrays.asList("a","b","c"));
        
        CList<String> cl2 = cl.shift(1); // b,c,a,b,c,a,b,c,a,b,c,...
        assertEquals(cl2.size(),3);
        assertEquals(cl2.getElem(0),"b");
        assertEquals(cl2.getElem(1),"c");
        assertEquals(cl2.getElem(2),"a");
        assertEquals(cl2.getElem(3),"b");
        assertEquals(cl2.getElem(4),"c");
        assertEquals(cl2.getElem(9),"b");
        
        assertTrue(cl2.contains("a"));
        assertTrue(cl2.contains("b"));
        assertTrue(cl2.contains("c"));
        assertFalse(cl2.contains("d"));
        
        assertEquals(cl2.toList(),Arrays.asList("b","c","a"));
        
        CList<String> cl3 = cl.shift(2); // c,a,b,c,a,b,c,a,b,c,...
        assertEquals(cl3.size(),3);
        assertEquals(cl3.getElem(0),"c");
        assertEquals(cl3.getElem(1),"a");
        assertEquals(cl3.getElem(2),"b");
        assertEquals(cl3.getElem(3),"c");
        assertEquals(cl3.getElem(4),"a");
        assertEquals(cl3.getElem(9),"c");
        
        assertTrue(cl3.contains("a"));
        assertTrue(cl3.contains("b"));
        assertTrue(cl3.contains("c"));
        assertFalse(cl3.contains("d"));
        
        assertEquals(cl3.toList(),Arrays.asList("c","a","b"));
        
        
    }
    
    @org.junit.Test
    public void optionalTest1() {
        CListFactory<String> cf = new CListFactoryImpl<>();
        
        CList<String> cl = cf.consCList(Arrays.asList("a","b","c")); // a,b,c,a,b,c,a,b,c,..
        CList<String> cl2 = cl.add("?",1); // ?,b,c,a,?,b,c,a,..
        
        assertEquals(cl2.size(),4);
        assertEquals(cl2.getElem(0),"?");
        assertEquals(cl2.getElem(1),"b");
        assertEquals(cl2.getElem(2),"c");
        assertEquals(cl2.getElem(3),"a");
        assertEquals(cl2.getElem(4),"?");
        assertEquals(cl2.getElem(5),"b");
        
        assertTrue(cl2.contains("a"));
        assertTrue(cl2.contains("b"));
        assertTrue(cl2.contains("c"));
        assertTrue(cl2.contains("?"));
        assertFalse(cl2.contains("d"));
        
        assertEquals(cl2.toList(),Arrays.asList("?","b","c","a"));
        
        CList<String> cl3 = cf.emptyCList();
        assertEquals(cl3.size(),0);
        
        assertFalse(cl3.contains("a"));
        
        assertTrue(cl3.toList().isEmpty());
        
    }
    

}
