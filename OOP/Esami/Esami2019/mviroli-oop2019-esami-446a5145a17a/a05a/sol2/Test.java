package a05a.sol2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig.png, fornita.
     * 1 - all'inizio la griglia ha 10 (sempre esattamente 10!!) celle selezionabili (ossia con una "X") 
     * 2 - clickando su una cella C con una "X", verranno deselezionate (togliendo la "X"), le seguenti celle:
     *   - la cella C
     *   - tutte le celle vicine a C (in orizzontale/verticale/diagonale) con una "X" 
     *   - ricorsivamente, le vicine delle vicine con una "X", finché se ne trovano
     * 3 - quando nessuna cella ha più una "X" l'applicazione si chiuda 
     * 
     * Suggerimento: la deselezione a partire da una cella C si fa quindi togliendo la X a lei, 
     *               e poi deselezionando tutte le vicine con la X, ricorsivamente  
     * 
     * Ad esempio, nella figura fig.png:
     * - selezionando la cella nella prima riga si deseleziona solo lei
     * - selezionando la cella nella ultima riga si deseleziona lei e la sua vicina nella prima colonna
     * - selezionando una cella del "cluster" a destra, ad esempio quella in seconda riga, si deselezionano 
     *   ricorsivamente tutte le celle di quel cluster (rimangono solo le 3 celle nelle prime 3 colonne)
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - compilazione e esecuzione dell'esercizio da linea di comando (I COMANDI DEVONO ESSERE GIA' PRONTI)
     *  
     * La classe GUIExample fornita include codice che potrebbe essere utile per la soluzione.
     * 
     * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 10 punti
	 * - qualità della parte opzionale: 5 punti
	 * - compilazione/esecuzione da linea di comando: 2 punti
     */

    public static void main(String[] args) throws java.io.IOException {
        // new GUIExample();
        new GUI(7); // only use odd numbers as arguments!
    }
}
