package a02c.e2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
     * che permette all'utente di premere pulsanti segnando via via l'ordine di pressione:
     * - alla pressione del primo pulsante, la sua label diventerà 1.. premendo quello dopo 2, poi 3, eccetera 
     * - se si preme un pulsante già premuto in passato, non deve succedere nulla
     * - l'applicazione termini quando gli ultimi 4 pulsanti premuti formano un quadrato di pulsanti contigui (di lato 2),
     * e che non sia adiacente al bordo (ossia non tocchi nessuno dei quattro bordi).
     * Ad esempio, nella figura fornita, l'applicazione si chiude se si preme il pulsante sotto al 4, perchè 4,5,6,7
     * formano il quadrato cercato, e tale quadrato NON è adiacente al bordo della griglia.
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - verifica dell'adiacenza del quadrato col bordo
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
        new GUI(5); 
    }
}
