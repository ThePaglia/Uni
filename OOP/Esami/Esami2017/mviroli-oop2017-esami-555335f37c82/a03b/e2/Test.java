package a03b.e2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig.png, fornita, 
     * che contiene una griglia quadrata di pulsanti inizialmente senza label. Il costruttore di GUI deve prendere in 
     * ingresso il numero di righe (o colonne)---e quindi dovra funzionare per qualunque numero (diciamo fra 3 e 7).
     * Alla pressione di un pulsante P, su tutta la diagonale (nord/est-sud/ovest) su cui si trova P si cambino le label 
     * dei pulsanti da " " a "*", o viceversa a seconda del loro stato.
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - compilazione e esecuzione dell'esercizio da linea di comando
     * 
     * La classe GUIExample fornita include codice che potrebbe essere utile per la soluzione.
     */

    public static void main(String[] args) throws java.io.IOException {
        // new GUIExample();
        new GUI(5);
    }
}
