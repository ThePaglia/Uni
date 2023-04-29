GCC=gcc
CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT -D_POSIX_C_SOURCE=200112L
LIBRARIES=-lrt

all: 		readFromShm.exe  writeToShm.exe


readFromShm.exe:	readFromShm.o
		${GCC} ${CFLAGS} -o readFromShm.exe readFromShm.o ${LIBRARIES}

readFromShm.o:	readFromShm.c
		${GCC} ${CFLAGS} -c readFromShm.c 


writeToShm.exe:	writeToShm.o
		${GCC} ${CFLAGS} -o writeToShm.exe writeToShm.o ${LIBRARIES}

writeToShm.o:	writeToShm.c
		${GCC} ${CFLAGS} -c writeToShm.c 


.PHONY:		clean


clean:	
		rm -f readFromShm.exe  writeToShm.exe readFromShm.o  writeToShm.o *~


