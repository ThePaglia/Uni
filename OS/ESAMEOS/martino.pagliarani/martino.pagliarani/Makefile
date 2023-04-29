CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT -D_THREAD_SAFE -D_DEFAULT_SOURCE  -D_POSIX_C_SOURCE=200112L

LIBRARIES=-lpthread

all: 	pista.exe

pista.exe: pista.o DBGpthread.o
	gcc ${CFLAGS} -o pista.exe pista.o DBGpthread.o ${LIBRARIES}

pista.o: pista.c DBGpthread.h printerror.h
	gcc -c ${CFLAGS} pista.c

DBGpthread.o: DBGpthread.c printerror.h
	gcc ${CFLAGS} -c DBGpthread.c

.PHONY:	clean

clean:
	-rm -f pista.o DBGpthread.o pista.exe


