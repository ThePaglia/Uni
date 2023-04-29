package a05b.e2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
     * che realizza una semplice animazione, che effettua uno scatto ad ogni pressione del pulsante ">", mostrata nella
     * sequenza delle immagini fig1.png, fig2.png, fig3.png, fig4.png,..
     * 1 - all'inizio la griglia è vuota, con una cella attiva (con asterisco) scelta in modo random *non nel bordo* 
     * 2 - ad ogni pressione si attivano 8 celle in più, in direzione orizzontale/verticale/diagonale rispetto a
     *     quella iniziale, a distanza via via crescente
     * 3 - quando l'aggiunta di una cella porterebbe a uscire dalla griglia, si esca  
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
        new GUI(10); // only use odd numbers as arguments!
    }
}
