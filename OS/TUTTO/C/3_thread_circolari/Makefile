# Makefile per mutex e CondVar* 
# a causa della presenza di strerror_r in printerror.h
# occorre compilare col flag -D_POSIX_C_SOURCE=200112L

CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT -D_POSIX_C_SOURCE=200112L
LIBRARIES=-lpthread

all: 	es10_circolareA3_turn.exe

es10_circolareA3_turn.exe: es10_circolareA3_turn.o DBGpthread.o
	gcc ${CFLAGS} -o es10_circolareA3_turn.exe es10_circolareA3_turn.o DBGpthread.o ${LIBRARIES}

es10_circolareA3_turn.o: es10_circolareA3_turn.c printerror.h
	gcc ${CFLAGS} -c es10_circolareA3_turn.c

DBGpthread.o: DBGpthread.c printerror.h
	gcc ${CFLAGS} -c DBGpthread.c

.PHONY:	clean

clean:
	rm -f es10_circolareA3_turn.o DBGpthread.o es10_circolareA3_turn.exe

