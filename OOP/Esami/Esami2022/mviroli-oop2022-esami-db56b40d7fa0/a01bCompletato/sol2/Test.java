package a01b.sol2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita:
     * - all'inizio tutti i pulsanti sono "vuoti"
     * - alla pressione di un pulsante, ai quattro pulsanti vicini ad esso in diagonale 
     *   (alto-sx, alto-dx, basso-sx, basso-dx... se presenti in griglia) viene cambiato 
     *   stato: se è vuoto viene messa una '*', se invece c'è una '*' viente tolta
     * - l'applicazione termini quando alla pressione di un pulsante ci sono 3 vicini a cui viene tolta la '*'
     *   e uno a cui viene messa -- quindi non potrà essere un click su una cella del bordo
     * Con riferimento alla figura, clickando sul pulsante di seconda riga e seconda colonna, l'applicazione si chiude. 
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - gestione dei casi di bordo: si potrà assumere che uno non clicki mai su una cella del bordo
     *  
     * La classe GUI fornita, da modificare, include codice che potrebbe essere utile per la soluzione.
     * 
     * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 10 punti
	 * - qualità della parte opzionale: 4 punti
	 * - correttezza della parte opzionale: 3 punti
     * - errori di programmazione comportano decurtamento del punteggio complessivo
     */

    public static void main(String[] args) throws java.io.IOException {
        new GUI(5); 
    }
}
