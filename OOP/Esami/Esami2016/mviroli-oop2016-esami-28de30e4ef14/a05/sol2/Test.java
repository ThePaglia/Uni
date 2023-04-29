package pr2016.a05.sol2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine gui.png fornita, con 
     * un costruttore che prende in ingresso il numero di pulsanti (tranne Move).
     * La "X", inizialmente in prima posizione, viene spostata a destra di una posizione ogni volta che si
     * preme il pulsante Move, se la checkbox è spuntata. Se non è spuntata invece si dovrà andare a sinistra.
     * Arrivandi in fondo, a sinistra o destra, non si proseguirà oltre: sarà possibile spostarsi solo
     * cambiando la spunta alla checkbox muovendosi.
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
