
CFLAGS=-ansi -Wpedantic -Wall


all:	stringhe_costanti.exe  stringhe_costanti.s
	

stringhe_costanti.exe:	stringhe_costanti.o 
		gcc -o stringhe_costanti.exe stringhe_costanti.o

stringhe_costanti.o:	stringhe_costanti.c 
		gcc -c ${CFLAGS} stringhe_costanti.c

stringhe_costanti.s:	stringhe_costanti.c
		gcc -S ${CFLAGS} stringhe_costanti.c

.PHONY:         clean

clean:		
		rm -f stringhe_costanti.exe stringhe_costanti.o stringhe_costanti.s *~  core 

