CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT -D_THREAD_SAFE -D_POSIX_C_SOURCE=200112L
LIBRARIES=-lpthread

all: 	 es7_fornaio2.exe

es7_fornaio2.exe:  es7_fornaio2.o DBGpthread.o 
	gcc ${CFLAGS} -o es7_fornaio2.exe es7_fornaio2.o DBGpthread.o ${LIBRARIES}

es7_fornaio2.o: es7_fornaio2.c DBGpthread.h printerror.h
	gcc -c ${CFLAGS} es7_fornaio2.c

DBGpthread.o: DBGpthread.c printerror.h
	gcc ${CFLAGS} -c DBGpthread.c


.PHONY:	clean

clean:
	-rm -f DBGpthread.o  es7_fornaio2.o  es7_fornaio2.exe


