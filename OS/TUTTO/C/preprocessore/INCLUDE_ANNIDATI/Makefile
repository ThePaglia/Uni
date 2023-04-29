CFLAGS=-ansi

all:	main.exe

main.exe:	main.o avvicina.o distanza.o
		gcc -o main.exe main.o avvicina.o distanza.o -lm

main.o:		main.c avvicina.h distanza.h struttura.h
		gcc -c ${CFLAGS} main.c

avvicina.o:	avvicina.c struttura.h
		gcc -c ${CFLAGS} avvicina.c

distanza.o:	distanza.c struttura.h
		gcc -c ${CFLAGS} distanza.c

.PHONY:		clean

clean:	
		rm -f main.exe main.o avvicina.o distanza.o 

