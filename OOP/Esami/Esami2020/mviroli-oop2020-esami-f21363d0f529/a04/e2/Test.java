package a04.e2;

public class Test {

    /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
     * che realizza un mini-gioco ispirato agli scacchi (le immagini dei pezzi sono ottenute con caratteri
     * unicode speciali come mostrato nel codice d'esempio). Inizialmente:
     * - c'è un re, posizionato ovunque nella scacchiera
     * - ci sono 3 pedine nemiche, da posizionare ovunque
     * (ci si accerti che i 4 pezzi stiano in celle diverse tra loro)
     *  
     * 1 - il giocatore sposta il re di una casella alla volta, clickando sulla casella d'arrivo 
     * (spostamento di una casella, in orizzontale, verticale o diagonale) 
     * 2 - appena il re finisce su una pedina la mangia, e si trasforma in un cavallo 
     * 3 - a quel punto il giocatore sposta il cavallo di una casella alla volta, clickando sulla casella d'arrivo 
     * (la casella d'arrivo deve distare da quella di partenza due caselle in una direzione e una in quella ortogonale, 
     * proprio come negli scacchi)
     * 4 - quando il cavallo mangia una pedina, si ritrasforma in re e si ricomincia dal punto 1 
     * 
     * Il gioco termina quando non ci sono più pedine.
     * 
     * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio:
     * - scorporamento di tutti gli aspetti che non sono di view in una interfaccia+classe esterna, via Strategy
     * - qualità del codice della logica di gioca
     * - ritrasformazione del cavallo in re
     * - uso dei caratteri speciali per mostrare i pezzi della scacchiera
     *  
     * La classe GUI fornita, da modificare, include codice che potrebbe essere utile per la soluzione.
     * 
     * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 10 punti
	 * - qualità della parte opzionale: 5 punti
	 * - correttezza della parte opzionale: 2 punti
     */

    public static void main(String[] args) {
        new GUI(5); 
    }
}
