CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT -D_THREAD_SAFE -D_POSIX_C_SOURCE=200112L

LIBRARIES=-lpthread
DBGPTHREADFUNCTION_SOURCE_DIR=../

all: 	 funiviasemplice.exe

funiviasemplice.exe:  funiviasemplice.o DBGpthread.o 
			gcc ${CFLAGS} -o funiviasemplice.exe funiviasemplice.o DBGpthread.o ${LIBRARIES}

funiviasemplice.o: funiviasemplice.c ${DBGPTHREADFUNCTION_SOURCE_DIR}DBGpthread.h ${DBGPTHREADFUNCTION_SOURCE_DIR}printerror.h
			gcc -c ${CFLAGS} -I${DBGPTHREADFUNCTION_SOURCE_DIR} funiviasemplice.c

DBGpthread.o: ${DBGPTHREADFUNCTION_SOURCE_DIR}DBGpthread.c ${DBGPTHREADFUNCTION_SOURCE_DIR}printerror.h
			gcc ${CFLAGS} -c ${DBGPTHREADFUNCTION_SOURCE_DIR}DBGpthread.c -I${DBGPTHREADFUNCTION_SOURCE_DIR}


.PHONY:	clean

clean:
	-rm -f funiviasemplice.o DBGpthread.o funiviasemplice.exe


