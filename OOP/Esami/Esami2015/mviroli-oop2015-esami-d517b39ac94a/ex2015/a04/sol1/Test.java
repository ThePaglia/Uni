package ex2015.a04.sol1;

import static org.junit.Assert.*;

import java.util.*;

/**
 * Implementare l'interfaccia TreeFactory con una classe TreeFactoryImpl con costruttore vuoto,
 * che realizzi una Factory per alberi immutabili, che aderiscano all'interfaccia generica Tree fornita
 * (ad esempio, lo si faccia costruendo anche una class TreeImpl che implementi Tree). 
 * La factory ha un metodo per generare alberi vuoti, e uno per generare un albero data
 * una radice e una lista di figli. Si assuma, senza fare controlli, che un albero non
 * abbia elementi ripetuti. 
 * 
 * Si osservi con attenzione il test seguente, che assieme ai commenti delle interfacce date
 * costituisce la definizione del problema da risolvere.
 * 
 * Il secondo test sia considerato opzionale ai fini della possibilità di correggere
 * l'esercizio -- anche se concorre alla definizione del punteggio.
 * 
 * Si tolga il commento al codice del test.
 */

public class Test {    
    
    @org.junit.Test
    public void testBasic() {
        TreeFactory<String> tf = new TreeFactoryImpl<>();
        // costruzione di tre alberi figli
        // a con tre figli aa,ab,ac
        Tree<String> son1 = tf.consTree("a",Arrays.asList(
                tf.consTree("aa",null), tf.consTree("ab",null), tf.consTree("ac",null)
        ));
        // solo la radice b
        Tree<String> son2 = tf.consTree("b",null);
        // c con tre figli ca,cb,cc, e cc a sua volta con i figli cca e ccb
        Tree<String> son3 = tf.consTree("c",Arrays.asList(
                tf.consTree("ca",null), 
                tf.consTree("cb",null), 
                tf.consTree("cc",Arrays.asList(
                        tf.consTree("cca",null), 
                        tf.consTree("ccb",null)
        ))));
        // costruisco un tree tramite i tre alberi figli
        Tree<String> tree = tf.consTree("root",Arrays.asList(son1,son2,son3));
        
        // test su size()
        assertEquals(son1.size(),4);
        assertEquals(son2.size(),1);
        assertEquals(son3.size(),6);
        assertEquals(tree.size(),12);
        
        // test su getRoot()
        assertEquals(son1.getRoot(),"a");
        assertEquals(son2.getRoot(),"b");
        assertEquals(son3.getRoot(),"c");
        assertEquals(tree.getRoot(),"root");
        
        // test su contains
        assertTrue(tree.contains("a"));
        assertTrue(tree.contains("b"));
        assertTrue(tree.contains("c"));
        assertTrue(tree.contains("ccb"));
        assertFalse(tree.contains("ccc"));
        
        // test su toList.. si noti che l'ordine NON è rilevante
        assertTrue(son1.toList().containsAll(Arrays.asList("a","aa","ab","ac")));
        assertEquals(son1.toList().size(),son1.size());
        assertTrue(son2.toList().containsAll(Arrays.asList("b")));
        assertEquals(son2.toList().size(),son2.size());
        assertTrue(son3.toList().containsAll(Arrays.asList("c","ca","cb","cc","cca","ccb")));
        assertEquals(son3.toList().size(),son3.size());
        assertTrue(tree.toList().containsAll(Arrays.asList("c","ca","cb","cc","cca","ccb","b","a","aa","ab","ac")));
        assertEquals(tree.toList().size(),tree.size());
     
        // test su getSons
        assertTrue(tree.getSons().get(0).toList().containsAll(Arrays.asList("a","aa","ab","ac")));
        assertTrue(tree.getSons().get(1).toList().containsAll(Arrays.asList("b")));
        assertTrue(tree.getSons().get(2).toList().containsAll(Arrays.asList("c","ca","cb","cc","cca","ccb")));
        
        // test su getSubTree
        assertTrue(tree.getSubTree("a").toList().containsAll(Arrays.asList("a","aa","ab","ac")));
        assertTrue(tree.getSubTree("b").toList().containsAll(Arrays.asList("b")));
        assertTrue(tree.getSubTree("c").toList().containsAll(Arrays.asList("c","ca","cb","cc","cca","ccb")));
        assertTrue(tree.getSubTree("root").toList().containsAll(Arrays.asList("c","ca","cb","cc","cca","ccb","b","a","aa","ab","ac")));
        assertTrue(tree.getSubTree("cc").toList().containsAll(Arrays.asList("cc","cca","ccb")));
    }
    
    @org.junit.Test
    public void optionalTestEmpty() {
        // test sul caso di albero vuoto
        TreeFactory<String> tf = new TreeFactoryImpl<>();
        Tree<String> tree = tf.emptyTree();
        
        // dimensione 0
        assertEquals(tree.size(),0);
        
        // non si può accedere a root
        try{
            tree.getRoot();
            fail("can't get root if emty");
        } catch (IllegalStateException e){
            
        } catch (Exception e){
            fail("wrong exception");
        }
        
        // non contiene alcun elemento
        assertFalse(tree.contains("a"));
        
        // ha lista vuota di elementi
        assertEquals(tree.toList().size(),0);
        
        // non si può accedere a sons
        try{
            tree.getSons();
            fail("can't get sons if emty");
        } catch (IllegalStateException e){
            
        } catch (Exception e){
            fail("wrong exception");
        }
        
        // non si può accedere a subtrees
        try{
            tree.getSubTree("a");
            fail("can't get subtrees if emty");
        } catch (IllegalStateException e){
            
        } catch (Exception e){
            fail("wrong exception");
        }
    }
    

}
