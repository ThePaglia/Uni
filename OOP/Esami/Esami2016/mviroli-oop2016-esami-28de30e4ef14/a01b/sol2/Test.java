package ex2016.a01b.sol2;

import ex2016.a01b.e2.GUI;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig.png fornita, con 
     * un costruttore che prende in ingresso un nome di file DI TESTO e il numero N di pulsanti numerici da visualizzare.
     * I pulsanti conterranno sempre l'indicazione 0,1,..,N-1, come da figura.
     * 
     * La GUI legge da file di testo una sequenza di numeri interi nel range [0,N-1], scritti uno per linea.
     * Dovrà mostrare questi numeri uno per uno, e lo farà abilitando il solo pulsante corrispondente:
     * premendo tale pulsante, la GUI mostrerà quello successivo e così via.
     * Arrivati alla fine del file, tutti i pulsanti vengono disabilitati.
     * Premendo Reset in un qualunque momento, l'esecuzione della GUI ricomincia dall'inizio (rileggendo il contenuto 
     * del file, che nel frattempo potrebbe essere cambiato).
     * 
     * Ad esempio, se il file di testo ha il contenuto:
     * 
     * 1
     * 3
     * 2
     * 1
     * 
     * allora i pulsanti abilitati saranno via via, appunto, quello con 1, poi quello con 3, poi quello con 2, poi quello con 1.
     * 
     * Per la lettura su file si può usare, ad esempio (ma non solo) BufferedReader che decora FileReader.
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - effettiva lettura su file (altrimenti la si simuli con la lettura di una sequenza di interi arbitraria e costante)
     * - compilazione e esecuzione dell'esempio da linea di comando
     * 
     * La classe GUI fornita come punto di partenza già contiene in minima parte del codice utile e eventualmente riusabile. 
     * 
     */

    public static void main(String[] args) throws Exception {
        new GUI("src/ex2016/a01b/sol2/b.txt",10); // Si noti che questo file è presente nella cartella di questo file java 
    }
}
