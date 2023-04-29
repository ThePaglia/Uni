# Makefile per CondVar* 
# a causa della presenza di strerror_r in printerror.h
# occorre compilare definendo il simbolo -D_POSIX_C_SOURCE=200112L
# ed e' bene compilare con uno dei due simboli  _THREAD_SAFE o _REENTRANT
# I due simboli sono equivalenti, per ricordare che esistono entrambi,
# nell'esempio li definisco entrambi, ma ne basterebbe uno solo.

CFLAGS=-ansi -Wpedantic -Wall -D_THREAD_SAFE -D_REENTRANT -D_POSIX_C_SOURCE=200112L
LFLAGS=
LIBRARIES=-lpthread

all: 	CondVarBroadcast.exe CondVarSignal.exe

CondVarBroadcast.exe: CondVarBroadcast.o
	gcc ${LFLAGS} -o CondVarBroadcast.exe CondVarBroadcast.o ${LIBRARIES}

CondVarBroadcast.o: CondVarBroadcast.c printerror.h
	gcc ${CFLAGS} -c CondVarBroadcast.c 

CondVarSignal.exe: CondVarSignal.o
	gcc ${LFLAGS} -o CondVarSignal.exe CondVarSignal.o ${LIBRARIES}

CondVarSignal.o: CondVarSignal.c printerror.h
	gcc ${CFLAGS} -c CondVarSignal.c 

.PHONY:	clean

clean:
	rm -f CondVarBroadcast.o CondVarBroadcast.exe CondVarSignal.o CondVarSignal.exe

