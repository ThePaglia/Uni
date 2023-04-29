# Makefile per gallinauovavolpe.c
# a causa della presenza di strerror_r in printerror.h
# occorre compilare col flag -D_POSIX_C_SOURCE=200112L

CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT -D_POSIX_C_SOURCE=200112L -D_DEFAULT_SOURCE
LFLAGS=
LIBRARIES=-lpthread

all: 	gallinauovavolpe.exe

gallinauovavolpe.exe: gallinauovavolpe.o
	gcc ${LFLAGS} -o gallinauovavolpe.exe gallinauovavolpe.o ${LIBRARIES}

gallinauovavolpe.o: gallinauovavolpe.c printerror.h
	gcc ${CFLAGS} -c gallinauovavolpe.c 

.PHONY:	clean

clean:
	rm -f gallinauovavolpe.o gallinauovavolpe.exe

