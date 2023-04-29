package a05.e2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita
     * Inizialmente, ogni cella presenti un numero random nel range [0..9].
     * 
     * Clickando su una cella C in cui è presente un numero N, questo viene "svuotato e propagato" ai vicini, ossia:
     * - il numero N viene ricopiato in tutte le celle adiacenti di C (in orizzontale, verticale e diagonale), 
     *   ma solo in quelle che non hanno una X
     * - nella cella C viene messa una X
     * 
     * Clickando invece su una cella C con una X, questa "raccoglie i valori dei vicini svuotandoli", ossia:
     * - si deve ottenee la somma S di tutti i numeri presenti nelle celle adiacenti a C
     * - nella cella C si mette il numero S
     * - in tutte le celle adiacenti C si mette la X
     * 
     * Il gioco termina quando tutte le celle hanno la X.
     * 
     * Ad esempio, la situazione in fig1 è stata ottenuta clickando sulla cella dove ora c'è la X, 
     * che conteneva un 2: il 2 infatti si è propagato in tutte le celle adiacenti! Se ora si clicka sulla X,
     * questa prende il numero 16 (la somma delle 8 adiacenti), e tutte le adiacenti diventano X.
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * - considerare fra le adiacenze anche le celle in diagonale
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - qualità del codice della logica di gioco
     *  
     * La classe GUI fornita, da modificare, include codice che potrebbe essere utile per la soluzione.
     * 
     * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 10 punti
	 * - qualità della parte opzionale: 5 punti
	 * - correttezza della parte opzionale: 2 punti
     */

    public static void main(String[] args) {
        new GUI(4); 
    }
}
