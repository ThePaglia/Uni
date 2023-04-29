# Makefile per es4_pthread_variabilecondivisa.c 

CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT -D_MULTI_THREADED -D_POSIX_SOURCE=1 -D_POSIX_C_SOURCE=200112L
LIBRARIES=-lpthread

all: 	es4_pthread_variabilecondivisa.exe

es4_pthread_variabilecondivisa.exe: es4_pthread_variabilecondivisa.o
	gcc -o es4_pthread_variabilecondivisa.exe ${CFLAGS} es4_pthread_variabilecondivisa.o ${LIBRARIES}

es4_pthread_variabilecondivisa.o: es4_pthread_variabilecondivisa.c
	gcc -c ${CFLAGS} es4_pthread_variabilecondivisa.c 

.PHONY:	clean

clean:
	rm -f es4_pthread_variabilecondivisa.o es4_pthread_variabilecondivisa.exe

