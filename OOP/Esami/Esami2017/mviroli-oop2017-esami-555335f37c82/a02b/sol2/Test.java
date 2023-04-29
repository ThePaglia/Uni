package a02b.sol2;

import java.io.IOException;

public class Test {
	
	/*
	 * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine gui.png fornita. Nella JComboBox
	 * di sinistra si mostrano le linee di un file di testo il cui nome va passato al costruttore della classe GUI.
	 * Si consideri un file di testo "a.txt" nella cartella src/, che contenga le quattro linee:
	 * 
	 * linea 1
	 * linea 2
	 * linea 3
	 * linea 4
	 * 
	 * Il funzionamento della GUI è il seguente:
	 * - alla partenza, si carica il file di testo nella JComboBox (si assuma che non ci siano mai linee ripetute), e quindi la combobox parte con 4 linee
	 * - di default è selezionata la prima linea
	 * - premendo il pulsante "Remove", la linea selezionata viene eliminata 
	 * - premendo il pulsante "Concat", la linea selezionata viene modificata concatenandone una sua copia: 
	 * 		ad esempio la prima linea diventerebbe "linea 1linea 1"
	 * - premendo il pulsante "Add", dopo la linea selezionata ne viene inserita un'altra ottenuta da questa aggiungendo un "*" all'inizio: 
	 * 		ad esempio dopo la prima linea si aggiungerebbe la linea "*linea 1" (e quindi ora il file avrebbe 5 linee: "linea 1", "*linea 1", "linea 2",..)
	 * - in tutti e tre i casi sopra, la modifica va subito attuata (i) su file, (ii) sulla combobox, e (iii) mostrata stampando le nuove linee su standard output
	 * - a quel punto l'utente può premere un nuovo pulsante e procedere ad altre modifiche
	 * - si esce solo chiudendo la GUI
	 *  
	 * Come visto in aula, per leggere il file si usi ad esempio la funzionalità java.nio.file.Files.lines di Java 8, oppure 
	 * la classe BufferedReader che decora un InputStreamReader che decora un FileReader. Per scrivere si usi ad esempio
	 * un PrintStream, oppure un BufferedWriter che decora un FileWriter.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - gestione delle modifiche su file (mentre la lettura iniziale del file è obbligatoria)
     * - compilazione e esecuzione dell'esempio da linea di comando
     * 
     * La classe GUIExample fornita include codice che potrebbe essere utile per la soluzione.
	 * 
	 */

	/*
	 * Questo main utilizza il file a.txt dentro alla cartella src del progetto).
	 */
	public static void main(String[] a) throws IOException {	
		String fileName = "src"+System.getProperty("file.separator")+"a.txt";
		System.out.println("Looking at file "+fileName);
		final GUI ui = new GUI(fileName);
	}

}
