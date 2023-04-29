/*
 File:  print64_usa_printf.s
 Descr.: esempio di stampa a video di stringa chiamando la funzione C printf
	 SFRUTTANDO la chiamata syscall ed ASSEMBLANDO E LINKANDO per 64 bit.
 Autore: Vittorio Ghini
 Data : 02/11/2019
 Architettura di riferimento: x86-64
 Dialetto assembly AT&T   (-masm=att)
 Assemblaggio e Linking 
    Modo 2, usare gcc con le stdlib: 
       Assemblare con: gcc -m64 -g -o print64_usa_printf.exe print64_usa_printf.o 
           (eventuale opzione -g per poter usare i debugger gdb o ddd)
       Linkare con:   gcc -c -m64 -g -o print64_usa_printf.o print64_usa_printf.s
 Eseguire con ./print64_usa_printf.exe 
          (o, per il debug, con gdb print64_usa_printf.exe  o ddd print64_usa_printf.exe 
*/
.data
.align 16
miastringa:	.string "MANNAG\n" # stringa da stampare compresa di a capo finale
FORMAT:		.string "ciao64 %i\n"
var_i:		.byte 0
.text
.globl main
main:
 nop
/* qui sotto attivo due volte, mediante l'istruzione syscall,
*  i servizi del sistema operativo (nel nostro caso Linux).
*  Il servizio richiamato e' identificato dal valore che 
*  viene inserito nel registro rax prima di invocare l'istruzione syscall.
*  La system call con numero 1 invoca la write.
*  La system call con numero 60 determina l'uscita dal programma
*  e il ritorno al sistema operativo.
*/
/* stampo stringa e vado a capo */
 mov $1, %rax		/* servizio da ottenere: write, stampa, in rax , ora è 1*/
 mov $1, %rdi		/* primo arg, sottoservizio da ottenere, in rdi non in rbx */
 mov $miastringa, %rsi	/* secondo arg, indirizzo inizio stringa, in rsi non in ecx */
 mov $7, %rdx		/* terzo arg, lunghezza stringa da stampare, in rdx */
 syscall
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
 xor %edi, %edi
 mov $3, %edi		/* metto exit status nel byte meno significativo del reg 'edi' */
 mov $60, %rax		/* servizio da ottenere: terminazione processo */
 syscall		/* ordine di eseguire system call */

