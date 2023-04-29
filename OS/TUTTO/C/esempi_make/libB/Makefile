CFLAGS=-ansi -Wpedantic -Wall
LFLAGS=

all:	libB.so

# ATTENZIONE, i flag -Wl vanno messi dopo i -o e dopo i moduli
libB.so:	B.o
	gcc -shared ${LFLAGS} -fPIC \
	-o libB.so B.o \
	-Wl,-soname,libB.so 


B.o:	B.c
	gcc -c -fPIC ${CFLAGS}  B.c 


.PHONY:         clean

clean:	
		-rm -f *.o *.so *~ 

