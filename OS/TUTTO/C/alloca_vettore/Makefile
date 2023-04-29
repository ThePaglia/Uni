all: main.exe

main.exe: main.o
	gcc -o main.exe main.o

main.o: main.c macro.h
	gcc -c -ansi -Wpedantic -Werror -Wall main.c

clean:
	-rm main.exe *.o

