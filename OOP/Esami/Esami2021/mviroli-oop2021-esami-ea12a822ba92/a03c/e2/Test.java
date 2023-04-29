package a03c.e2;

public class Test {

	 /*
     * Scopo di questo esercizio è realizzare una GUI con l'aspetto iniziale mostrato nell'immagine fig1.png, fornita, 
     * che realizza poi la possibilità di spostare i simboli "*" verso l'alto, rimbalzare una volta negli ostacoli "O"
     * distruggendoli e tornando verso il basso, rimbalzare di nuovo a terra tornando verso l'alto, e infine raggiungere 
     * la parte alta, come segue:
     * 1 - all'inizio, come da figura, abbiamo per ogni colonna una "*" in ultima riga e un "O" non nelle penultime due righe
     * 2 - l'utente agisce clickando una cella qualunque della griglia (non importa quale)
     * 3 - ad ogni clik ogni "*" si sposta di una casella (o in alto o in basso), ognuno separatamente con la seguente logica 
     * 3.1 - inizialmente una "*" va verso l'alto
     * 3.2 - appena si scontrerebbe con una "O" inverte direzione verso il basso eliminando la "O"
     * 3.3 - tornando in basso quando arriva sul fondo reinverte la direzione verso l'alto
     * 3.4 - non essendoci più la O ora arriverà passo passo in cima
     * 4 - appena la prima "*" uscirebbe in alto dalla griglia si chiuda l'applicazione 
     *
     * Esempio di figura (una figura per click):
     * - fig1, situazione iniziale
     * - fig2, tutte le * si sono alzate di 1
     * - fig3, la * della sesta colonna è tornata giù eliminando la sua O, le altre si sono alzate
     * - fig4, la * della sesta colonna torna a salire, quella della terza e penultima colonna incontrano la loro O
     * - fig5, la * procede verso l'alto, e quando più avanti uscirà dalla parte alta l'applicazione si chiuderà
     * 
     * Ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
     * della totalità del punteggio, SONO CONSIDERATI OPZIONALI I SEGUENTI PUNTI:
     * - scorporamento via delegazione di tutti gli aspetti che non sono di view in una interfaccia+classe esterna
     * - controllo della logica finale di completamento del gioco
     * - gestione di una * per colonna (ossia va bene anche se si gestisce la * in una sola colonna, generata però a caso)
     *  
     * La classe GUI fornita, da modificare, include codice che potrebbe essere utile per la soluzione.
     * 
     * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 9 punti
	 * - qualità della parte opzionale: 5 punti
	 * - correttezza della parte opzionale: 3 punti
     */


    public static void main(String[] args) throws java.io.IOException {
        // new GUIExample();
        new GUI(10); 
    }
}
