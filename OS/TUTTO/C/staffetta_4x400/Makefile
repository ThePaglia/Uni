# Makefile per mutex e CondVar* 
# a causa della presenza di strerror_r in printerror.h
# occorre compilare col flag -D_POSIX_C_SOURCE=200112L

CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT -D_POSIX_C_SOURCE=200112L
LIBRARIES=-lpthread

all: 	es31_staffetta4x400.exe

es31_staffetta4x400.exe: es31_staffetta4x400.o DBGpthread.o
	gcc ${CFLAGS} -o es31_staffetta4x400.exe es31_staffetta4x400.o DBGpthread.o ${LIBRARIES}

es31_staffetta4x400.o: es31_staffetta4x400.c printerror.h
	gcc -c ${CFLAGS} es31_staffetta4x400.c

DBGpthread.o: DBGpthread.c printerror.h
	gcc -c ${CFLAGS} DBGpthread.c

.PHONY:	clean

clean:
	rm -f es31_staffetta4x400.o DBGpthread.o es31_staffetta4x400.exe

