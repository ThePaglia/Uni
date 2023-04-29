package a01a.e2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita:
     * - all'inizio tutti i pulsanti sono "vuoti"
     * - alla pressione di un pulsante, se è vuoto viene messa una '*'', se invece c'è una '*' viente tolta
     * - l'applicazione termini quando valgono entrambe le condizioni:
     *      1) le ultime 3 pressioni hanno messo una '*'
     *      2) le ultime 3 pressioni sono relative ad una fila di 3 pulsanti consecutivi in diagonale
     * Con riferimento alla figura, ipotizzando si sia clickato sulla cella in prima riga, poi in quella
     * in seconda riga, e poi in quella in terza riga:
     * - se uno premesse la cella in quarta riga e seconda colonna, allora l'applicazione terminerebbe
     * - con qualunque altro click, l'applicazione NON terminerebbe
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - verifica della consecutività degli ultimi 3 click, ossia basta uscire se la '*' che si aggiunge
     *  forma una fila da 3 in diagonale con quelle presenti al momento
     * 
     * Attenzione: dato un click, le diagonali possibili sono lungo quattro direzioni (alto-sx, alto-dx, basso-sx, basso-dx)
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
