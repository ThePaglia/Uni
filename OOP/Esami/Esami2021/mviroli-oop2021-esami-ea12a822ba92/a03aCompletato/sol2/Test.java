package a03a.sol2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
     * che realizza la possibilità di selezionare via via aree rettangolari una dentro l'altra, come segue:
     * 1 - l'utente clicka il vertice in alto a sinistra del rettangolo che vuole individuare e 
     * subito compare il bordo del rettangolo che ha come ulteriore vertice quello diametralmente opposto, in basso-destra
     * (si può assumere per semplicità, se si vuole, che la cella clickata sia nel quadrante alto-sinistra della griglia)
     * 2 - si ricomincia col punto 1, ma ignorando i click che non porterebbero ad un rettangolo incluso nell'ultimo selezionato
     * 3 - il "gioco" finisce quando si seleziona un rettangolo troppo piccolo per averne altri al suo interno: in tal caso si disabilitino
     * tutti i pulsanti
     * 
     * Ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio, SONO CONSIDERATI OPZIONALI I SEGUENTI PUNTI:
     * - scorporamento via delegazione di tutti gli aspetti che non sono di view in una interfaccia+classe esterna
     * - controllo della logica finale di completamento del gioco
     * - controllo della logica di inclusione dei rettangoli
     *  
     * La classe GUI fornita, da modificare, include codice che potrebbe essere utile per la soluzione.
     * 
     * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 9 punti
	 * - qualità della parte opzionale: 5 punti
	 * - correttezza della parte opzionale: 3 punti
     */

    public static void main(String[] args) throws java.io.IOException {
        new GUI(20); 
    }
}
