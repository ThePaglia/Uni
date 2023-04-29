package a06.e2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita.
     * Inizialmente, ci sia una "*" in prima posizione; 3 numeri random compresi fra 1 e 5 (inclusi) siano
     * posizionati a caso (random) nelle posizioni successive. La "*" corrisponde ad una sorta di proiettile, sparato 
     * da sinistra a destra: quando colpisce un numero, che rappresenta una sorta di ostacolo, questo cala di 1, e a 
     * quel punto parte un altro proiettile, sempre dalla prima posizione. Quando si colpisce un ostacolo con valore 1, 
     * questo scompare del tutto (si distrugge), e il prossimo proiettile andrà quindi a colpire l'ostacolo successivo. 
     * Quando non ci sono più ostacoli l'applicazione si chiuda. 
     * Il tempo viene fato scorrere di un "tick" premendo il pulsante ">".
     * 
     * Ad esempio, partendo dalla situazione in fig1:
 	 * - clickando su ">", il proiettile si sposta a destra di una casella (fig2)
 	 * - clickand ancora su ">", il proiettile "colpirebbe" l'ostacolo di valore 4: il risultato è che il 4 diventa 3, 
 	 * e un nuovo proiettile riparte in prima posizione (fig3)
     * - e così via (quando il 3 verrà colpito diventerà 2, poi 1, e se colpito ancora si cancellerà, e a quel punto
     * il prossimo ostacolo che sarà colpibile sarà l'1 in penultima posizione), finché tutti i numeri saranno
     * scomparsi.
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * - considerare 3 numeri/ostacoli all'inizio del run, ossia, per la parte opzionale basta che ce ne sia uno solo
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - qualità del codice della logica di gioco
     *  
     * La classe GUI fornita, da modificare, include codice che potrebbe essere utile per la soluzione.
     * 
     * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 10 punti
	 * - qualità della parte opzionale: 5 punti
	 * - correttezza della parte opzionale: 2 punti
     */

    public static void main(String[] args) {
        new GUI(6); 
    }
}
