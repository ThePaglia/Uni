package a02a.sol2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig.png fornita, 
     * che contiene una sequenza di 5 pulsanti con valori numerici inizialmente random (da 0 a 9 incluso), e 
     * poi i pulsanti "<" e ">". Inizialmente è "selezionato" il primo numero. Uno e un solo numero
     * può essere selezionato in un certo istante, e ciò viene mostrato dal fatto che il pulsante sul quale
     * si trova è disabilitato. 
     * Un numero selezionato è spostabile con i tasti "<" e ">": Premendo pulsante "<" il numero selezionato 
     * si sposta a sinistra di una casella (se non esce dal range), premendo ">" si sposta a destra 
     * di una casella (se non esce dal range). Quando un numero si sposta viene fatto uno switch col numero 
     * nella casella d'arrivo, e quest'ultima sarà quindi poi selezionata.
     * Ossia, ad esempio: se la sequenza è 8,5,3,2,6, la prima casella è selezionata, e si preme ">", allora 
     * la nuova sequenza è 5,8,3,2,6 e la seconda casella è selezionata.
     * Premendo su un nuovo numero lo si seleziona, e quindi si potrà spostare quello.
     * Quando a fronte degli spostamenti i numeri si trovano in ordine crescente da sinistra a destra,
     * si disabilitino tutti i pulsanti: a quel punto non si potrà fare più niente.
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono 
     * comunque al raggiungimento della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - compilazione e esecuzione dell'esercizio da linea di comando
     * 
     * Indicazioni di punteggio:
     * - correttezza della parte obbligatoria: 9 punti
     * - qualità della parte obbligatoria: 1 punto
     * - qualità della parte opzionale (strategy): 5 punti
     * - compilazione e esecuzione da linea di comando: 2 punti
     * 
     * La classe GUIExample fornita include codice che potrebbe essere utile per la soluzione.
     */

    public static void main(String[] args) throws java.io.IOException {
        // new GUIExample();
        new GUI();
    }
}
