CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT -D_THREAD_SAFE -D_POSIX_C_SOURCE=200112L

LIBRARIES=-lpthread

all: 	sincroCircolareConTurno.exe

sincroCircolareConTurno.exe: sincroCircolareConTurno.o DBGpthread.o
	gcc -o sincroCircolareConTurno.exe sincroCircolareConTurno.o DBGpthread.o ${LIBRARIES}

sincroCircolareConTurno.o: sincroCircolareConTurno.c DBGpthread.h printerror.h
	gcc -c ${CFLAGS} sincroCircolareConTurno.c 

DBGpthread.o: DBGpthread.c printerror.h
	gcc -c ${CFLAGS} DBGpthread.c

.PHONY:	clean

clean:
	-rm -f sincroCircolareConTurno.o DBGpthread.o sincroCircolareConTurno.exe


