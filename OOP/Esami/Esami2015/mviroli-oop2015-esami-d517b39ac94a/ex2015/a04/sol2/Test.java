package ex2015.a04.sol2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig.png fornita, 
     * che realizza una semplicissima variante della battaglia navale. Il costruttore di GUI deve prendere in 
     * ingresso 4 elementi:
     * - numero r di righe
     * - numero c di colonne
     * - numero n di "navi" presenti (una occupa esattamente una cell)
     * - numero t di tentativi per distruggerle
     * 
     * Alla partenza, si posizionino in modo random le n navi nella griglia, 
     * e a fini di debug si stampino su standard output tali posizioni. 
     * A quel punto l'utente dovrà clickare sui pulsanti.
     * Ogni volta:
     * - se intercetta una nave il pulsante mostrerà "X", altrimenti "0"
     * - il pulsante viene disabilitato
     * Alla fine:
     * - giunti a t tentativi, se le navi non sono tutte prese compare una JDialog che indica "you lost"
     * - se tutte le navi sono prese con t o meno tentativi compare una JDialog che indica "you won"
     * in entrambi i casi a quel punto si chiuda l'applicazione.
     *  
     * Si prediligeranno (ma senza essere necessarie ai fini della sufficienza) le soluzioni che inseriscono
     * in una classe separata da GUI e ben incapsulata (pattern strategy) tutti gli aspetti del Model.
     * 
     * La classe GUIExample (la si esegua e se ne controlli il codice) fornisce codice facilmente adattabile
     * per gestire i componenti grafici necessari, e anche alcune funzionalità accessorie. 
     * 
     * * NOTA SULLA CONSEGNA
     * Mostrando la soluzione al docente, si produca compilazione ed esecuzione da linea di comando, a partire
     * da una medesima cartella -- pena decurtazione del punteggio.
     */

    public static void main(String[] args) throws java.io.IOException {
        // new GUIExample();
        new GUI(5,3,2,4);
    }
}
