CFLAGSCONSTRERROR=-ansi -Wpedantic -Wall -D_REENTRANT -D_THREAD_SAFE -D_POSIX_C_SOURCE=200112L
CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT -D_THREAD_SAFE 

LIBRARIES=-lrt -lpthread
DBGPTHREADFUNCTION_SOURCE_DIR=../

all: 	 megapizze_semplice.exe

megapizze_semplice.exe:  megapizze_semplice.o DBGpthread.o 
	gcc ${CFLAGS} -o megapizze_semplice.exe megapizze_semplice.o DBGpthread.o ${LIBRARIES}

megapizze_semplice.o: megapizze_semplice.c ${DBGPTHREADFUNCTION_SOURCE_DIR}DBGpthread.h ${DBGPTHREADFUNCTION_SOURCE_DIR}printerror.h
	gcc -c ${CFLAGS} -I${DBGPTHREADFUNCTION_SOURCE_DIR} megapizze_semplice.c

DBGpthread.o: ${DBGPTHREADFUNCTION_SOURCE_DIR}DBGpthread.c ${DBGPTHREADFUNCTION_SOURCE_DIR}printerror.h
	gcc ${CFLAGSCONSTRERROR} -c ${DBGPTHREADFUNCTION_SOURCE_DIR}DBGpthread.c -I${DBGPTHREADFUNCTION_SOURCE_DIR}


.PHONY:	clean

clean:
	-rm -f megapizze_semplice.o DBGpthread.o megapizze_semplice.exe


