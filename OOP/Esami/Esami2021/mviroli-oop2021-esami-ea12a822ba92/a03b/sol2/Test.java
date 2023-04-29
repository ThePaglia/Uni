package a03b.sol2;

public class Test {

	 /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
     * che realizza la possibilità di mostrare un percorso di numeri incrementali 0->1->2->3... a spirale, ottenibile come segue:
     * 1 - l'utente clicka sempre una cella qualunque della griglia (non importa quale)
     * 2 - ad ogni pressione compare un nuovo numero, che è sempre adiacente (orizzontalmente o verticalmente) rispetto al precedente,
     * e non va mai sopra un numero precedente (considerato "ostacolo") e non esce mai dalla griglia 
     * 3 - alla prima pressione lo zero compare in posizione random (distante almeno 2 dal bordo), e la direzione di riferimento è "in su"
     * 4 - alle successive pressioni, si tenta di procedere in senso orario rispetto alla direzione di riferimento, 
     * e se non si riesce (perché c'è un ostacolo) si procede invece diritto; 
     * (si noti che per procedere in senso orario si deve andare nell'ordine di direzioni SU,DESTRA,GIU',SINISTRA,SU,DESTRA,....
     * 5 - se ci si scontra col bordo o non si può proseguire con la logica precedente, l'applicazione si chiuda
     * 
     *  Il risultato è che la progressione di numeri si deve arricciare a spirale finché non si scontra col bordo
     *  (vedere figura).
     * 
     * Ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio, SONO CONSIDERATI OPZIONALI I SEGUENTI PUNTI:
     * - scorporamento via delegazione di tutti gli aspetti che non sono di view in una interfaccia+classe esterna
     * - controllo della logica finale di completamento del gioco
     * - posizione random iniziale (ossia va bene anche se si comincia dalla cella centrale della griglia)
     *  
     * La classe GUI fornita, da modificare, include codice che potrebbe essere utile per la soluzione.
     * 
     * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 9 punti
	 * - qualità della parte opzionale: 5 punti
	 * - correttezza della parte opzionale: 3 punti
     */


    public static void main(String[] args) throws java.io.IOException {
        new GUI(7); 
    }
}
