package a02a.sol2;

public class Test {

	 /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
     * che realizza la possibilità di selezionare un percorso di numeri incrementali 0->1->2->3..., come segue:
     * 1 - l'utente clicka sempre una cella qualunque della griglia (non importa quale)
     * 2 - ad ogni pressione compare un nuovo numero, che è sempre adiacente (orizzontalmente o verticalmente) rispetto al precedente  
     * 3 - alla prima pressione lo zero compare in posizione random, e la direzione del prossimo numero è "in su"
     * 4 - alle successive pressioni, compare un nuovo numero nella direzione corrente, se la cella è vuota
     * 5 - se non è vuota o si sbatte su una cella già occupata, si cambi direzione in modo da poter proseguire
     * 6 - quando non è più possibile procedere il gioco si chiuda (nella figura, clickando compare il 39 e quindi si chiude) 
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque 
     * al raggiungimento della totalità del punteggio:
     * - scorporamento via delegazione di tutti gli aspetti che non sono di view in una interfaccia+classe esterna
     * - gestione della fine del gioco
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
        new GUI(7); 
    }
}
