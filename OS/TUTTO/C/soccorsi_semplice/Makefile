CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT -D_THREAD_SAFE -D_POSIX_C_SOURCE=200112L

LIBRARIES=-lpthread

all: 	soccorsi_semplice.exe

soccorsi_semplice.exe: soccorsi_semplice.o DBGpthread.o
	gcc ${CFLAGS} -o soccorsi_semplice.exe soccorsi_semplice.o DBGpthread.o ${LIBRARIES}

soccorsi_semplice.o: soccorsi_semplice.c DBGpthread.h printerror.h
	gcc -c ${CFLAGS} soccorsi_semplice.c

DBGpthread.o: DBGpthread.c printerror.h
	gcc ${CFLAGS} -c DBGpthread.c

.PHONY:	clean

clean:
	-rm -f soccorsi_semplice.o DBGpthread.o soccorsi_semplice.exe


