package a04.e2;

public class Test {
	
	/*
	 * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine gui.png, che
	 * realizza un gioco simile a Yahtzee, ossia una variante del poker giocata coi dadi, come segue:
	 * 
	 * 1) all'inizio del gioco si tirano 5 dadi (numeri random compresi fra 1 e 6 inclusi) mostrandoli nei pulsanti
	 * 2) premendo uno o più di questi pulsanti, questi si disabilitano
	 * 3) a questo punto, premendo il pulsante Draw, si ritirano i dadi relativi ai pulsanti disabilitati
	 * 4) si riabilitano tutti i pulsanti e si stampa su System.out il risultato conseguito (vedere sotto)
	 * 5) si riparte dal punto 2 sopra, indefinitamente
	 * 
	 * I risultati possibili non dipendono dall'ordine dei dati, e sono i seguenti:
	 * - STRAIGHT (se i dadi formano la sequenze 2,3,4,5,6 o 1,2,3,4,5)
	 * - YAHTZEE (tutti i dadi uguali tra loro, ad esempio: 2,2,2,2,2 o 5,5,5,5,5)
	 * - FOUR (quattro dadi uguali tra loro, e uno diverso, ad esempio: 1,1,2,1,1 o 4,2,4,4,4)
	 * - FULL (tre dadi uguali tra loro, e gli altri due uguali tra loro, ad esempio: 1,1,2,2,1 o 5,6,5,6,5)
	 * - THREE (tre dadi uguali, e gli altri diversi, ad esempio: 1,2,3,1,1 o 4,3,4,2,4)
	 * - NOTHING (in tutti gli altri casi)
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque 
	 * al raggiungimento della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - riconoscimento di FULL e STRAIGHT
     * - compilazione e esecuzione dell'esempio da linea di comando (a partire dal folder con src/ e bin/)
	 * 
	 * La classe Example (con main) fornisce una GUI con alcune funzionalità probabilmente utili, ma da sistemare
	 * con pertinenza. 
	 */
	
	public static void main(String[] args){
		new GUI();
	}

}
