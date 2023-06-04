package a03b.e2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, 
     * la cui logica permette di giocare una sorta di partita a scacchi fra soli pedini contro un computer
     * che non muove mai. Quindi si muovono solo i pedini del giocatore "*" contro quelli di un computer
     * "o" che li tiene sempre fermi: si ricordi che un pedino "*" si muove di una sola casella in verticale 
     * (verso l'alto, se non occupata), mentre mangia un pedino "o" in diagonale di una casella 
     * (sempre però procedendo verso l'alto, quindi in diagonale alto-dx o alto-sx). 
     * 
     * L'applicazione si comporti complessivamente come segue:
     * 1) all'inizio si posizionano i 4 pedini "*" nell'ultima riga in basso, e i 4 pedini "o"
     *    in modo random nella prima o seconda riga
     * 2) l'utente clicka su un suo pedino ("*"), che si muove come segue:
     * -- se il click è su una cella senza pedino "*" il click viene ignorato
     * -- se il click è su un pedino che non si può muovere il click viene ignorato
     * -- se il pedino può mangiare (in diagonale quindi), allora mangia
     * -- se può mangiare due diversi pedini nemici se ne scelga uno a caso
     * -- se non può mangiare allora si muove in avanti
     * 3) fatta una mossa valida, si torna al punto 2)
     * 4) il gioco termina in due possibili situazioni:
     * -- se non ci sono più pedini "o" perché tutti mangiati, allora si resetti il gioco
     * -- se ci sono ancora pedini "o" ma quelli "*" non si possono più muovere perché bloccati, 
     *    non si faccia nulla: l'applicazione rimarrà bloccata e la si dovrà riavviare
     * 
     * Ad esempio, partendo da fig1, se uno clicka sulla "*" più a sinistra allora il pedino si sposta
     * in avanti, portando alla fig2. Se ci riclicka sopra allora si muove in diagonale (alto-dx) mangiando
     * un pedino "o". Se ci riclicka di nuovo sopra si muove in diagonale (alto-sx) mangiando un altro
     * pedino "o", eccetera.
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - gestione del termine della partita
     * - obbligatorietà del mangiare: va bene anche se si sceglie in modo random se mangiare o no
     * 
     * La classe GUI fornita, da modificare, include codice che potrebbe essere utile per la soluzione.
     * 
     * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 10 punti
	 * - qualità della parte opzionale: 3 punti
	 * - correttezza della parte opzionale: 4 punti
     * - bug di programmazione, o violazione di regole base di programmazione Java, comportano decurtamento 
     *   del punteggio complessivo, anche in caso di bonus
     */

    public static void main(String[] args) throws java.io.IOException {
        new GUI(4); 
    }
}
