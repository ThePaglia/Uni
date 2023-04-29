package a01a.e2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig.png, fornita, 
     * che contiene una griglia quadrata di pulsanti (5x5), in cui sono presenti un pedone indicato con "*" 
     * e un cavallo indicato con "K", tutti e 2 in posizione iniziale random.
     * 
     * Premendo un pulsante si intende spostare il cavallo in quella posizione, e la cosa funzionerà secondo la
     * regola degli scacchi, dove i cavalli si spostano solo con movimento unico di 2 caselle in una direzione 
     * e 1 in quella perpendicolare.
     * Considerando la figura, dove il cavallo sta in posizione 0-based (4,3), ossia quarta colonna e terza riga,
     * ad esempio potrebbe muoversi solo in 3 caselle, ossia (3,1) mangiando il pedone, (2,2) e (2,4). 
     * Clickando in una posizione valida il cavallo viene spostato, altrimenti non deve succedere nulla.
     * Quando il cavallo va sopra il pedone, si chiuda l'applicazione.
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
