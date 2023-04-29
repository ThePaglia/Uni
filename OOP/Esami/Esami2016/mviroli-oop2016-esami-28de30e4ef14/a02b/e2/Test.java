package ex2016.a02b.e2;

public class Test {
	
	/* Realizzare una classe GUI con costruttore che prende in ingresso un numero di pulsanti (>3), tale che quando istanziata crei un 
	 * JFrame con l'aspetto mostrato nella figura allegata.
	 * Si noti che gli N pulsanti sono sempre disabilitati, e mostrano sempre 3 X.
	 * La GUI deve essere reattiva e thread-safe.
	 * Deve, ogni 300 msec circa aggiornare la posizione delle 3 X, spostandole tutte a destra di una casella. 
	 * Laddove una X esca dai margini ricompaia dall'altra parte (ossia se le posizioni sono da 0 a 9, l'andare in posizione
	 * 10 deve causare il fatto di andare in realtà in posizione 0), in modo che si mostrino sempre 3 X.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - compilazione e esecuzione dell'esempio da linea di comando
     * 
     * La classe GUI fornita come punto di partenza già contiene in minima parte del codice utile e eventualmente riusabile. 
	 * 
*/

	public static void main(String[] args) throws java.io.IOException{
		new GUI(10);
	}
}
