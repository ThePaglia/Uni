package ex2014.a01.e2;

public class Test {
	
	/*
	 * PRELIMINARE
	 * Si consideri come mero punto di partenza la classe FormGUI fornita (la si esegua e se ne guardi il listato), 
	 * che realizza una GUI che contiene una "form" a tre campi: nome, cognome e codice fiscale. 
	 * Se le condizioni espresse nell'handler dell'evento ActionPerformed sono soddisfatte, allora alla pressione 
	 * del pulsante OK l'applicazione viene terminata. Il codice di questa GUI può essere riusato (copia-incolla) a 
	 * piacimento per l'esercizio di cui sotto (ma va opportunamento migliorato con tutte le caratteristiche discusse nel corso).
	 * 
	 * ESERCIZIO
	 * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine gui.png, attraverso
	 * il completamento della classe FibonacciFormGUI fornita. Il costruttore accetta il numero dei campi della 
	 * GUI: l'utente dovrà quindi inserire la sequenza di Fibonacci (1,1,2,3,5,8,13..: i primi due sono necessariamente 1,1 
	 * e poi ogni numero è la somma dei due precedenti). Alla pressione del pulsante OK, se la sequenza è corretta questa verrà
	 * stampata a video e l'applicazione verrà terminata, altrimenti la pressione del pulsante non abbia alcun effetto.
	 * 
	 * Una quota significativa del punteggio di questo esercizio (fino al 40% dello stesso) sarà attribuita in
	 * base alla seguente caratteristica di qualità Q che il sistema dovrà avere:
	 * 
	 * Q: Volendo il committente riusare il vostro codice per future form, si dovrà incapsulare il più possibile 
	 * la decisione sul numero e nome dei campi della form, sulla condizione che deve valere affinché i dati 
	 * prodotti dall'utente siano considerati corretti, e sull'azione da eseguire quando tali dati sono corretti 
	 * e viene premuto il pulsante OK.
	 * 
	 * Si organizzi il contenuto della classe FibonacciFormGUI di conseguenza (eventualmente creando altre
	 * interfacce/classi se reputato utile), considerando che verrà comunque richiamata come descritto dal main qui sotto.
	 * 
	 * NOTA SULLA CONSEGNA
	 * Mostrando la soluzione al docente, si produca compilazione ed esecuzione da linea di comando, a partire
	 * da una medesima cartella -- pena decurtazione del punteggio.
	 * 
	 */

	public static void main(String[] args) {
		new FibonacciFormGUI(5); // deve funzionare anche passando 5 o 10
	}

}
