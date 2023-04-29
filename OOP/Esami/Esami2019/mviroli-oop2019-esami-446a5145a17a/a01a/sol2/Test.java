package a01a.sol2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig.png, fornita, 
     * che realizza una mini-versione di Battaglia Navale. All'avvio si prepara un campo di gara quadrato 
     * (dimensione indicata dal primo parametro del costruttore), e si colloca in modo random una nave (di lunghezza
     * indicata nel secondo parametro) in posizione orizzontale (si stampi su console tale posizione, a fini di debug). 
     * Alla pressione di un pulsante, questo si disabiliti e la sua label diventi "X" se viene colpita la nave, "0" altrimenti.
     * Colpite tutte le celle della nave si avrà vinto, mentre al quinto tentativo sbagliato (ossia alla quinta "O"), si avrà perso:
     * in entrambi i casi si stampi il risultato e si esca.
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - compilazione e esecuzione dell'esercizio da linea di comando
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
        new GUI(5,3);
    }
}
