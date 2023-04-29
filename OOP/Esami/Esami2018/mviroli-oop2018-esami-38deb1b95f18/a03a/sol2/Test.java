package a03a.sol2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig.png, fornita, 
     * che contiene una griglia quadrata di pulsanti (6x6) inizialmente vuota, in cui possono essere mostrati 
     * dei numeri incrementali nel seguente modo:
     * - inizialmente ogni casella ha uno 0, lo 0 non viene mai mostrato
     * - premendo un pulsante, lui e tutti i pulsanti vicini (sono 8, apparte i casi "di bordo"), incrementano il loro valore di 1 
     * - quando un pulsante arriva a 5, viene disabilitato
     * 
     * Non appena tutte le caselle hanno un numero positivo, la applicazione si chiuda.
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
