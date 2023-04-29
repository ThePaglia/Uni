CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT -D_THREAD_SAFE -D_POSIX_C_SOURCE=200112L

LIBRARIES=-lpthread

all: 	strumenti_complicato.exe

strumenti_complicato.exe: strumenti_complicato.o DBGpthread.o
	gcc ${CFLAGS} -o strumenti_complicato.exe strumenti_complicato.o DBGpthread.o ${LIBRARIES}

strumenti_complicato.o: strumenti_complicato.c DBGpthread.h printerror.h
	gcc -c ${CFLAGS} strumenti_complicato.c

DBGpthread.o: DBGpthread.c printerror.h
	gcc ${CFLAGS} -c DBGpthread.c

.PHONY:	clean

clean:
	-rm -f strumenti_complicato.o DBGpthread.o strumenti_complicato.exe


