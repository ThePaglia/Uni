# Makefile per mutex
# a causa della presenza di strerror_r in printerror.h
# occorre compilare definendo il simbolo -D_POSIX_C_SOURCE=200112L
# ed e' bene compilare con uno dei due simboli  _THREAD_SAFE o _REENTRANT
# I due simboli sono equivalenti, per ricordare che esistono entrambi,
# nell'esempio li definisco entrambi, ma ne basterebbe uno solo.

CFLAGS=-ansi -Wpedantic -Wall -D_THREAD_SAFE -D_REENTRANT -D_POSIX_C_SOURCE=200112L
LFLAGS=
LIBRARIES=-lpthread

all: 	mutex.exe

mutex.exe: mutex.o
	gcc ${LFLAGS} -o mutex.exe mutex.o ${LIBRARIES}

mutex.o: mutex.c printerror.h
	gcc ${CFLAGS} -c mutex.c

.PHONY:	clean

clean:
	rm -f mutex.o mutex.exe

