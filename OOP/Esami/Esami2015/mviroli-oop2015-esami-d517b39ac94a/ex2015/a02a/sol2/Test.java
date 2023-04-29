package ex2015.a02a.sol2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI *reattiva* con l'aspetto mostrato nell'immagine fig.png fornita, 
     * che legge le linee di un file di testo (il cui nome è passato al costruttore), e poi le mostra via via (una ogni 2 secondi). 
     * Il pulsante stop interrompe la visualizzazione e disabilita i pulsanti, mentre il pulsante mark aggiunge in coda alla
     * linea corrente un "*" (senza interrompere il meccanismo di visualizzazione).
     *  
     * Si prediligeranno (ma senza essere necessarie ai fini della sufficienza) le soluzioni che riescono a mostrare l'aggiunta dell'"*"
     * proprio al momento della pressione del pulsante mark, e senza che questo comporti modifiche alla pausa 
     * dei 2 secondi. Si assuma per semplicità che il pulsante mark non venga premuto più di una volta al secondo -- circa.
     * 
     * Come visto in aula, per leggere il file si usi ad esempio la funzionalità java.nio.file.Files.lines di Java 8, oppure 
     * la classe BufferedReader che decora un FileReader. 
     * Relativamente alla gestione thread-safe di una GUI reattiva, si controlli (e riusi a piacere ma con pertinenza) il codice
     * di esempio della GUI reattiva contatore mostrato in CGUI. 
     * 
     * * NOTA SULLA CONSEGNA
     * Mostrando la soluzione al docente, si produca compilazione ed esecuzione da linea di comando, a partire
     * da una medesima cartella -- pena decurtazione del punteggio.
     */

    public static void main(String[] args) throws java.io.IOException {
        // new GUIExample();
        System.out.println("Looking at file " + System.getProperty("user.home") + System.getProperty("file.separator") + "a.txt");
        new GUI(System.getProperty("user.home") + System.getProperty("file.separator") + "a.txt");
    }
}
