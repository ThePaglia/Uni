package a03a.e2;

import java.io.IOException;

public class Test {
	
	/*
	 * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine gui.png fornita. Nei pulsanti di sinistra
	 * si mostrano via via le linee di un file di testo il cui nome va passato al costruttore della classe GUI.
	 * Si consideri un file di testo "a.txt" nella cartella src/, che contenga le linee:
	 * 
	 * linea 1
	 * linea 2
	 * linea 3
	 * linea 4
	 * linea 5
	 * linea 6
	 * 
	 * Il funzionamento della GUI è il seguente:
	 * - premendo i pulsanti relativi alle linee questi si disabilitano
	 * - premendo il pulsante "Close", le linee relative ai pulsanti NON disabilitati vengono ri-scritte sul file, e l'applicazione termina 
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
