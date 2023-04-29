package a01a.sol2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
     * che realizza la possibilità di selezionare via via aree rettangolari, come segue:
     * 1 - l'utente clicka una cella, e lì ci compare un '1'
     * 2 - l'utente clicka un'altra cella, e a questo punto tutte le celle che stanno nel rettangolo che ha 
     * come estremi le due celle clickate assumono un '*' 
     * 3 - si ricomincia col punto 1, finché non succede che tutte le celle hanno un '*', a quel punto le si disabilitano tutte
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * - scorporamento via delegazione di tutti gli aspetti che non sono di view in una interfaccia+classe esterna
     * - controllo della logica finale di completamento del gioco
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
        new GUI(20); 
    }
}
