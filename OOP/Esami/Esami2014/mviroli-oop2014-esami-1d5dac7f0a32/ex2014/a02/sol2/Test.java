package ex2014.a02.sol2;

public class Test {
	
	/*
	 * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine gui.png fornita, attraverso
	 * il completamento della classe CombinatioGUI fornita. Il costruttore accetta una stringa col nome di file con cui 
	 * lavorare. La GUI richiede la pressione dei pulsanti A..E in qualunque ordine e ripetizione. Dopo la pressione di 
	 * almeno un pulsante A..E, il pulsante Write si abilita (metodo JButton.setEnabled).
	 * Premendolo, l'informazione relativa aalla lista dei pulsanti A..E premuti viene in qualche modo salvata
	 * sul file (si usi la modalità preferita di gestione del file), e a questo punti si riparte dalla situazione iniziale
	 * nella quale si possono ripremere i pulsanti, e così via via.
	 * Ossia questa GUI permette di inserire e scrivere combinazioni di A..E (ad esempio, AAAAA,BBBB,CCC,ABCDE,...). 
	 * Premuto il pulsante Quit, si chiude il file in scrittura, lo si riapre, e lo si legge stampando a video quali 
	 * combinazioni sono presenti, ad esempio in questa forma (ma una equivalente va comunque bene):
	 * 
	 * [A, A, A, A, A]
	 * [B, B, B, B]
	 * [C, C, C]
	 * [A, B, C, D, E]
	 * 
	 * Una difficoltà nella gestione dei file potrebbe essere quella di accordgersi in lettura che il file è finito.
	 * Si valuti la possibilità di scrivere sul file la lista vuota di pulsanti come marcatore di fine file.
	 *
	 * NOTA SULLA CONSEGNA
	 * Mostrando la soluzione al docente, si produca compilazione ed esecuzione da linea di comando, a partire
	 * da una medesima cartella -- pena decurtazione del punteggio.
	 * 
	 */

	public static void main(String[] args) {
		String filename = System.getProperty("user.home")+System.getProperty("file.separator")+"buttons.dat";
		// in Linux sarebbe: /home/mirko/buttons.dat.. ed equivalente in Windows
		System.out.println("Using file: "+filename);
		new CombinationGUI(filename);
	}

}
