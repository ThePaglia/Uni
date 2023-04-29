package ex2015.a01b.sol2;

public class Test {

    /**
      * PRELIMINARE
	 * Si consideri come mero punto di partenza la classe GUIExample fornita (la si esegua e se ne guardi il listato), 
	 * che realizza una GUI che contiene un insieme di pulsanti con delle stringhe, e tre pulsanti di calcolo in fondo,
	 * premendo uno dei quali compare una JDialog.
	 * Lo studente usi a propria discrezione e giudizio il codice presente in questa classe, come punto di partenza della 
	 * sua soluzione, ma applicando tutte le riorganizzazioni che ritiene necessarie.
	 * 
	 * ESERCIZIO
	 * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine gui.png (stesso layout dell'esempio,
	 * ma diverse stringhe da mostrare).
	 * In questa GUI:
	 * - premendo un pulsante numerico, questo viene disabilitato
	 * - premendo un pulsante operazione in fondo, compare una JDialog che mostra il risultato dell'operazione
	 * - l'operazione "OF LENGTH 4" dà TRUE se tutte le stringhe hanno lunghezza 4
	 * - l'operazione "< 1000" dà TRUE se tutte le stringhe hanno valore < 1000 escluso il carattere finale
	 * - l'operazione "TRAILING P" dà TRUE se l'ultimo carattere della stringa è una 'p'
	 * - se nessun pulsante numerico è stato premuto, l'esecuzione di una operazione mostri un risultato "INVALID"
	 * - alla chiusura della JDialog, si ripristini lo stato iniziale dei pulsanti, per poter impostare una nuova operazione 
	 * 
	 * Una quota significativa del punteggio di questo esercizio (fino al 40% dello stesso) sarà attribuita in
	 * base alla seguente caratteristica di qualità Q che il sistema dovrà avere:
	 * 
	 * Q: Si prevede che il sistema sarà in futuro da modificare, per cambiare il numero e stringhe dei pulsanti,
	 * e il numero e tipo di operazioni da effettuare. Di conseguenza, per il Single Responsibility Principle, si usi
	 * uno Strategy per "portare fuori" dalla classe della GUI il codice che potrebbe essere modificato -- ossia, in una nuova
	 * classe + interfaccia di "modello".
	 * 
	 * NOTA SULLA CONSEGNA
	 * Mostrando la soluzione al docente, si produca compilazione ed esecuzione da linea di comando, a partire
	 * da una medesima cartella -- pena decurtazione del punteggio. 
     */
    public static void main(String[] args) {
        new GUI();
    }

}
