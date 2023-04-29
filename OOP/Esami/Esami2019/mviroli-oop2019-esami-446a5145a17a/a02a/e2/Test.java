package a02a.e2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
     * che realizza una semplice animazione: ad ogni pressione del pulsante ">", si transita di configurazione.
     * Da quella mostrata in fig1.png, a quella in fig2.png, quindi a quella in fig3.png, quindi a quella in fig4.png, per poi tornare
     * indietro a fig3.png, eccetera, ossia con progessione: 1,2,3,4,3,2,1,2,3,4,..
     * Si noti che l'applicazione dovrà funzionare per qualunque intero positivo dispari passato al costruttore (dimensione griglia),
     * facendo giungere tutte le "X" al centro della griglia.
     * Alla pressione di un pulsante che contiene una X, l'applicazione si chiuda.
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - compilazione e esecuzione dell'esercizio da linea di comando
     * 
     * Suggerimento: si consideri, se ritenuto utile, che la configurazione delle "X" in un certo istante è fortemente simmetrica
     * (orizzontalmente, verticalmente, e diagonalmente), o anche che è invariante per rotazioni (di 90 gradi) rispetto al centro. 
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
        new GUI(7); // only use odd numbers as arguments!
    }
}
