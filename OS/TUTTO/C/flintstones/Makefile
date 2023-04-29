CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT -D_THREAD_SAFE -D_POSIX_C_SOURCE=200112L

LIBRARIES=-lpthread

all: 	flintstones_semplice.exe

flintstones_semplice.exe: flintstones_semplice.o DBGpthread.o
	gcc ${CFLAGS} -o flintstones_semplice.exe flintstones_semplice.o DBGpthread.o ${LIBRARIES}

flintstones_semplice.o: flintstones_semplice.c DBGpthread.h printerror.h
	gcc -c ${CFLAGS} flintstones_semplice.c

DBGpthread.o: DBGpthread.c printerror.h
	gcc ${CFLAGS} -c DBGpthread.c

.PHONY:	clean

clean:
	-rm -f flintstones_semplice.o DBGpthread.o flintstones_semplice.exe


