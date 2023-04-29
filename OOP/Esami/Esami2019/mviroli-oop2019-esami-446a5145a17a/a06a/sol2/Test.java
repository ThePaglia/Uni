package a06a.sol2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig.png, fornita,
     * dove tutte le celle sono sempre clickabili, e alcune di queste conterranno un numero naturale.
     * 1 - all'inizio la griglia è vuota;
     * 2 - clickando su una cella C vuota, questa assume il valore 0, e ogni altra cella (con numero) che non sia sulla 
     *     stessa riga o sulla stessa colonna di C si incrementa di 1;
     * 3 - clickano su una cella che ha già il numero, l'applicazione si chiude. 
     * 
     * Considerando ad esempio l'immagine fornita, clickando sulla cella a destra dello 0, si incrementerebbero
     * soltanto le due celle che contengono il 3, perché le altre sono sulla stessa riga a colonna di quella clickata. 
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - compilazione e esecuzione dell'esercizio da linea di comando (I COMANDI DEVONO ESSERE GIA' PRONTI)
     *  
     * La classe GUIExample fornita include codice che potrebbe essere utile per la soluzione.
     * 
     * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 11 punti
	 * - qualità della parte opzionale: 5 punti
	 * - compilazione/esecuzione da linea di comando: 1 punti
     */

    public static void main(String[] args) throws java.io.IOException {
        // new GUIExample();
        new GUI(7);
    }
}
