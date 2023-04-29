package a01b.sol2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
     * che realizza la possibilità di selezionare un angolo rettangolo, come segue:
     * 1 - l'utente clicka una cella, vertice dell'angolo, e lì ci compare un '1'
     * 2 - l'utente clicka un'altra cella (in orizzontale o verticale) e lì ci compare un '2' -- se ne clicka una 
     * sbagliata, ossia in diagonale, non deve succedere nulla
     * 3 - l'utente clicka un'altra cella (in direzione perpendicolare rispetto alla precedente) e lì ci compare
     * un '3' -- se ne clicka una sbagliata, non deve succedere nulla
     * 4 - a questo punto l'insieme delle celle che collegano '1' con '2' e '1' con '3' vengono riempite con '*'
     * 5 - tutte le celle si disabilitano
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * - scorporamento via delegazione di tutti gli aspetti che non sono di view in una interfaccia+classe esterna
     * - possibilità che '2' sia in orizzontale o in verticale rispetto a '1' (ossia, nella parte opzionale va 
     * bene assumere ad esempio che '2' sia solo in verticale, e quindi '3' sia in orizzontale, o viceversa)
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
