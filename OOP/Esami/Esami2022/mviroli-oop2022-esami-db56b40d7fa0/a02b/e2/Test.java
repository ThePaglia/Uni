package a02b.e2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, e la cui logica preveda 
     * di poter selezionare un po' di celle, e quindi verificare se ce ne sono esattamente 3 che stanno sulla stessa linea 
     * diagonale (alto-sx/basso-dx): se così fosse, si indicano tutte le celle di quella linea e quindi si ricomincia dall'inizio. 
     * 
     * L'applicazione si comporti complessivamente come segue:
     * - all'inizio tutti i pulsanti sono "vuoti"
     * - alla pressione di un pulsante, se è vuoto viene messa una '*'', se invece c'è una '*' viente tolta
     * - alla prima pressione del pulsante "Check > Restart", si controlli se 3 (non più di 3) celle selezionate
     *   stanno sulla stessa diagonale (alto-sx/basso-dx, come in fig 1)
     *      - se non è così, allora si ignori la pressione di "Check > Restart"
     *      - se se ne trova una (di linea diagonale), allora si disabilitino tutte le celle di quella linea diagonale (come in fig 1)
     *      - se ce ne fosse più d'una (di linea diagonale), se ne disabilitino le cell di una scelta arbitrariamente
     * - in caso si siano disabilitate delle celle, alla prossima pressione di "Check > Restart" l'applicazione riparta
     *   dall'inizio, ossia con tutti i pulsanti vuoti e abilitati, pronti per una nuova esecuzione
     * 
     * Con riferimento alla figura, ipotizzando si siano selezionati tutti quegli "*" e si sia premuto "Check > Restart",
     * vengono disabilitate quelle celle mostrate (le uniche che formano una diagonale alto-sx/basso-dx su cui stanno 3 "*").
     * A questo punto ripremendo "Check > Restart" si riparte dall'inizio.
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - reinizializzazione della partita: ossia, nella parte obbligatoria è sufficiente si esegua un solo run
     * 
     * La classe GUI fornita, da modificare, include codice che potrebbe essere utile per la soluzione.
     * 
     * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 10 punti
	 * - qualità della parte opzionale: 4 punti
	 * - correttezza della parte opzionale: 3 punti
     * - bug di programmazione, o violazione di regole base di programmazione Java, comportano decurtamento del punteggio 
     *   complessivo, anche in caso di bonus
     */

    public static void main(String[] args) throws java.io.IOException {
        new GUI(7); 
    }
}
