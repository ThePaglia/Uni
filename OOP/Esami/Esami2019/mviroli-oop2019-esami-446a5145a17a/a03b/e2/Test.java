package a03b.e2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
     * che realizza una semplice animazione, che effettua uno scatto ad ogni pressione del pulsante ">":
     * 1 - all'inizio la griglia è vuota
     * 2 - alla prima pressione compare un X in posizione random della prima riga
     * 3 - a quel punto ad ogni scatto la X scende di una posizione verso il basso
     * 4 - quando la X "si deposita", perché ha raggiunto l'ultima riga della griglia o è su una X precedente, 
     *     allora si ritorna al punto 2
     * 5 - la prima volta che una X in prima riga è depositata, l'applicazione si chiuda 
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - compilazione e esecuzione dell'esercizio da linea di comando (DEVONO ESSERE GIA' PRONTI)
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
        new GUI(4); // only use odd numbers as arguments!
    }
}
