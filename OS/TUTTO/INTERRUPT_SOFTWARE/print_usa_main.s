/*
 Descr.: esempio di stampa a video di stringa compilando e linkando con gcc
 Autore: Vittorio Ghini
 Data : 31/10/2019
 Architettura di riferimento: x86
 Dialetto assembly AT&T   (-masm=att)
 Assemblaggio e Linking 
    Modo 2, usare gcc con le stdlib: 
       Assemblare con: gcc -c -g -o print_usa_main.o print_usa_main.s
            (eventuale opzione -g per poter usare gdb o ddd)
       Linkare con:    igcc -g -o print_usa_main.exe print_usa_main.o

 Eseguire con ./print_usa_main.exe 
       (o, per il debug, con gdb print_usa_main.exe o ddd print_usa_main.exe)
*/
.data
miastringa: .string "MANNAG\n" # stringa da stampare compresa di a capo
.text
.globl main
main:
 nop
/* qui sotto attivo due volte, mediante l'istruzione int,
*  l'interruzione software identificata con il valore 8016 con la quale, 
*  in generale, si richiamano routine o servizi del sistema
*  operativo (nel nostro caso Linux).
*  Il servizio richiamato e' identificato dal valore che 
*  viene inserito nel registro eax prima di attivare
*  la interruzione. il valore 1 corrisponde alla routine 
*  di uscita dal programma e ritorno al sistema
*  operativo.
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

