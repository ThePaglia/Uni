package a04.sol2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
     * che realizza la possibilità di selezionare via via caselle adiacenti per identificare una espressione
     * matematica della qualse si vuole calcolare il risultato, come segue:
     * 0 - alla partenza si riempia (probabilisticamente) la griglia al 50% di numeri e al 50% di operatori: i numeri 
     * siano fra 0 e 9 inclusi, gli operatori siano +, - e *
     * 1 - l'utente clicka la prima volta la cella che contiene un numero
     * 2 - da quel punto in poi clicka celle adiacenti alla precedente (orizzontalmente o verticalmente), che contengono
     * alternando un operatore, un numero, un operatore, un numero, e così via 
     * 3 - ogni volta che un cella corretta è clickata viene deselezionata
     * 4 - quando si è clickato un numero si avrà sempre individuato una espressione matematica compiuta,
     * quindi sarà possibile premere il pulsante "Quit", che causerà la chiusura dell'applicazione e la stampa
     * su console del risultato della valutazione dell'espressione.
     * 
     * Se la sequenza dei pulsanti premuti era ad esempio 7,-,4,*,5, il risultato sarà (7-4)*5 = 15: ossia
     * si assuma che il calcola associa a sinistra -- e quindi si può calcolare l'espressione "mano a mano".
     * 
     * Ai fini della possibilità di correggere l'esercizio, anche se concorrono comunque al raggiungimento 
     * della totalità del punteggio, SONO CONSIDERATI OPZIONALI I SEGUENTI ASPETTI:
     * - scorporamento via delegazione di tutti gli aspetti che non sono di view in una interfaccia+classe esterna
     * - controllo della adiacenza dei pulsanti premuti
     *  
     * La classe GUI fornita, da modificare, include codice che potrebbe essere utile per la soluzione.
     * 
     * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 9 punti
	 * - qualità della parte opzionale: 5 punti
	 * - correttezza della parte opzionale: 3 punti
     */

    public static void main(String[] args) throws java.io.IOException {
        new GUI(5); 
    }
}
