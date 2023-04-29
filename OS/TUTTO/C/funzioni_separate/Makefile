# NOTA SULLE OPZIONI DI COMPILAZIONE
# l'opzione -ansi equivale all'opzione -std=c90.
# L'opzione -ansi definisce la compilazione secondo lo standard C90
# e l'aggiunta dell'opzione -Wpedantic avvisa in caso
# di istruzioni che, secondo lo standard C90,
# rendono il codice meno leggibile,
# ad esempio se mischio dichiarazioni e codice eseguibile
#     "ISO C90 forbids mixed declarations and code".
# Avrei potuto anche usare analogamente -std=c90 -Wpedantic
#
#
# Infine l'opzione -Wall chiede al compilatore
# di notificare con dei warning la presenza di
# istruzioni potenzialmente "pericolose"
# come ad esempio la presenza di chiamate a fuunzioni
# che non sono state dichiarate
#     "warning: implicit declaration of function"

# NOTARE PERO' CHE ERRONEAMENTE NON HO MESSO IL  -Wall
# CIO' IMPEDISCE DI VEDERE DEI WARNING IMPORTANTI


CFLAGS=-ansi -Wpedantic -Wall


all:	mainConErrore.exe
	

mainConErrore.exe:	mainConErrore.o funzioni.o
		gcc -o mainConErrore.exe  mainConErrore.o funzioni.o -lm

mainConErrore.o:	mainConErrore.c
		gcc -c ${CFLAGS} mainConErrore.c

funzioni.o:	funzioni.c
		gcc -c ${CFLAGS} funzioni.c

.PHONY:         clean

clean:		
		rm -f mainConErrore.exe mainConErrore.o funzioni.o  *~  core 

