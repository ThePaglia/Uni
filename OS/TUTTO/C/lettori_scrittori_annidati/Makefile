CFLAGSCONSTRERROR=-ansi -Wpedantic -Wall -D_REENTRANT -D_THREAD_SAFE -D_POSIX_C_SOURCE=200112L
CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT -D_THREAD_SAFE 
LIBRARIES=-lpthread
DBGPTHREADFUNCTION_SOURCE_DIR=./

all: 	 es34_LettoriScrittori_SenzaMutexAnnidate.exe

es34_LettoriScrittori_SenzaMutexAnnidate.exe:  es34_LettoriScrittori_SenzaMutexAnnidate.o DBGpthread.o 
	gcc ${CFLAGS} -o es34_LettoriScrittori_SenzaMutexAnnidate.exe es34_LettoriScrittori_SenzaMutexAnnidate.o DBGpthread.o ${LIBRARIES}

es34_LettoriScrittori_SenzaMutexAnnidate.o: es34_LettoriScrittori_SenzaMutexAnnidate.c ${DBGPTHREADFUNCTION_SOURCE_DIR}DBGpthread.h ${DBGPTHREADFUNCTION_SOURCE_DIR}printerror.h
	gcc -c ${CFLAGS} -I${DBGPTHREADFUNCTION_SOURCE_DIR} es34_LettoriScrittori_SenzaMutexAnnidate.c 

DBGpthread.o: ${DBGPTHREADFUNCTION_SOURCE_DIR}DBGpthread.c ${DBGPTHREADFUNCTION_SOURCE_DIR}printerror.h
	gcc ${CFLAGSCONSTRERROR} -c ${DBGPTHREADFUNCTION_SOURCE_DIR}DBGpthread.c -I${DBGPTHREADFUNCTION_SOURCE_DIR} 


.PHONY:	clean

clean:
	-rm -f es34_LettoriScrittori_SenzaMutexAnnidate.o DBGpthread.o es34_LettoriScrittori_SenzaMutexAnnidate.exe


