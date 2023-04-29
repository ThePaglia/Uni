CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT -D_THREAD_SAFE -D_POSIX_C_SOURCE=200112L
DBGMODULEPATH=../../PTHREAD/NPROD_MCONS/
LIBRARIES=-lrt

all: 	dezombizza_sighandler.exe

dezombizza_sighandler.exe: dezombizza_sighandler.o 
	gcc -o dezombizza_sighandler.exe dezombizza_sighandler.o 

dezombizza_sighandler.o: dezombizza_sighandler.c ${DBGMODULEPATH}/printerror.h
	gcc -c ${CFLAGS} -I${DBGMODULEPATH} dezombizza_sighandler.c

.PHONY:	clean

clean:
	-rm -f dezombizza_sighandler.o dezombizza_sighandler.exe


