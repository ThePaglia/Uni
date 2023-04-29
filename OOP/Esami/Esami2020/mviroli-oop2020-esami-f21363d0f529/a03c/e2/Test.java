package a03c.e2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
     * che simula una mini-partita del gioco "snake". Il serpente è costituito da una fila contigua di pulsanti 
     * con numerazione crescente. La sua testa è quindi il numero più alto presente sulla griglia. Scopo del gioco 
     * è allungare il serpente facendo avanzare la testa il più possibile, mentre la coda (0) è ferma.
     * 
     * Inizialmente:
     * - si inserisca uno 0 in posizione random della prima riga, ed un 1 sotto
     * 
     * Ad ogni passo:
     * - se si preme un pulsante libero della griglia e adiacente (orizzontalmente o verticalmente) alla testa del 
     * serpente (numero più alto presente, 8 in figura), allora questo diventa la nuova testa (quindi 9) -- nel caso in figura,
     * premendo il pulsante sopra o a destra dell'8, tale pulsante diventerebbe il 9; 
     * - in ogni momento, premendo il pulsante ">" si ha lo stesso effetto di aver fatto avanzare di una casella vuota
     * (se c'è, altrimenti non si fa nulla) il serpente, in direzione retta rispetto al suo ultimo spostamento -- nel caso 
     * in figura, premendo ">" comparirebbe il 9 sopra l'8. 
     * - qualunque altra pressione di pulsante non ha effetto 
	 * - per semplicità, l'applicazione non si chiude mai
	 * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - gestione del pulsante ">" per spostamenti sia verticali che orizzontali (ossia ne basta uno dei due)
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
