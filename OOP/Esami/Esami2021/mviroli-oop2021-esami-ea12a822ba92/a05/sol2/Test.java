package a05.sol2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
     * che realizza la possibilità di selezionare via via caselle adiacenti per identificare una espressione
     * logica della qualse si vuole calcolare il risultato, come segue:
     * 0 - alla partenza si riempia di valori booleani (True,False) e di operatori booleani (AND, OR, XOR), 
     * in posizioni alterne come mostrato in figura---per il resto la scelta di un valore o operatore sia (equi-)probabilistica; 
     * 1 - l'utente clicka la prima volta la cella che contiene un valore;
     * 2 - da quel punto in poi clicka celle adiacenti alla precedente (orizzontalmente o verticalmente), che contengono
     * quindi un operatore, un valore, un operatore, un valore, e così via; 
     * 3 - ogni volta che un cella corretta è clickata viene deselezionata
     * 4 - quando si è clickato un valore si avrà sempre individuato una espressione logica compiuta: se il suo risultato,
     * interpretato da sinistra a destra è FALSE, l'applicazione si chiuda subito.
     * 
     * Se la sequenza dei pulsanti premuti era ad esempio TRUE,AND,TRUE,OR,FALSE, il risultato sarà (T and T) or F = T or F = T, 
     * e quindi la finestra non si chiude -- si assuma cioè associatività a sinistra, e quindi si può calcolare l'espressione "mano a mano".
     * Invece con T or F xor T = (T  or F) xor T = T xor T = F la finestra si chiude (appena clickato sull'ultimo F).
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
