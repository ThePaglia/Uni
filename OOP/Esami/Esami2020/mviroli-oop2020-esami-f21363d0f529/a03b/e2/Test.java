package a03b.e2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
     * che realizza una mini-partita ispirata agli scacchi. 
     * 
     * Inizialmente:
     * - sia N la dimensione della griglia (5 in figura)
     * - c'è un alfiere (simbolo B -- bishop), disposto random nella griglia
     * - ci sono N pezzi nemici (simbolo *), e vanno disposti in modo random nella griglia, non sopra l'alfiere
     * 
     * Il gioco si svolge come segue. Ad ogni passo:    
     *   
     * 1 - si abilitano solo i pulsanti dove l'alfiere si può muovere, che corrispondono a movimenti in diagonale,
     * che possono finire sopra un pezzo nemico (*), ma non possono scavalcarlo (si noti infatti in figura che non
     * si abilita il pulsante nell'ultima colonna) 
     * 2 - clickando su un pulsante abilitato si sposta l'alfiere (e quindi si devono ricalcolare i pulsanti da abilitare)
     * 3 - finendo sopra un pezzo nemico lo si mangia, ossia sparisce per sempre dalla partita
     * 4 - il gioco non finisce mai
     *  
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - abilitazione corretta dei pulsanti relativi alle sole posizioni dove poter muoversi
     *  
     * La classe GUI fornita, da modificare, include codice che potrebbe essere utile per la soluzione.
     * 
     * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 10 punti
	 * - qualità della parte opzionale: 5 punti
	 * - correttezza della parte opzionale: 2 punti
     */

    public static void main(String[] args) throws java.io.IOException {
        new GUI(5); 
    }
}
