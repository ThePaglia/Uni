# MakefileBanali

CFLAGS=-ansi -Wpedantic -Wall -D_THREAD_SAFE -D_REENTRANT -D_POSIX_C_SOURCE=200112L
LFLAGS=-lpthread

all: es00000_facile.exe

es00000_facile.exe: es00000_facile.o
	gcc ${CFLAGS} -o es00000_facile.exe es00000_facile.o ${LFLAGS}

es00000_facile.o: es00000_facile.c
	gcc -c ${CFLAGS} es00000_facile.c

.PHONY:	clean

clean:
	rm -f es00000_facile.exe es00000_facile.o

