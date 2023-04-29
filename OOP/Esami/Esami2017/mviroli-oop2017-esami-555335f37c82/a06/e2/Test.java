package a06.e2;

public class Test {
	
	/*
	 * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine gui.png fornita (N=4), attraverso
	 * il completamento della classe GUI fornita (il cui costruttore prenda il numero N dei pulsanti (tranne Reset) da
	 * visualizzare). I pulsanti hanno indicazione iniziale 1.
	 * Alla pressione di un pulsante questo modifica il proprio numero, che passa al valore ottenuto sommando: il valore
	 * attuale del pulsante corrente, quello a sinistra (se c'è) e quello a destra (se c'è). Premendo Reset, 
	 * tutti i pulsanti tornino al valore 1.
	 * 
	 *  Ad esempio, partendo da (1,1,1,1), se si preme il primo pulsante si va in (2,1,1,1), se si preme quindi
	 *  il secondo pulsante si va in (2,4,1,1), se si preme quindi il terzo in (2,4,6,1), se si preme quindi
	 *  il secondo si va in (2,12,6,1), e premendo reset si torna in (1,1,1,1), e così via.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - compilazione e esecuzione dell'esempio da linea di comando (a partire dal folder con src/ e bin/)
	 * 
	 * La classe Example (con main) fornisce una GUI con alcune funzionalità probabilmente utili. 
	 */
	
	public static void main(String[] args){
		new GUI(4);
	}

}
