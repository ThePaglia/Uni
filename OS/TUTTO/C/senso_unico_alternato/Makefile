CFLAGSCONSTRERROR=-ansi -Wpedantic -Wall -D_REENTRANT -D_THREAD_SAFE -D_POSIX_C_SOURCE=200112L
CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT -D_THREAD_SAFE 
LIBRARIES=-lpthread
DBGPTHREADFUNCTION_SOURCE_DIR=./

all: 	 es35_masochistiasanzaccaria.exe

es35_masochistiasanzaccaria.exe:  es35_masochistiasanzaccaria.o DBGpthread.o 
	gcc ${CFLAGS} -o es35_masochistiasanzaccaria.exe es35_masochistiasanzaccaria.o DBGpthread.o ${LIBRARIES}

es35_masochistiasanzaccaria.o: es35_masochistiasanzaccaria.c ${DBGPTHREADFUNCTION_SOURCE_DIR}DBGpthread.h ${DBGPTHREADFUNCTION_SOURCE_DIR}printerror.h
	gcc -c ${CFLAGS} -I${DBGPTHREADFUNCTION_SOURCE_DIR} es35_masochistiasanzaccaria.c 

DBGpthread.o: ${DBGPTHREADFUNCTION_SOURCE_DIR}DBGpthread.c ${DBGPTHREADFUNCTION_SOURCE_DIR}printerror.h
	gcc ${CFLAGSCONSTRERROR} -c ${DBGPTHREADFUNCTION_SOURCE_DIR}DBGpthread.c -I${DBGPTHREADFUNCTION_SOURCE_DIR} 


.PHONY:	clean

clean:
	-rm -f es35_masochistiasanzaccaria.o DBGpthread.o es35_masochistiasanzaccaria.exe


