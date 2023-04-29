# Makefile per sync.c
# a causa della presenza di strerror_r in printerror.h
# occorre compilare col flag -D_POSIX_C_SOURCE=200112L

CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT -D_POSIX_C_SOURCE=200112L -D_DEFAULT_SOURCE
LFLAGS=
LIBRARIES=-lpthread

all: 	sync.exe

sync.exe: sync.o DBGpthread.o
	gcc ${LFLAGS} -o sync.exe sync.o DBGpthread.o ${LIBRARIES}

sync.o: sync.c printerror.h
	gcc ${CFLAGS} -c sync.c 

DBGpthread.o: DBGpthread.c printerror.h
	gcc ${CFLAGS} -c DBGpthread.c 

.PHONY:	clean

clean:
	rm -f sync.o sync.exe

