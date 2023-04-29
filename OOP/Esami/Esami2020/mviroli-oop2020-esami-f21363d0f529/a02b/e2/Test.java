package a02b.e2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
     * che realizza una mini-animazione ispirata al gioco denominato 2048:
     * - ad ogni click su una cella, su questa compare il simbolo "*" (se c'è già non succede nulla)
     * - premendo il pulsante "down", tutte le celle si spostano verso il basso (dalla fig1, premendo "down", si passa alla fig2)
     * - premendo il pulsante "up", tutte le celle sispostano analogamente verso l'alto (dalla fig2, premendo "up" si passerebbe alla fig1)
     * - in ogni momento si può premere up/down o una cella
     *  
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - gestione del pulsante "up"
     *  
     * La classe GUI fornita, da modificare, include codice che potrebbe essere utile per la soluzione.
     * 
     * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 10 punti
	 * - qualità della parte opzionale: 5 punti
	 * - correttezza della parte opzionale: 2 punti
     */

    public static void main(String[] args) throws java.io.IOException {
        new GUI(5); 
    }
}
