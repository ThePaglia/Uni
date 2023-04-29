CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT -D_THREAD_SAFE -D_POSIX_C_SOURCE=200112L
DBGMODULEPATH=./
LIBRARIES=

all: 	es22_passaggioparametriafiglio.exe

es22_passaggioparametriafiglio.exe: es22_passaggioparametriafiglio.o 
	gcc ${CFLAGS} -o es22_passaggioparametriafiglio.exe es22_passaggioparametriafiglio.o ${LIBRARIES}

es22_passaggioparametriafiglio.o: es22_passaggioparametriafiglio.c ${DBGMODULEPATH}/printerror.h
	gcc -c ${CFLAGS} -I${DBGMODULEPATH} es22_passaggioparametriafiglio.c ${LIBRARIES}


.PHONY:	clean

clean:
	-rm -f es22_passaggioparametriafiglio.o es22_passaggioparametriafiglio.exe


