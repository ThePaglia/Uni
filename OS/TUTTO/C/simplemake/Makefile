all :		main.exe

main.exe :	main.o  funzioni.o
		gcc -o main.exe main.o funzioni.o

main.o :	main.c  funzioni.h strutture.h
		gcc -c -ansi -Wpedantic -Wall main.c

funzioni.o :	funzioni.c  strutture.h
		gcc -c -ansi -Wpedantic -Wall funzioni.c

clean:		
		-rm    main.exe *.o

