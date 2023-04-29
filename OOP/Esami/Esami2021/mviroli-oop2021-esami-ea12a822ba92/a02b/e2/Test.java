package a02b.e2;

public class Test {

	 /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
     * che realizza la possibilità di spostare il simbolo "*", deviato dalle lettere L,R, come segue:
     * 1 - all'inizio, il "*" è in una posizione random dell'ultima riga, e si posizionano 20 lettere L,R nella griglia in modo random
     * 2 - l'utente agisce clickando una cella qualunque della griglia (non importa quale)
     * 3 - ad ogni clik il "*" si sposta in una casella adiacente seguendo la direzione corrente (inizialmente "up") 
     * 4 - se si incontra una lettera, si cambia direzione da lì in poi (L-left, R-right) e la si mantiene fino alla prossima lettera incontrata
     * 5 - se la "*" uscirebbe dalla griglia si chiuda l'applicazione 
     *
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque 
     * al raggiungimento della totalità del punteggio:
     * - scorporamento via delegazione di tutti gli aspetti che non sono di view in una interfaccia+classe esterna
     * - gestione della chiusura dell'applicazione
     *  
     * La classe GUI fornita, da modificare, include codice che potrebbe essere utile per la soluzione.
     * 
     * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 10 punti
	 * - qualità della parte opzionale: 5 punti
	 * - correttezza della parte opzionale: 2 punti
     */


    public static void main(String[] args) throws java.io.IOException {
        // new GUIExample();
        new GUI(10); 
    }
}
