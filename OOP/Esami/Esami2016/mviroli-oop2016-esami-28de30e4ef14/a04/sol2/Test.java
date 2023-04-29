package ex2016.a04.sol2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine gui.png fornita, con 
     * un costruttore senza argomenti, una JComboBox con tre selezioni possibili ([l,l,l,l,l],[r,r,r],[l,r,l,r]).
     * e due pulsanti con etichetta "l" e "r".
     * - Impostata una selezione, bisognerà premere i pulsanti nel corrispondente ordine indicato, a quel punto la finestra si chiuderà.
     * - Cambiando la selezione in un qualunque momento, si dovrà ripartire daccapo a premere i pulsanti.
     * - Sbagliando pressione del pulsante bisogna ripartire daccapo.
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - generalizzazione del codice, che permetta minime modifiche in caso il tipo di selezioni cambi (p.e., aggiungendo [r,l,r,l,r])
     * 
     * La classe GUI fornita come punto di partenza già contiene in minima parte del codice utile e eventualmente riusabile. 
     * 
     */

    public static void main(String[] args){
        new GUI();  
    }
}
