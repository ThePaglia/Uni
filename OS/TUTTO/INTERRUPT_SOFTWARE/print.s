/*
 Descr.: esempio di stampa a video di stringa
 Autore: Vittorio Ghini
 Data : 31/10/2019
 Architettura di riferimento: x86
 Dialetto assembly AT&T   (-masm=att)
 Assemblaggio e Linking 
    Modo 0, non usare gcc: 
       Assemblare con: as -o print.o --gstabs print.s
           (eventuale opzione --gstabs per poter usare gdb o ddd)
       Linkare con:    ld -o print.exe print.o
    Modo 1, usare gcc senza le stdlib: 
       Assemblare con: gcc -c -g -o print.o print.s
         (eventuale opzione -g per poter usare gdb o ddd)
       Linkare con:    gcc -nostdlib -o print.exe print.o

 Eseguire con ./print.exe o (per il debug) con gdb print.exe o ddd print.exe
*/
.data
miastringa: .string "MANNAG\n" # stringa da stampare compresa di a capo
.text
.globl _start
_start:
 nop
/* qui sotto attivo due volte, mediante l'istruzione int,
*  l'interruzione software identificata con il valore 8016 con la quale, 
*  in generale, si richiamano routine o servizi del sistema
*  operativo (nel nostro caso Linux).
*  Il servizio richiamato e' identificato dal valore che 
*  viene inserito nel registro eax prima di attivare
*  la interruzione.
*  Il valore 4 chiede il servizio di stampa di una stringa.
*  Il valore 1 invece corrisponde alla routine di terminazione 
*  del programma e ritorno al sistema operativo.
*/
/* stampo stringa e vado a capo */
 movl $4, %eax		/* servizio da ottenere: stampa */
 movl $1, %ebx		/* sottoservizio da ottenere */
 movl $miastringa, %ecx	/* indirizzo di inizio stringa in registro ecx */
 movl $7, %edx		/* lunghezza della stringa da stampare in registro edx */
 int $0x80		/* ordine di eseguire interrupt */
/* termino */
fine:
 movl $1, %eax		/* servizio da ottenere: terminazione processo */
 int $0x80		/* ordine di eseguire interrupt */

