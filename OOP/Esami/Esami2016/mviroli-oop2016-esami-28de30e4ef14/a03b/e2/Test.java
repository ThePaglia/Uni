package ex2016.a03b.e2;

public class Test {
	
	/* Realizzare una classe GUI con costruttore che prende in ingresso un numero di pulsanti (>3), tale che quando istanziata crei un 
	 * JFrame con l'aspetto mostrato nella figura allegata.
	 * Si noti che gli N pulsanti sono tutti disabilitati tranne il primo, e che inizialmente mostra una X in prima posizione.
	 * La GUI deve essere reattiva e thread-safe.
	 * Deve, ogni 300 msec circa aggiornare la posizione di X spostandola a destra di una posizione -- giunta in fondo, 
	 * dovrà tornare indietro (e così via, avanti e indietro).
	 * Appena premuto il pulsante, questo si disabilità, e a questo punto appena la X ci arriva sopra, la GUI si chiuda.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - compilazione e esecuzione dell'esempio da linea di comando
     * 
     * La classe GUI fornita come punto di partenza già contiene in minima parte del codice utile, thread-safe, e eventualmente riusabile. 
	 * 
*/

	public static void main(String[] args) throws java.io.IOException{
		new GUI(10);
	}
}
