package a01c.sol2;

public class Test {

	   /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
     * che realizza la possibilità di selezionare un percorso a zig-zag (orizzontale-verticale), come segue:
     * 1 - l'utente clicka una cella, e lì ci compare un '*'
     * 2 - l'utente clicka un'altra cella (in orizzontale o verticale): tutte le celle dalla precedente 
     * a questa vengono quindi riempite con '*' 
     * 3 - si riprocede come da punto 2, ma se prima ci si era mossi orizzontalmente ora ci si deve muovere 
     * verticalmente, e viceversa
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque 
     * al raggiungimento della totalità del punteggio:
     * - scorporamento via delegazione di tutti gli aspetti che non sono di view in una interfaccia+classe esterna
     * - alternanza di orizzontale e verticale (ossia, nella parte opzionale va bene anche se ad ogni passo
     * si procede o orizzontalmente o verticalmente indipendentemente dalla mossa precedente)
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
