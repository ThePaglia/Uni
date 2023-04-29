INSTALLDIR_1=../libA
INSTALLDIR_2=../libB


CFLAGS=-ansi -Wpedantic -Wall
LFLAGS=

all:	main.exe


main.exe:	main.o ${INSTALLDIR_1}/libA.so ${INSTALLDIR_2}/libB.so
	gcc ${LFLAGS} \
		-o main.exe main.o      \
		-lA \
		-L${INSTALLDIR_1} \
		-Wl,-rpath,`pwd`/${INSTALLDIR_1} \
		-lB \
		-L${INSTALLDIR_2} \
		-Wl,-rpath,`pwd`/${INSTALLDIR_2}


main.o:	main.c ${INSTALLDIR_1}/A.h ${INSTALLDIR_2}/B.h
	gcc -c ${CFLAGS} -I${INSTALLDIR_1} -I${INSTALLDIR_2} main.c

.PHONY:         clean

clean:	
		-rm -f *.exe *.o *~ 

