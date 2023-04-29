CFLAGSCONSTRERROR=-ansi -Wpedantic -Wall -D_REENTRANT -D_THREAD_SAFE -D_POSIX_C_SOURCE=200112L
CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT -D_THREAD_SAFE 

LIBRARIES=-lpthread
DBGPTHREADFUNCTION_SOURCE_DIR=../

all: 	 megapizze_complicato.exe

megapizze_complicato.exe:  megapizze_complicato.o DBGpthread.o 
	gcc ${CFLAGS} -o megapizze_complicato.exe megapizze_complicato.o DBGpthread.o ${LIBRARIES}

megapizze_complicato.o: megapizze_complicato.c ${DBGPTHREADFUNCTION_SOURCE_DIR}DBGpthread.h ${DBGPTHREADFUNCTION_SOURCE_DIR}printerror.h
	gcc -c ${CFLAGS} -I${DBGPTHREADFUNCTION_SOURCE_DIR} megapizze_complicato.c

DBGpthread.o: ${DBGPTHREADFUNCTION_SOURCE_DIR}DBGpthread.c ${DBGPTHREADFUNCTION_SOURCE_DIR}printerror.h
	gcc ${CFLAGSCONSTRERROR} -c ${DBGPTHREADFUNCTION_SOURCE_DIR}DBGpthread.c -I${DBGPTHREADFUNCTION_SOURCE_DIR}


.PHONY:	clean

clean:
	-rm -f megapizze_complicato.o DBGpthread.o megapizze_complicato.exe


