CFLAGSCONSTRERROR=-ansi -Wpedantic -Wall -D_REENTRANT -D_THREAD_SAFE -D_POSIX_C_SOURCE=200112L
CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT -D_THREAD_SAFE 

LIBRARIES=-lrt -lpthread
DBGPTHREADFUNCTION_SOURCE_DIR=../

all: 	 notifica_semplice.exe

notifica_semplice.exe:  notifica_semplice.o DBGpthread.o 
	gcc ${CFLAGS} -o notifica_semplice.exe notifica_semplice.o DBGpthread.o ${LIBRARIES}

notifica_semplice.o: notifica_semplice.c ${DBGPTHREADFUNCTION_SOURCE_DIR}DBGpthread.h ${DBGPTHREADFUNCTION_SOURCE_DIR}printerror.h
	gcc -c ${CFLAGS} -I${DBGPTHREADFUNCTION_SOURCE_DIR} notifica_semplice.c 

DBGpthread.o: ${DBGPTHREADFUNCTION_SOURCE_DIR}DBGpthread.c ${DBGPTHREADFUNCTION_SOURCE_DIR}printerror.h
	gcc ${CFLAGSCONSTRERROR} -c ${DBGPTHREADFUNCTION_SOURCE_DIR}DBGpthread.c -I${DBGPTHREADFUNCTION_SOURCE_DIR}


.PHONY:	clean

clean:
	-rm -f notifica_semplice.o DBGpthread.o notifica_semplice.exe


