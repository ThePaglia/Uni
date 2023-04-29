CFLAGS=-ansi -Wpedantic -Wall
LFLAGS=

all:	libA.so

# ATTENZIONE, i flag -Wl vanno messi dopo i -o e dopo i moduli
libA.so:	A.o
	gcc -shared ${LFLAGS} -fPIC \
	-o libA.so A.o \
	-lm \
	-Wl,-soname,libA.so 


A.o:	A.c
	gcc -c -fPIC ${CFLAGS} A.c 


.PHONY:         clean

clean:	
		-rm -f *.o *.so *~ 


