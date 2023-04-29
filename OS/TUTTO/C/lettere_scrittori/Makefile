CFLAGSCONSTRERROR=-ansi -Wpedantic -Wall -D_REENTRANT -D_THREAD_SAFE -D_POSIX_C_SOURCE=200112L
CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT -D_THREAD_SAFE 

LIBRARIES=-lpthread
DBGPTHREADFUNCTION_SOURCE_DIR=../NPROD_MCONS/

all: 	 NLettoriMScrittori.exe

NLettoriMScrittori.exe:  NLettoriMScrittori.o DBGpthread.o 
	gcc -o NLettoriMScrittori.exe NLettoriMScrittori.o DBGpthread.o ${LIBRARIES}

NLettoriMScrittori.o: NLettoriMScrittori.c ${DBGPTHREADFUNCTION_SOURCE_DIR}DBGpthread.h ${DBGPTHREADFUNCTION_SOURCE_DIR}printerror.h
	gcc -c ${CFLAGS} -I${DBGPTHREADFUNCTION_SOURCE_DIR} NLettoriMScrittori.c 

DBGpthread.o: ${DBGPTHREADFUNCTION_SOURCE_DIR}DBGpthread.c ${DBGPTHREADFUNCTION_SOURCE_DIR}printerror.h
	gcc ${CFLAGSCONSTRERROR} -c ${DBGPTHREADFUNCTION_SOURCE_DIR}DBGpthread.c -I${DBGPTHREADFUNCTION_SOURCE_DIR} 


.PHONY:	clean

clean:
	-rm -f NLettoriMScrittori.o DBGpthread.o NLettoriMScrittori.exe


