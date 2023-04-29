all: 	dammi_il_precedente.exe 

dammi_il_precedente.exe: dammi_il_precedente.o
	gcc -ansi -Wall -Wpedantic -Werror -o dammi_il_precedente.exe dammi_il_precedente.o -lm

dammi_il_precedente.o: dammi_il_precedente.c
	gcc -ansi -Wall -Wpedantic -Werror -c dammi_il_precedente.c

.PHONY:	clean

clean:
	rm -f dammi_il_precedente.o dammi_il_precedente.exe 
