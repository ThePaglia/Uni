package ex2014.a03b.sol2;

public class Test {
	
	/*
	 * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine gui.png fornita, attraverso
	 * il completamento della classe CodeGUI fornita. La GUI è costituita da 7 pulsanti con indicazione iniziale "A".
	 * Alla pressione di un pulsante questo passa a "B", poi ripremendolo a "C", poi a "D" e così via. Arrivato a "G" il
	 * pulsante si disattiva e non può più essere premuto. Premendo OK, si stampi a video la stringa di 7 caratteri
	 * ottenuta e si esca. Nel caso dell'immagine fornita, premendo OK si ottenga ESATTAMENTE la stampa:
	 * 
	 * (C:A:B:D:C:G:B)
	 * 
	 * Il costruttore della classe CodeGUI accetti un oggetto della classe CodeModelImpl che implementa l'interfaccia
	 * CodeModel: si progettino entrambi in modo che rappresentino il modello (nel senso MVC) per questa applicazione,
	 * secondo le indicazioni riportate nella documentazione di CodeModel.
	 * 
	 * Se ritenuto utile, si consideri che in Java preso il carattere 'A', allora 'A'+1 fornisce il carattere 'B'.
	 * 
	 * IMPORTANTE: 5 punti in questo esercizio saranno attribuiti a chi riesce a sviluppare CodeModel e CodeModelImpl,
	 * in modo da incapsulare in modo coerente le parti del modello. 
	 *
	 * NOTA SULLA CONSEGNA
	 * Mostrando la soluzione al docente, si produca compilazione ed esecuzione da linea di comando, a partire
	 * da una medesima cartella -- pena decurtazione del punteggio.
	 * 
	 */
	
	public static void main(String[] args){
		new CodeGUI(new CodeModelImpl());
	}

}
