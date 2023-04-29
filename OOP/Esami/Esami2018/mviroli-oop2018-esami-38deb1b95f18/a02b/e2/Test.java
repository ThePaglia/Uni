package a02b.e2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig.png, fornita, 
     * che contiene una griglia quadrata di pulsanti (10x10) inizialmente vuota, in cui possono essere aggiunti
     * degli "*", come segue:
     * - premendo un pulsante, lo si seleziona, e questo si disabilita
     * - premendo un altro pulsante, si saranno definiti due vertici opposti di un rettangolo di celle, e quindi 
     * si riempia di "*" il perimetro di questo rettangolo
     * 
     * Queste due operazioni possano essere svolte una dietro l'altra più volte, riempiendo la griglia di vari rettangoli.
     * Non appena si tenta di disegnare un rettangolo già interamente disegnato, la applicazione si chiuda.
     * Si noti che i due vertici potranno essere qualsiasi (alto-sinistra/basso-destra, alto-destra/basso-sinistra, etc..).
     * 
     * Ad esempio: la situazione in figura è creata dopo aver disegnato due rettangoli.
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
