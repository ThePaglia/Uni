package ex2014.a03.e2;

public class Test {
	
	/*
	 * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine gui.png fornita, attraverso
	 * il completamento della classe SelectorGUI fornita. La GUI serve per impostare un valore intero compreso fra
	 * +20 e -20, iniziando da 0, e mostrandolo via via nel pulsante più a destra. Da sinistra a destra, i primi 5 pulsanti
	 * modificano tale valore come segue (<< toglie 5, < toglie 1, 0 azzera, > aggiunge 1, >> aggiunge 5).
	 * Premuto il pulsante più a destra (che riporta il numero selezionato), si stampi a video la lista delle 
	 * stringhe mostrate nei pulsanti mano a mano premuti, ad esempio::
	 * 
	 * [<<, <<, >, >, >, 0, >>, >, Exit:6]
	 * 
	 * Si noti infatti che con quella sequenza di pressioni, il numero più a destra è il 6.
	 * Uno dei primi 5 pulsanti sia disattivato se comporterebbe l'uscita dal range +20,-20 ammesso.
	 * Il costruttore della classe SelectorGUI accetti un oggetto della classe SelectorModelImpl che implementa l'interfaccia
	 * SelectorModel: si progettino entrambi in modo che rappresentino il modello (nel senso MVC) per questa applicazione,
	 * secondo le indicazioni riportate nella documentazione di SelectorModel.
	 * 
	 * IMPORTANTE: 5 punti in questo esercizio saranno attribuiti a chi riesce a sviluppare SelectorModel e SelectorModelImpl,
	 * in modo da incapsulare in modo coerente le parti del modello, e in modo da ridurre le ripetizioni.
	 *
	 * NOTA SULLA CONSEGNA
	 * Mostrando la soluzione al docente, si produca compilazione ed esecuzione da linea di comando, a partire
	 * da una medesima cartella -- pena decurtazione del punteggio.
	 * 
	 */
	
	public static void main(String[] args){
		new SelectorGUI(new SelectorModelImpl());
	}


}
