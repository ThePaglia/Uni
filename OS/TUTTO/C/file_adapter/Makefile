
all: file_adapter_ex2.exe

file_adapter_ex2.exe: file_adapter_ex2.o
	gcc -o file_adapter_ex2.exe file_adapter_ex2.o -L../LIB/lib -Wl,-rpath,/home/studente/LIB/lib -lcmph
file_adapter_ex2.o: file_adapter_ex2.c
	gcc -c file_adapter_ex2.c -I/home/studente/LIB/include

.PHONY: clean

clean:
	rm -f *.o *.exe
