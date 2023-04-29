CFLAGSCONSTRERROR=-ansi -Wpedantic -Wall -D_REENTRANT -D_THREAD_SAFE -D_POSIX_C_SOURCE=200112L
CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT -D_THREAD_SAFE 

LIBRARIES=-lpthread

all: 	 mucche.exe

mucche.exe:  mucche.o DBGpthread.o 
	gcc -o mucche.exe mucche.o DBGpthread.o ${LIBRARIES}

mucche.o: mucche.c DBGpthread.h printerror.h
	gcc -c ${CFLAGS} mucche.c

DBGpthread.o: DBGpthread.c printerror.h
	gcc ${CFLAGSCONSTRERROR} -c DBGpthread.c 


.PHONY:	clean

clean:
	-rm -f mucche.o DBGpthread.o mucche.exe


