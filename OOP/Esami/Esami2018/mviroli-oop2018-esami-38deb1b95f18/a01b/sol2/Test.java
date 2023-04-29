package a01b.sol2;

public class Test {

	/*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig.png, fornita, 
     * che contiene una griglia quadrata di pulsanti (5x5), in cui è presente un alfiere indicato con "B" (bishop). 
     * Inizialmente tutti i pulsanti sono abilitati, e l'alfiere sta nella cella (0,0), in alto a sinistra.
     * 
     * Premendo sul pulsante dell'alfiere, si disabilitano tutte le celle in cui non può essere spostato secondo 
     * le regole degli scacchi, dove gli alfieri sono spostano in diagonale di quante caselle vogliono.
     * Considerando la figura, avendo clickato sull'alfiere vengono evidenziate infatti solo le caselle in diagonale,
     * dove lui si può spostare.
     * A questo punto, clickando su una di queste caselle, l'alfiere si sposterà lì e tutte i pulsanti si riabilitano.
     * Si può ricominciare ora a spostarlo, clickando sulla sua posizione, vedendo dove può andare (pulsanti abilitato),
     * e quindi clickando sull'arrivo. 
     * Quando l'alfiere torna in posizione (0,0) si chiuda l'applicazione.
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono 
     * comunque al raggiungimento della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - compilazione e esecuzione dell'esercizio da linea di comando
     * 
     * Indicazioni di punteggio:
     * - correttezza della parte obbligatoria: 9 punti
     * - qualità della parte obbligatoria: 1 punto
     * - qualità della parte opzionale (strategy): 5 punti
     * - compilazione e esecuzione da linea di comando: 2 punti
     * 
     * La classe GUIExample fornita include codice che potrebbe essere utile per la soluzione.
     */

    public static void main(String[] args) throws java.io.IOException {
        // new GUIExample();
        new GUI();
    }
}
