package ex2014.a05.e1;

public class Test {
	
	/*
	 * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine gui.png fornita, che permette
	 * di indovinare un numero "goal" da 1 a 100 (inclusi) che è stato generato in modo random alla partenza dell'applicazione
	 * e che deve subito essere mostrato su standard output.
	 * - L'utente inserirà nella textfield un tentativo (guess), e la gui mostrerà nella parte bassa se il numero tentato
	 * è maggiore o minore del goal
	 * - Se l'utente indovina il numero, nella parte bassa si indicheranno il numero di tentativi necessari a indovinare
	 * e la finestra non permetterà più alcun input (metodo setEnabled su pulsante e textfield)
	 * - Se l'utente dopo 8 tentativi ancora non ha indovinato, la finestra venga chiusa e si riporti un messagio di sconfitta
	 * su standard output
	 *  
	 * Si prediligeranno le soluzioni che incapsulano in un oggetto di "modello" la gestione della generazione del numero
	 * e della risposta ad un tentativo.
	 * 
	 * La classe AnswerGameGUI già fornita contiene una impalcatura per la GUI. 
	 *    
	 * * NOTA SULLA CONSEGNA
	 * Mostrando la soluzione al docente, si produca compilazione ed esecuzione da linea di comando, a partire
	 * da una medesima cartella -- pena decurtazione del punteggio.
	 * 
	 */

	public static void main(String[] args) {
		new AnswerGameGUI();
	}

}
