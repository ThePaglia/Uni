CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT -D_THREAD_SAFE -D_POSIX_C_SOURCE=200112L

LIBRARIES=-lpthread

all: 	figliarecomeconigli.exe

figliarecomeconigli.exe: figliarecomeconigli.o DBGpthread.o
	gcc ${CFLAGS} -o figliarecomeconigli.exe figliarecomeconigli.o DBGpthread.o ${LIBRARIES}

figliarecomeconigli.o: figliarecomeconigli.c DBGpthread.h printerror.h
	gcc -c ${CFLAGS} figliarecomeconigli.c

DBGpthread.o: DBGpthread.c printerror.h
	gcc ${CFLAGS} -c DBGpthread.c

.PHONY:	clean

clean:
	-rm -f figliarecomeconigli.o DBGpthread.o figliarecomeconigli.exe


