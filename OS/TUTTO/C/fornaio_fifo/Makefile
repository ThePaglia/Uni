CFLAGSCONSTRERROR=-ansi -Wpedantic -Wall -D_REENTRANT -D_THREAD_SAFE -D_POSIX_C_SOURCE=200112L
CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT -D_THREAD_SAFE 

LIBRARIES=-lpthread
DBGPTHREADFUNCTION_SOURCE_DIR=./

all: 	 algoritmo_fornaio_per_coda_FIFO.exe

algoritmo_fornaio_per_coda_FIFO.exe:  algoritmo_fornaio_per_coda_FIFO.o DBGpthread.o 
	gcc -o algoritmo_fornaio_per_coda_FIFO.exe algoritmo_fornaio_per_coda_FIFO.o DBGpthread.o ${LIBRARIES}

algoritmo_fornaio_per_coda_FIFO.o: algoritmo_fornaio_per_coda_FIFO.c ${DBGPTHREADFUNCTION_SOURCE_DIR}DBGpthread.h ${DBGPTHREADFUNCTION_SOURCE_DIR}printerror.h
	gcc -c ${CFLAGS} -I${DBGPTHREADFUNCTION_SOURCE_DIR} algoritmo_fornaio_per_coda_FIFO.c

DBGpthread.o: ${DBGPTHREADFUNCTION_SOURCE_DIR}DBGpthread.c ${DBGPTHREADFUNCTION_SOURCE_DIR}printerror.h
	gcc ${CFLAGSCONSTRERROR} -c ${DBGPTHREADFUNCTION_SOURCE_DIR}DBGpthread.c -I${DBGPTHREADFUNCTION_SOURCE_DIR}


.PHONY:	clean

clean:
	-rm -f DBGpthread.o  algoritmo_fornaio_per_coda_FIFO.o  algoritmo_fornaio_per_coda_FIFO.exe


