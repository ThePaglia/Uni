package a04a.e2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig.png, fornita, 
     * che realizza una semplice animazione, che effettua uno scatto ad ogni pressione del pulsante ">":
     * 1 - all'inizio la griglia è vuota, con un numero al centro 
     * 2 - ad ogni pressione il numero si sposta in una cella adiacente scelta in modo random (equiprobabile)
     * 3 - per adiacente si intende su, giù, destra o sinistra, evitando di uscire dalla griglia
     * 4 - il gioco finisce (e l'applicazione esce) quando il numero finisce in uno dei quattro angoli della griglia
     * 5 - a soli fini di visualizzazione, il numero da mostrare sia alternativamente 1,0,1,0,1,0,.. 
     * Le pressioni dei pulsanti sulle celle non abbiano effetti.  
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
