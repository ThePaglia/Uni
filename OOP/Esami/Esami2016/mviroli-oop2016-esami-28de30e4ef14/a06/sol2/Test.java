package pr2016.a06.sol2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine gui.png fornita, con 
     * un costruttore che prende in ingresso il numero di pulsanti.
     * Il funzionamento della GUI avviene in 3 fasi:
     * 1) all'inizio, ogni pulsante è abilitato e mostra "-"
     * 2) premendo un pulsante, questo si disabilita, insieme a tutti quelli alla sua sinistra
     * 3) premendo un altro pulsante, questo si disabilita, insieme a tutti quelli alla sua destra
     * A quel punto i pulsanti ancora attivi mostrano da sx a dx i numeri 1,2,3,..: premendoli non accade nulla
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
