CFLAGS=-ansi -Wpedantic -Wall -D_THREAD_SAFE -D_REENTRANT -D_POSIX_C_SOURCE=200112L
LIBRARIES=-lpthread

all: es0000_strutture.exe

es0000_strutture.exe: es0000_strutture.o
	gcc -o es0000_strutture.exe es0000_strutture.o ${LIBRARIES}

es0000_strutture.o: es0000_strutture.c
	gcc -c ${CFLAGS} es0000_strutture.c

.PHONY:	clean

clean:
	rm -f es0000_strutture.exe es0000_strutture.o

