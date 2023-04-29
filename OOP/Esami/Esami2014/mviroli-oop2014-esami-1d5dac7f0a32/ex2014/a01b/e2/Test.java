package ex2014.a01b.e2;

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
	 * il completamento della classe AddressFormGUI fornita. L'utente dovrà inserire i vari campi (inizialmente vuoti) e alla 
	 * pressione del pulsante OK, se i dati sono corretti, verrà stampata a video una stringa tipo:
	 * 
	 * [Nome=Mirko;Cognome=Viroli;Codice Fiscale=VRLMRK00T02C574B;Anno di nascita=1900;Via=Sacchi 3;CAP=47521;Città=Cesena;Provincia=FC]
	 * 
	 * altrimenti una indicazione "Errore". I dati siano considerati corretti se: 
	 * - nome ha almeno 1 carattere
	 * - cognome ha almeno 2 caratteri 
	 * - cf ne ha esattamente 16
	 * - anno di nascita è un numero compreso fra 1900 e 2015 inclusi
	 * - via ha almeno 7 caratteri
	 * - CAP è un numero costituito da 5 caratteri
	 * - Città ha almeno 3 caratteri
	 * - Provincia ha esattamente 2 caratteri
	 *  
	 * Una quota significativa del punteggio di questo esercizio (fino al 40% dello stesso) sarà attribuita in
	 * base alla seguente caratteristica di qualità Q che il sistema dovrà avere:
	 * 
	 * Q: Volendo il committente riusare il vostro codice per future form, si dovrà incapsulare il più possibile 
	 * la decisione sul numero, nome e dimensione dei campi della form, sulla condizione che deve valere affinché 
	 * ogni dato prodotto da un campo sia considerato corretto, e sul risultato finale riepilogativo costruibile 
	 * a partire dai valori correnti dei campi -- in questo caso una String come sopra mostrato, ma in generale anche altro.
	 * 
	 * Si organizzi il contenuto della classe AddressFormGUI di conseguenza (eventualmente creando altre
	 * interfacce/classi se reputato utile), considerando che verrà comunque richiamata come descritto dal main qui sotto.
	 * 
	 * NOTA SULLA CONSEGNA
	 * Mostrando la soluzione al docente, si produca compilazione ed esecuzione da linea di comando, a partire
	 * da una medesima cartella -- pena decurtazione del punteggio.
	 * 
	 */
	
	public static void main(String[] args) {
		new AddressFormGUI();
	}

}
