package ex2014.a06.sol1;

public class Test {
	
	/*
	 * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine gui.png fornita, che permette
	 * di scorrere tutte le linee di un file di testo il cui nome è fornito in input al costruttore. In particolare,
	 * i pulsanti Up e Down (che permettono di muoversi in una linea precedente o successiva) dovranno venire disabilitati
	 * se si è rispettivamente all'inizio o alla fine del file. Sarà consentito leggere il file tutto all'inizio tenendolo in memoria.
	 * Per mostrare una linea si usi un JTextField inizializzato a 80 caratteri di larghezza.
	 *  
	 * Si prediligeranno le soluzioni che incapsulano in un oggetto di "modello" (e relativa interfaccia) la gestione del file, 
	 * e lo scorrimento delle sue linee, lasciando fuori solo gli aspetti grafici (view) e la gestione degli eventi.
	 * Come visto in aula, per leggere il file si usi ad esempio la funzionalità java.nio.file.Files.lines di Java 8, oppure 
	 * la classe BufferedReader che decora un InputStreamReader che decora un FileInputStream.
	 * 
	 * La classe LinesGUI già fornita contiene una impalcatura per la GUI. 
	 *    
	 * * NOTA SULLA CONSEGNA
	 * Mostrando la soluzione al docente, si produca compilazione ed esecuzione da linea di comando, a partire
	 * da una medesima cartella -- pena decurtazione del punteggio.
	 * 
	 */

	/*
	 * Questo mail stampa il nome del file da cui preleva il testo (a.txt all'interno del percorso HOME).
	 * Si crei un file di testo e lo si usi per fare le prove.
	 */
	public static void main(String[] args) throws java.io.IOException{
		System.out.println("Looking at file "+System.getProperty("user.home")+System.getProperty("file.separator")+"a.txt");	
		new LinesGUI(System.getProperty("user.home")+System.getProperty("file.separator")+"a.txt");
	}

}
