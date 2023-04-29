# Makefile per es3bis_Deadlock_fachiri.c
# a causa della presenza di pthread
# occorre compilare col flag -D_REENTRANT
# oppure con  -D_THREAD_SAFE 

CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT  -D_THREAD_SAFE -D_POSIX_C_SOURCE=200112L
LIBRARIES=-lpthread

all: 	es3bis_Deadlock_fachiri.exe 

es3bis_Deadlock_fachiri.exe: es3bis_Deadlock_fachiri.o
	gcc -o es3bis_Deadlock_fachiri.exe es3bis_Deadlock_fachiri.o ${LIBRARIES}

es3bis_Deadlock_fachiri.o: es3bis_Deadlock_fachiri.c
	gcc ${CFLAGS} -c es3bis_Deadlock_fachiri.c


.PHONY:	clean

clean:
	rm -f es3bis_Deadlock_fachiri.o es3bis_Deadlock_fachiri.exe 

