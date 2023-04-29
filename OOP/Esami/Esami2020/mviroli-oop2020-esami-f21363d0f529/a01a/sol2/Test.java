package a01a.sol2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
     * che realizza una versione semplificata del Forza4, che chiameremo Forza3--
     * 1 - all'inizio la griglia è vuota, e giocherà il giocatore '0' clickando su una cella (comparirà lo 0) 
     * 2 - poi al prossimo click sarà il turno del giocatore 'X' a clickare (comparirà la X)
     * 3 - si procede fino alla vittoria di un giocatore, o al riempimento di tutte le celle
     * Regole del gioco:
     * A - un giocatore può clickare su una cella solo se quella sotto (qualora esista) non è vuota, altrimenti non succede nulla
     * B - vince un giocatore che riesce a inserire il suo segno in 3 celle contigue verticali o orizzontali
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - verifica del tris orizzontale per conclusione gioco (verifica verticale e griglia completa sono invece obbligatori)
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
        new GUI(4); 
    }
}
