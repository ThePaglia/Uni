CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT -D_THREAD_SAFE -D_POSIX_C_SOURCE=200112L

LIBRARIES=-lrt -lpthread

all: 	flintstones_complicato.exe

flintstones_complicato.exe: flintstones_complicato.o DBGpthread.o
	gcc ${CFLAGS} -o flintstones_complicato.exe flintstones_complicato.o DBGpthread.o ${LIBRARIES}

flintstones_complicato.o: flintstones_complicato.c DBGpthread.h printerror.h
	gcc -c ${CFLAGS} flintstones_complicato.c

DBGpthread.o: DBGpthread.c printerror.h
	gcc ${CFLAGS} -c DBGpthread.c

.PHONY:	clean

clean:
	-rm -f flintstones_complicato.o DBGpthread.o flintstones_complicato.exe


