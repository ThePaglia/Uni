package a03a.e2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig.png, fornita, 
     * che si comporta come segue:
     * - inizialmente c'è una X in alto a sinistra
     * - clickando su una cella in un qualunque momento, compare una "O" 
     * - clickando su ">" la "X" si sposta sul bordo della griglia di una casella, in senso orario
     * - quando una "X" finisce su uno "O" l'applicazione si chiuda
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque 
     * al raggiungimento della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - compilazione e esecuzione dell'esercizio da linea di comando (DEVONO ESSERE GIA' PRONTI)
     * 
     * La classe GUIExample fornita include codice che potrebbe essere utile per la soluzione.
     * 
     * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 10 punti
	 * - qualità della parte opzionale: 5 punti
	 * - compilazione/esecuzione da linea di comando: 2 punti
     */

    public static void main(String[] args) throws java.io.IOException {
        // new GUIExample();
        new GUI(5);
    }
}
