package ex2015.a02b.e2;

public class Test {
	
	/*
	 * PRELIMINARE
	 * Si consideri come mero punto di partenza la classe GUIExample fornita (la si esegua e se ne guardi il listato), 
	 * che realizza una GUI che contiene un insieme di pulsanti con valori numerici, che quando premuti mostrano una dialog.
	 * Lo studente usi a propria discrezione e giudizio il codice presente in questa classe, come punto di partenza della 
	 * sua soluzione, ma applicando tutte le riorganizzazioni che ritiene necessarie.
	 * 
	 * ESERCIZIO
	 * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine gui.png (stesso layout dell'esempio).
	 * Questa GUI realizza una cartella semplificata del gioco della Tombola:
	 * - contiene numeri random non ripetuti compresi fra 1 e 20
	 * - è disposta su due file da 5 numeri
	 * - premendo un pulsante questo viene disabilitato
	 * - appena su una fila viene premuto il secondo numero, compare una dialog che dichiara l'ambo (e capiterà una volta per fila)
	 * - appena tutti i numeri sono stati premuti, compare una dialog che dichiara la tombolina (e quindi l'applicazione esce)
	 * 
	 * Una quota significativa del punteggio di questo esercizio (fino al 30% dello stesso) sarà attribuita in
	 * base alla seguente caratteristica di qualità Q che il sistema dovrà avere:
	 * 
	 * Q: Si prevede che il sistema sarà in futuro da modificare, per cambiare il numero e valore dei pulsanti numerici
	 * (sempre però disposti su due file), e l'algoritmo di riconoscimento di ambo e tombolina. Di conseguenza, per il 
	 * Single Responsibility Principle, si usi uno Strategy per "portare fuori" dalla classe della GUI tutto il codice che 
	 * potrebbe essere modificato -- ossia, in una nuova classe + interfaccia di "modello".
	 * 
	 * NOTA SULLA CONSEGNA
	 * Mostrando la soluzione al docente, si produca compilazione ed esecuzione da linea di comando, a partire
	 * da una medesima cartella -- pena decurtazione del punteggio.
	 * 
	 */

	public static void main(String[] args) {
		new GUIExample(); // GUI d'esempio 
		//new GUI();
	}

}
