package a02a.sol2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, che consente di
     * piazzare un insieme di alfieri (bishop) del gioco degli scacchi, in modo che nessuno attacchi nessun'altro,
     * ossia nessuno stia nella diagonale di nessun altro.
     * Nello specifico, il comportamento deve essere il seguente:
     * - all'inizio tutti i pulsanti sono "vuoti"
     * - alla pressione di un pulsante, ci si scriva una "B" (bishop), e si disabilitino tutti pulsanti a lui in diagonale (vedi fig.1)
     * - se si preme un pulsante che già presenta una "B" non dve succedere nulla
     * - si proceda iterativamente piazzando le varie "B" e disabilitando le diagonali (in fig.2 si è piazzata una nuova "B")
     * - quando non c'è più nessuna "B" piazzabile, perché è tutto disabilitato, allora premendo una "B" l'applicazione riparta
     *   dall'inizio, ossia con tutti i pulsanti vuoti e abilitati, pronti per una nuova esecuzione
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
