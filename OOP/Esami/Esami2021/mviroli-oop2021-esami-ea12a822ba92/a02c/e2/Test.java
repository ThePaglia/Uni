package a02c.e2;

public class Test {

	/*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita: una sorta di flipper. 
     * Realizza la caduta del simbolo "*", che incontrato un "o" prosegue la sua caduta alla sua sinistra o a destra, come segue:
     * 1 - all'inizio, il "*" è in una posizione random della prima riga, e si posizionano 20 "o" in posizioni random delle altre righe
     * 2 - l'utente agisce clickando una cella qualunque della griglia (non importa quale)
     * 3 - ad ogni clik il "*" scende verso il basso di una posizione, se libera 
     * 4 - se non libera, perchè c'è una "o", deve spostarsi a sinistra o destra (in modo random) senza però finire su una altra "o"
     * 5 - se la "*" giungesse in fondo, la si reinserisca in una nuova cella random della prima riga, continuando
     * 6 - se la "*" finisce in posizione dalla quale non c'è via di uscita si esca dall'applicazione  
     * 
     * Nalle GUI di figura ad esempio, la "*" potrebbe andare a sinistra (e quindi cadrebbe dalla prima colonna, per poi
     * finire nella seconda colonna) oppure a destra (cadendo dalla quarta colonna, e poi trovando altri ostacoli finendo
     * in fondo nella seconda o sesta colonna).
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque 
     * al raggiungimento della totalità del punteggio:
     * - scorporamento via delegazione di tutti gli aspetti che non sono di view in una interfaccia+classe esterna
     * - gestione della chiusura dell'applicazione, e scelta fra spostamento a sx o dx effettivamente random
     *  
     * La classe GUI fornita, da modificare, include codice che potrebbe essere utile per la soluzione.
     * 
     * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 10 punti
	 * - qualità della parte opzionale: 5 punti
	 * - correttezza della parte opzionale: 2 punti
     */


    public static void main(String[] args) throws java.io.IOException {
        new GUI(10); 
    }
}
