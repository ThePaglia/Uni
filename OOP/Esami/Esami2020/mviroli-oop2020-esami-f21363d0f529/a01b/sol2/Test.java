package a01b.sol2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
     * che realizza un mini-gioco ispirato agli scacchi. Inizialmente:
     * - la K rappresenta il re, e va posizionato in basso a destra
     * - le p rappresentano pedine nemiche, sono esattamente 3, e vanno posizionate in modo random 
     *  ovunque tranne che nelle ultime 2 righe
     *  
     * 1 - il giocatore sposta il re di una casella alla volta, clickando 
     * (spostamento di una casella, in orizzontale, verticale o diagonale) 
     * 2 - il re non si può spostare in una casella se nel farlo avrebbe una pedina adiacente, in alto-sinistra o alto-destra
     * (con riferimento alla figura, non potrebbe andare ad esempio su di uno, né potrebbe fare sinistra-sinistra-su, 
     *  né sinistra-sinistra-sinistra-su) 
     * 3 - se il re finisce al posto di una pedina, queste viene rimossa per sempre dal gioco
     * 
     * Il gioco termina quando non ci sono più pedine.
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - verifica della adiacenza con una pedina
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
