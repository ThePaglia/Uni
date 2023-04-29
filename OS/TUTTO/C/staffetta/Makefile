CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT -D_THREAD_SAFE -D_POSIX_C_SOURCE=200112L

LIBRARIES=-lpthread -lm


all: 	es23_staffetta.exe

es23_staffetta.exe: es23_staffetta.o
	gcc ${CFLAGS} -o es23_staffetta.exe es23_staffetta.o ${LIBRARIES}

es23_staffetta.o: es23_staffetta.c printerror.h
	gcc -c ${CFLAGS} es23_staffetta.c ${LIBRARIES}


.PHONY:	clean

clean:
	-rm -f es23_staffetta.o es23_staffetta.exe


