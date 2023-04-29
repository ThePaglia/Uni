package ex2015.a03b.sol2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig.png fornita, 
     * che legge le linee di un file di testo (il cui nome è passato al costruttore), e le mostra 5 a 5, con
     * pulsanti Next e Prev (che si disabilitano quando non avrebbero effetto). In particolare:
     * il file di testo **andrà posizionato** nella home directory e chiamarsi 'b.txt' (si può usare l'allegato);
     * 
     * Si prediligeranno (ma senza essere necessarie ai fini della sufficienza) le soluzioni che inseriscono
     * in una classe separata da GUI e ben incapsulata (pattern strategy) tutti gli aspetti che NON sono di View,
     * e sicuramente l'accesso al file e il mantenimento della lista complessiva delle linee.
     * 
     * Come visto in aula, per leggere il file si usi ad esempio la funzionalità java.nio.file.Files.lines di Java 8, 
     * oppure la classe BufferedReader che decora un FileReader. 
     * 
     * La classe GUIExample (la si esegua e se ne controlli il codice) fornisce codice facilmente adattabile
     * per gestire i componenti grafici necessari. 
     * 
     * * NOTA SULLA CONSEGNA
     * Mostrando la soluzione al docente, si produca compilazione ed esecuzione da linea di comando, a partire
     * da una medesima cartella -- pena decurtazione del punteggio.
     */


    public static void main(String[] args) throws java.io.IOException {
        // new GUIExample();
        System.out.println("Looking at file " + System.getProperty("user.home") + System.getProperty("file.separator") + "a.txt");
        new GUI(System.getProperty("user.home") + System.getProperty("file.separator") + "b.txt");
    }
}
