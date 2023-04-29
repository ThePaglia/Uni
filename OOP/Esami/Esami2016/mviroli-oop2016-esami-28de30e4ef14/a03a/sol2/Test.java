package ex2016.a03a.sol2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine gui.png fornita, con 
     * un costruttore che prende in ingresso il numero di pulsanti disabilitati (ossia tranne MoveA, MoveB e Reset).
     * - La "A", inizialmente in prima posizione, viene spostata di una posizione a destra premendo il pulsante MoveA 
     * - La "B", inizialmente in seconda posizione, viene spostata di una posizione a destra premendo il pulsante MoveB
     * - La "A" deve sempre stare alla sinistra di "B" (ignorando quindi i comandi MoveA che le farebbero sovrapporre). 
     * - Quando né "A" né "B" possono più spostarsi la GUI si chiuda da sola.
     * - Il pulsante Reset ripristini la situazione iniziale della GUI.
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - compilazione e esecuzione dell'esempio da linea di comando
     * 
     * La classe GUI fornita come punto di partenza già contiene in minima parte del codice utile e eventualmente riusabile. 
     * 
     */

    public static void main(String[] args) throws java.io.IOException {
        new GUI(10);  
    }
}
