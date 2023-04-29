package a06b.sol2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
     * che realizza una semplice animazione, che effettua uno scatto ad ogni pressione del pulsante ">"
     * 1 - all'inizio la griglia è vuota, e con uno scatto si attiva (con asterisco) la casella centrale 
     * 2 - ad ogni pressione si attiva SEMPRE una nuova casella adiacente all'ultima attivata (sopra/sotto/destra/sinistra) 
     * scelta in modo random, ma evitando sempre di selezionare una cella già attivata, ed evitando di uscire dalla griglia
     * 3 - quando nessuna cella è attivabile in base al punto 2 qui sopra, si esca  
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
        new GUI(7); // only use odd numbers as arguments!
    }
}
