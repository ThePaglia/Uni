# Makefile per es3_fachiri.c
# a causa della presenza di pthread
# occorre compilare col flag -D_REENTRANT
# oppure con  -D_THREAD_SAFE 

CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT  -D_THREAD_SAFE -D_POSIX_C_SOURCE=200112L
LIBRARIES=-lpthread

all: 	es3_fachiri.exe 

es3_fachiri.exe: es3_fachiri.o
	gcc -o es3_fachiri.exe es3_fachiri.o ${LIBRARIES}

es3_fachiri.o: es3_fachiri.c
	gcc ${CFLAGS} -c es3_fachiri.c


.PHONY:	clean

clean:
	rm -f es3_fachiri.o es3_fachiri.exe 

