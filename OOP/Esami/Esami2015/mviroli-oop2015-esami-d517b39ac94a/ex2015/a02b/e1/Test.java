package ex2015.a02b.e1;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class Test {

    /*
     * Implementare l'interfaccia Menu data tramite una classe
     * MenuImpl con costruttore senza argomenti. Modella un menu di un ristorante, 
     * con metodi per creare piatti, aggiungerli al menu, e metodi per estrarre informazioni sul menu. 
     * Il commento al codice di Menu e i metodi di test qui sotto costituiscono la
     * necessaria spiegazione del problema. I test il cui nome comincia con
     * 'optional' sono considerati opzionali ai fini della possibilità di
     * correggere l'esercizio -- anche se concorrono alla definizione del
     * punteggio. 
     * 
     * Si prediligeranno le soluzioni che creano i piatti (Dish) col pattern Builder.
     * 
     * Si tolga il commento ai test..
     */

    /*
    // creazione di un menu base
    private Menu buildBasicMenu() {
        Menu menu = new MenuImpl();
        menu.add(menu.createDish("Tagliere", 100, Menu.Temperature.NORMAL));
        menu.add(menu.createDish("Bruschette", 90, Menu.Temperature.NORMAL));
        menu.add(menu.createDish("Spaghetti", 120, Menu.Temperature.HOT));
        menu.add(menu.createDish("Grigliata", 150, Menu.Temperature.HOT));
        menu.add(menu.createDish("Mascarpone", 150, Menu.Temperature.FROZEN));
        menu.add(menu.createDish("Vino", 60, Menu.Temperature.COLD, "Vino dell'anno")); // menu con premio
        return menu;
    }

    @org.junit.Test
    public void testBasicMenu() {
        final Menu menu = buildBasicMenu();
        // Verifica dei metodi per estrarre informazioni dal menu
        assertEquals(menu.getDishNames().size(), 6);
        assertThat(menu.getDishNames(), hasItems("Tagliere", "Bruschette", "Spaghetti", "Mascarpone", "Vino"));
        assertEquals(menu.getOverallCost(), 670);
        assertEquals(menu.getMapByTemperature().size(), 4);
        assertEquals(menu.getMapByTemperature().get(Menu.Temperature.NORMAL).size(), 2); // 2 piatti NORMAL
        assertEquals(menu.getMapByTemperature().get(Menu.Temperature.HOT).size(), 2);    // 2 piatti HOT
        assertEquals(menu.getMapByTemperature().get(Menu.Temperature.FROZEN).size(), 1); // 1 piatto FROZEN
        assertEquals(menu.getMapByTemperature().get(Menu.Temperature.COLD).size(), 1);   // 1 piatto COLD
        // il piatto FROZEN è il Mascarpone
        assertTrue(menu.getMapByTemperature().get(Menu.Temperature.FROZEN).get(0).getName().equals("Mascarpone"));
        // il piatto COLD ha il premio
        assertTrue(menu.getMapByTemperature().get(Menu.Temperature.COLD).get(0).awarded().isPresent());
    }
    
    @org.junit.Test
    public void optionalTestErrors() {
        final Menu menu = buildBasicMenu();
        Menu.Dish mascarpone = menu.createDish("Mascarpone", 100, Menu.Temperature.FROZEN);
        try{
            menu.add(mascarpone);
            fail("can't add a dish twice");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception thrown");
        }
        try{
            menu.add(null);
            fail("can't add a null dish");
        } catch (NullPointerException e){
        } catch (Exception e){
            fail("wrong exception thrown");
        }
        try{
            menu.createDish("Bistecca", -50, Menu.Temperature.HOT);
            fail("can't cost a negative value");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception thrown");
        }
        try{
            menu.createDish(null, 100, Menu.Temperature.HOT);
            fail("can't have a null name");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception thrown");
        }
    }
    */
}
