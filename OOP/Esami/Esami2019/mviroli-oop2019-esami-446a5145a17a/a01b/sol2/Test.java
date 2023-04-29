package a01b.sol2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig.png, fornita, 
     * che realizza una mini-versione di Campo Minato. All'avvio si prepara un campo di gara quadrato 
     * (dimensione indicata dal primo parametro del costruttore), e si collocano in modo random le mine (in numero
     * indicato nel secondo parametro) facendo attenzioni che siano in posizioni distinte 
     * (si stampi su console la loro posizione, a fini di debug). 
     * Alla pressione di un pulsante, questo si disabilita: se contiene una mina si esca indicando la sconfitta, 
     * altrimenti si mostri sul pulsante quante mine sono presenti in un vicino della cella clickata 
     * (in orizzontale, verticale o diagonale).
     * Colpite tutte le celle che non hanno delle mine, si avrà vinto: si produca una stampa a console e si esca.
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
        new GUI(4,2);
    }
}
