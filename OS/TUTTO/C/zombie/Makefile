CFLAGS=-ansi -Wpedantic -Wall -D_POSIX_C_SOURCE=200112L
DBGMODULEPATH=./
LIBRARIES=

all: 	es25_zombie.exe

es25_zombie.exe: es25_zombie.o 
	gcc ${CFLAGS} -o es25_zombie.exe es25_zombie.o ${LIBRARIES}

es25_zombie.o: es25_zombie.c ${DBGMODULEPATH}/printerror.h
	gcc -c ${CFLAGS} -I${DBGMODULEPATH} es25_zombie.c ${LIBRARIES}


.PHONY:	clean

clean:
	-rm -f es25_zombie.o es25_zombie.exe


