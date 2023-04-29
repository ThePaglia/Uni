package a01b.e2;

public class Test {
	
	/*
	 * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine gui.png fornita, attraverso
	 * una classe GUI con costruttore che accetta il numero di pulsanti che deve contenere (N). Il funzionamento 
	 * della GUI è così espresso:
	 * 
	 * - All'avvio tutti gli N pulsanti indicano "*"
	 * - Viene generata una sequenza di N numeri random compresi fra 0 e 99, ognuno "nascosto" sotto ad un pulsante
	 * - L'utente clicka un pulsante: se questo nasconde il numero più piccolo fra gli N, allora il numero viene mostrato
	 *   e il pulsante disabilitato, altrimenti non succede nulla
	 * - Successivamente, l'utente dovrà trovare il prossimo numero più piccolo fra quelli non ancora mostrati, e così via 
	 *   fino a trovarli tutti
	 * - Ad ogni click, lo standard output riporti il numero di tentativi finora effettuati
	 * - Quando tutti i numeri sono stati mostrati, l'applicazioni non venga chiusa, ossia rimanga bloccata
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - compilazione e esecuzione dell'esempio da linea di comando (a partire dal folder con src/ e bin/)
	 * 
	 * La classe Example (con main) fornisce una GUI con alcune funzionalità probabilmente utili. 
	 */

	public static void main(String[] args) {
		new GUI(5);
	}

}
