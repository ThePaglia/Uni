all: main.exe

main.exe: main.o dimezza.o quadrato.o
	gcc -o ../main.exe main.o dimezza.o quadrato.o -lm

main.o: main.c ../include/dimezza.h ../include/quadrato.h
	gcc -c -ansi -Wpedantic -Werror -Wall -I ../include main.c

dimezza.o: dimezza.c 
	gcc -c -ansi -Wpedantic -Werror -Wall dimezza.c

quadrato.o: quadrato.c 
	gcc -c -ansi -Wpedantic -Werror -Wall quadrato.c

.PHONY: clean

clean:
	-rm -f main.exe *.o
 
