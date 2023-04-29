CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT -D_THREAD_SAFE -D_POSIX_C_SOURCE=200112L
DBGMODULEPATH=./
LIBRARIES=-lrt

all: 	es21_memcondivisafiglio.exe

es21_memcondivisafiglio.exe: es21_memcondivisafiglio.o
	gcc ${CFLAGS} -o es21_memcondivisafiglio.exe es21_memcondivisafiglio.o ${LIBRARIES}

es21_memcondivisafiglio.o: es21_memcondivisafiglio.c ${DBGMODULEPATH}/printerror.h
	gcc -c ${CFLAGS} -I${DBGMODULEPATH} es21_memcondivisafiglio.c ${LIBRARIES}


.PHONY:	clean

clean:
	-rm -f es21_memcondivisafiglio.o es21_memcondivisafiglio.exe


