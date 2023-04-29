package ex2015.a01c.e2;

public class Test {
	
	/*
	 * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine gui.png fornita, che permette
	 * di scrivere numeri interi su un file di TESTO, linea per linea, per poi visualizzarne il contenuto su console.
	 * Il funzionamento concreto è il seguente:
	 * - alla partenza, il pulsante Show permette di mostrare il contenuto del file su console, mentre Show è disabilitato
	 * - aggiungendo un numero, si entra in modalità di inserimento, quindi Show si disabilita e Save si abilita
	 * - si potranno aggiungere altri numeri
	 * - se si aggiunge una stringa che non è un numero, si stampi un errore su console, senza aggiungere nulla
	 * - premendo Save, il file venga salvato e chiuso, e si esca dalla modalità di inserimento tornando alla situazione iniziale
	 *  
	 * Si prediligeranno le soluzioni che incapsulano in un oggetto di "controllo" (e relativa interfaccia) tutto quello che non
	 * riguarda aspetti di mera view, ossia la gestione del file, i comandi per verificare la modalità di inserimento, il comando 
	 * per aggiungere un nuovo numero.
	 * Come visto in aula, per leggere il file si usi ad esempio la funzionalità java.nio.file.Files.lines di Java 8, oppure 
	 * la classe BufferedReader che decora un InputStreamReader che decora un FileReader. Per scrivere si usi semplicemente
	 * un PrintStream, oppure un BufferedWriter che decora un FileWriter.
	 * 
	 * * NOTA SULLA CONSEGNA
	 * Mostrando la soluzione al docente, si produca compilazione ed esecuzione da linea di comando, a partire
	 * da una medesima cartella -- pena decurtazione del punteggio.
	 * 
	 */

	/*
	 * Questo main stampa il nome del file da cui preleva il testo (a.txt all'interno del percorso HOME).
	 * Si lasci creare il file di testo alla propria applicazione, salvandoci dei dati.
	 */
	public static void main(String[] args) throws java.io.IOException{
		System.out.println("Looking at file "+System.getProperty("user.home")+System.getProperty("file.separator")+"a.txt");	
		//new GUI(System.getProperty("user.home")+System.getProperty("file.separator")+"a.txt");
	}

}
