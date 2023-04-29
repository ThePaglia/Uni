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
# L'opzione -Wpedantic e' uguale a   -pedantic
#
#
# Infine l'opzione -Wall chiede al compilatore
# di notificare con dei warning la presenza di
# istruzioni potenzialmente "pericolose"
# come ad esempio la presenza di chiamate a fuunzioni
# che non sono state dichiarate
#     "warning: implicit declaration of function"
#
#
# Per eccesso di sicurezza si puo' utilizzare il flag -Werror
# che ordina al compilatore di trattare qualunque warning
# come fosse un errore, interrompendo la compilazione.
# Serve per auto-costringersi ad eliminare i warning.
#


CFLAGS=-ansi -Wpedantic -Wall -Werror
# CFLAGS=-ansi -Wpedantic


all:	main.exe
	

main.exe:	main.o funzioni.o funzioni2.o
		gcc -o main.exe  main.o funzioni.o funzioni2.o -lm

main.o:		main.c
		gcc -c ${CFLAGS} main.c

funzioni.o:	funzioni.c
		gcc -c ${CFLAGS} funzioni.c

funzioni2.o:	funzioni2.c
		gcc -c ${CFLAGS} funzioni2.c

.PHONY:         clean

clean:		
		rm -f main.exe main.o funzioni.o funzioni2.o  *~  core 

