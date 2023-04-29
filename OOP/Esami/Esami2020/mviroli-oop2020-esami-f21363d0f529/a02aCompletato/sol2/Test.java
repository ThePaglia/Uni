package a02a.sol2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
     * che realizza una mini-animazione ispirata agli scacchi. Sia N la dimensione della matrice. Inizialmente:
     * - c'è una pedina (simbolo p), disposta random nell'ultima riga
     * - ci sono N pezzi nemici (simbolo *), e vanno disposti in modo random nella griglia, non nell'ultima riga
     * Premere i pulsanti della griglia non comporta alcun effetto.
     * Ad ogni pressione del pulsante next (">"), ci sarà una mossa automatica della pedina, che segue le regole degli scacchi nel senso che: 
     *  
     * 1 - se può mangiare un pezzo lo mangia; una pedina mangia muovendosi in diagonale di 1 posto, ossia su-sinistra o su-destra 
     * 2 - se non può mangiare, allora avanza di 1 posto (ossia in su), ma solo se la casella di arrivo è libera
     * 
     * Se la pedina non si può muovere il pulsante si deve disabilitare, e la animazione è finita
     *  
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - far mangiare la pedina sia su-sinistra che su-destra (ossia è sufficiente una sola delle due)
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
