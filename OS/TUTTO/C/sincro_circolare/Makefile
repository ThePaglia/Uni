CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT -D_THREAD_SAFE -D_POSIX_C_SOURCE=200112L

LIBRARIES=-lpthread

all: 	circolare1a1.exe SBAGLIATO_0_circolare1a1.exe SBAGLIATO_1_circolare1a1.exe SBAGLIATO_2_circolare1a1.exe

circolare1a1.exe: circolare1a1.o DBGpthread.o
	gcc -o circolare1a1.exe circolare1a1.o DBGpthread.o ${LIBRARIES}

circolare1a1.o: circolare1a1.c DBGpthread.h printerror.h
	gcc -c circolare1a1.c 

DBGpthread.o: DBGpthread.c printerror.h
	gcc -c DBGpthread.c 

SBAGLIATO_0_circolare1a1.exe: SBAGLIATO_0_circolare1a1.o DBGpthread.o
	gcc -o SBAGLIATO_0_circolare1a1.exe SBAGLIATO_0_circolare1a1.o DBGpthread.o ${LIBRARIES}

SBAGLIATO_0_circolare1a1.o: SBAGLIATO_0_circolare1a1.c DBGpthread.h printerror.h
	gcc -c SBAGLIATO_0_circolare1a1.c 

SBAGLIATO_1_circolare1a1.exe: SBAGLIATO_1_circolare1a1.o DBGpthread.o
	gcc -o SBAGLIATO_1_circolare1a1.exe SBAGLIATO_1_circolare1a1.o DBGpthread.o ${LIBRARIES}

SBAGLIATO_1_circolare1a1.o: SBAGLIATO_1_circolare1a1.c DBGpthread.h printerror.h
	gcc -c SBAGLIATO_1_circolare1a1.c 

SBAGLIATO_2_circolare1a1.exe: SBAGLIATO_2_circolare1a1.o DBGpthread.o
	gcc -o SBAGLIATO_2_circolare1a1.exe SBAGLIATO_2_circolare1a1.o DBGpthread.o ${LIBRARIES}

SBAGLIATO_2_circolare1a1.o: SBAGLIATO_2_circolare1a1.c DBGpthread.h printerror.h
	gcc -c SBAGLIATO_2_circolare1a1.c 

.PHONY:	clean

clean:
	-rm -f circolare1a1.o DBGpthread.o circolare1a1.exe
	-rm -f SBAGLIATO_0_circolare1a1.o DBGpthread.o SBAGLIATO_0_circolare1a1.exe
	-rm -f SBAGLIATO_1_circolare1a1.o DBGpthread.o SBAGLIATO_1_circolare1a1.exe
	-rm -f SBAGLIATO_2_circolare1a1.o DBGpthread.o SBAGLIATO_2_circolare1a1.exe


