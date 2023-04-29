package a05.e2;

public class Test {
	
	/*
	 * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine gui.png, che
	 * realizza un gioco di dadi fra due persone (A e B), in cui ognuno ha un punteggio iniziale, entrambi tirano 
	 * un dado e chi fa punteggio più basso ha un punto in meno, e si va avanti finché uno dei due arriva a zero 
	 * e quini perde la partita.
 	 * La figura mostra un esempio in cui il giocatore A (in alto) ha perso fino a questo punto due volte (due punti)
	 * mentre il giocatore B una volta sola. 
	 * In particolare:
	 * 
	 * 1 la GUI prenda in ingresso il numero di punti iniziali N dei due giocatori (6 nel caso della figura)
	 * 2 la parte alta della GUI mostri N pulsanti disabilitati, con tanti "*" quanti sono i punti di A 
	 * 3 la parte bassa della GUI mostri N pulsanti disabilitati, con tanti "*" quanti sono i punti di B
	 * 4 alla pressione di "Play" si tirino i due dadi, si mostri a console il risultato (ad esempio come in figura)
	 * 5 si tolga il punto al giocatore che ha fatto punteggio più basso (se ne tolga uno a entrambi se pari)
	 * 6 quando uno dei due giocatori arriva a zero, si comunichi in qualche modo chi ha vinto e si chiuda la partita
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque 
	 * al raggiungimento della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - compilazione e esecuzione dell'esempio da linea di comando (a partire dal folder con src/ e bin/)
	 * 
	 * La classe Example (con main) fornisce una GUI con alcune funzionalità probabilmente utili, ma da sistemare
	 * con pertinenza.
	 */
	
	public static void main(String[] args){
		new GUI(6);
	}

}
