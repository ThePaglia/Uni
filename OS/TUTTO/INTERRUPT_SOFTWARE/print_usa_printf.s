/*
 Descr.: esempio di stampa a video di stringa chiamando la funzione C printf
 Autore: Vittorio Ghini
 Data : 31/10/2019
 Architettura di riferimento: x86
 Dialetto assembly AT&T   (-masm=att)
 Assemblaggio e Linking 
    Modo 2, usare gcc con le stdlib: 
       Assemblare con: gcc -g -o print_usa_printf.exe print_usa_printf.o 
           (eventuale opzione -g per poter usare i debugger gdb o ddd)
       Linkare con:   gcc -c -g -o print_usa_printf.o print_usa_printf.s
 Eseguire con ./print_usa_printf.exe 
          (o, per il debug, con gdb print_usa_printf.exe print o ddd print_usa_printf.exe 
*/
.data
.align 16
miastringa:	.string "MANNAG\n" # stringa da stampare compresa di a capo finale
FORMAT:		.string "ciao %i\n"
var_i:		.byte 0
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
/* ora assegno valore 13 alla variabile i
   Notare la caratteristica tipica di x64: posso referenziare i dati
   anche con INDIRIZZI RELATIVI all'Instruction Pointer (registro rip).
   Cio' rende il codice Position Independent e quindi facilmente rilocabile.
*/
 movl	$13, var_i(%rip)
/* ora voglio usare la funzione C printf per stampare "ciao 13\n" */
 movl	var_i(%rip), %eax
 movl	%eax, %esi
 movl	$FORMAT, %edi
 movl	$0, %eax
 call	printf
/* ora assegno valore 17 alla variabile i */
 movl	$17, var_i(%rip)
/* termino */
fine:
 movl $1, %eax		/* servizio da ottenere: terminazione processo */
 int $0x80		/* ordine di eseguire interrupt */

