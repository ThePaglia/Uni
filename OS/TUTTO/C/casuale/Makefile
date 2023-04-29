all: casuale.exe

casuale.exe: casuale.o
	gcc -ansi -Wall -Wpedantic -Werror -o casuale.exe casuale.o -lm

casuale.o: casuale.c
	gcc -ansi -Wall -Wpedantic -Werror -c casuale.c

.PHONY:	clean

clean:
	rm -f casuale.o casuale.exe
