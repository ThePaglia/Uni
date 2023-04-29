file:  ProcessiThread_NProdMCons1buffer_NONefficente.c

- NProduttori e NConsumatori che si scambiano prodotti
   mediante un unico Buffer. E' la versione NON efficente
- I Produttori stanno in un processo.
- I Consumatori stanno in un processo diverso.
- I due processi non sono padre  e figlio.
- Eseguendo il programma devo passare a riga di comando
   un carattere P o C per eseguire il ruolo di Produttori
   oppure il ruolo di Consumatori.
- Uno script LANCIA.sh lancia in background  entrambi i processi,
   prima lancia il processo che crea i thread Consumatori,
   poi aspetta 2 secondi,
   poi lancia il processo che crea i thread Produttori.
- Lo script aspetta 10 secondi e poi killa entrambi i processi
