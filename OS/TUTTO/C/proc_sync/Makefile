CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT -D_THREAD_SAFE -D_POSIX_C_SOURCE=200112L
DBGMODULEPATH=../../PTHREAD/NPROD_MCONS/
LIBRARIES=-lrt -lpthread

all: 	acrossProcessBoundary.exe

acrossProcessBoundary.exe: acrossProcessBoundary.o DBGpthread.o
	gcc -o acrossProcessBoundary.exe acrossProcessBoundary.o DBGpthread.o ${LIBRARIES}

acrossProcessBoundary.o: acrossProcessBoundary.c ${DBGMODULEPATH}/DBGpthread.h ${DBGMODULEPATH}/printerror.h
	gcc -c ${CFLAGS} -I${DBGMODULEPATH} acrossProcessBoundary.c

DBGpthread.o: ${DBGMODULEPATH}/DBGpthread.c ${DBGMODULEPATH}/printerror.h
	gcc -c ${DBGMODULEPATH}/DBGpthread.c 


.PHONY:	clean

clean:
	-rm -f acrossProcessBoundary.o DBGpthread.o acrossProcessBoundary.exe


