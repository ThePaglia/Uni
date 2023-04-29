package a06.e2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
     * che realizza la possibilità di selezionare via via un certo numero di caselle non adiacenti ("o"),
     * e quindi attraverso ripetuta pressione del pulsante "ADVANCE" far attivare ("*") in ordine
     * le loro celle immediatamente sopra e sotto. Nello specifico:
     * 0 - alla partenza tutte le celle della griglia sono vuote
     * 1 - l'utente seleziona clickando un certo numero di caselle (ognuna riporterà un "o"), che non 
     * devono stare nel bordo e non devono essere tra loro adiacenti (se lo fa, il click viene ignorato)
     * 2 - alla prima pressione di "ADVANCE" tutti i pulsanti in griglia si disattivano
     * 3 - ad ogni pressione del pulsante "ADVANCE" (anche alla prima) appare un "*" secondo questo ordine: 
     * -- cella sopra al primo "o" che fu selezionato, 
     * -- cella sotto al primo "o" che fu selezionato, 
     * -- cella sopra al secondo "o" che fu selezionato,
     * -- cella sotto al secondo "o" che fu selezionato,
     * -- e così via... 
     * 4 - ultimate tutte le attivazioni di "*", una ulteriore pressione di "ADVANCE" causa la chiusura dell'applicazione.
     *
     * La figura mostra ad esempio cosa succede dopo aver selezionato 4 caselle e quindi aver premuto 8 volte
     * il pulsante ADVANCE. Premendo una ulteriore volta l'applicazione si chiude.
     * 
     * SONO CONSIDERATI OPZIONALI I SEGUENTI ASPETTI, anche se concorrono comunque al raggiungimento 
     * della totalità del punteggio:
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
        new GUI(10); 
    }
}
