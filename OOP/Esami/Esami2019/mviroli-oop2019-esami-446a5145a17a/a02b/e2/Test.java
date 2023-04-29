package a02b.e2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig.png, fornita, che si comporta come segue:
     * - inizialmente tutte le celle sono vuote
     * - clickando su una cella in un qualunque momento, compare la "X" 
     * - si assume che nessuno clickerà due volte nella stessa colonna (non c'è bisognio di fare coding/test a proposito)
     * - clickando su ">" tutte le "X" si spostano di una casella in basso
     * - quando una "X" finisce nell'ultima riga, da lì in poi l'effetto della ">" è che la sposterà verso l'alto
     * - quando una "X" finisce nella prima riga, da lì in poi l'effetto della ">" è che la sposterà verso il basso
     * 
     * Quindi l'effetto finale è che premendo con continuità ">" si vedrà una animazione delle "X" che si spostano
     * su e giù nella griglia.
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
        new GUI(5);
    }
}
