CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT -D_THREAD_SAFE -D_POSIX_C_SOURCE=200112L
LIBRARIES=-lpthread

all: 	 es8_consA+B.exe

es8_consA+B.exe:  es8_consA+B.o DBGpthread.o 
	gcc ${CFLAGS} -o es8_consA+B.exe es8_consA+B.o DBGpthread.o ${LIBRARIES}

es8_consA+B.o: es8_consA+B.c DBGpthread.h printerror.h
	gcc -c ${CFLAGS} es8_consA+B.c

DBGpthread.o: DBGpthread.c printerror.h
	gcc ${CFLAGS} -c DBGpthread.c


.PHONY:	clean

clean:
	-rm -f DBGpthread.o  es8_consA+B.o  es8_consA+B.exe


