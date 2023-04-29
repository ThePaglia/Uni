package ex2016.a02a.e2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine gui.png fornita, con 
     * un costruttore che prende in ingresso il numero di pulsanti (tranne Move e Reset).
     * La "X", inizialmente in prima posizione, viene spostata a destra di una posizione ogni volta che si
     * preme il pulsante Move. Arrivata in fondo "rimbalza" e si sposta quindi a sinistra ad ogni presssione
     * di Move. Arrivata di nuovo in fondo si sposta poi a destra e così via.
     * Premendo un pulsante (non Move e non Reset) questo si disabilita (solo se non ha la "X" sopra), e a questo 
     * punto diventa un ostacolo sul quale la X rimbalza. Più ostacoli potranno essere impostati.
     * Alla pressione di Reset, la GUI deve tornare alla configurazione iniziale.
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
