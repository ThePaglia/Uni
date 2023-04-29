package ex2014.a02b.e2;

public class Test {
	
	/*
	 * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine gui.png fornita, attraverso
	 * il completamento della classe RandomBinaryGUI fornita. Il costruttore accetta una stringa col nome di file con cui 
	 * lavorare. La GUI richiede di impostare la dimensione di una sequenza binaria, compresa fra 1 e 10 inclusi,
	 * attraverso due pulsanti di incremento e decremento (">" e "<") -- si faccia attenzione a disabilitarli al momento giusto
	 * per evitare di uscire dal range, usando il metodo JButton.setEnabled. 
	 * Scelta la giusta dimensione N, si potrà premere il pulsante Write: a quel punto verrà generata una sequenza di N 
	 * valori binari (o 0 o 1), e verrà scritta in qualche modo sul file (si usi la modalità preferita di gestione del file), 
	 * e a questo punto si riparta dalla situazione iniziale nella quale si può reimpostare la dimensione della prossima 
	 * sequenza, la si può scrivere su file, e così via via.
	 * Ossia questa GUI permette di scrivere su file sequenze random di booleani (ad esempio, 100,10010,1110010,...). 
	 * Il pulsante Quit in ogni momento può essere usato: premendolo, si chiude il file in scrittura, lo si riapre, 
	 * e lo si legge stampando a video quali sequenze sono presenti, esattamente in questa forma (caso N=10,10,10,10,10,8)
	 * 
	 * 1011100101
	 * 1111011010
	 * 1111010011
	 * 1100001011
	 * 1011100100
	 * 00101000
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
		new RandomBinaryGUI(filename);
	}

}
