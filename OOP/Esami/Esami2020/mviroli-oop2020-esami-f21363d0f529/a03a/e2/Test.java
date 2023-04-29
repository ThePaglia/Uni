package a03a.e2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
     * che realizza una mini-partita ispirata agli scacchi. 
     * 
     * Inizialmente:
     * - sia N la dimensione della griglia (5 in figura)
     * - c'è una torre (simbolo R -- rook), disposta random nella griglia
     * - ci sono N pezzi nemici (simbolo *), e vanno disposti in modo random nella griglia, non sopra la torre
     * 
     * Il gioco si svolge come segue. Ad ogni passo:    
     *   
     * 1 - si abilitano solo i pulsanti dove la torre si può muovere, che corrispondono a movimenti in orizzontale o verticale,
     * che possono finire sopra un pezzo nemico (*), ma non possono scavalcarlo (si noti infatti in figura che non
     * si abilitano i pulsanti nelle ultime due colonne) 
     * 2 - clickando su un pulsante abilitato si sposta la torre (e quindi si devono ricalcolare i pulsanti da abilitare)
     * 3 - finendo sopra un pezzo nemico lo si mangia, ossia sparisce per sempre dalla partita
     * 4 - il gioco termina quando i pezzi nemici sono finiti
     * 
     * La classe GUI fornita, da modificare, include codice che potrebbe essere utile per la soluzione.
     *  
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * 
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - abilitazione corretta dei pulsanti relativi alle sole posizioni dove poter muoversi (ossia nella parte obbligatoria
     * si possono anche tenere abilitati tutti i pulsanti)
     * 
     * La classe GUI fornita, da modificare, include codice che potrebbe essere utile per la soluzione.
     * 
     * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 10 punti
	 * - qualità della parte opzionale: 5 punti
	 * - correttezza della parte opzionale: 2 punti
	 * 
     */

    public static void main(String[] args) throws java.io.IOException {
        new GUI(5); 
    }
}
