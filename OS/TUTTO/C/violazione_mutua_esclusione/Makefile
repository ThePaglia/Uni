# Makefile
# -D_REENTRANT per funzioni thread safe
# -D_DEFAULT_SOURCE per random, prima si usava _BSD_SOURCE
# -D_POSIX_C_SOURCE=199309L per nanosleep

CFLAGS=-ansi -Wpedantic -Wall -D_REENTRANT -D_DEFAULT_SOURCE -D_POSIX_C_SOURCE=199309L 
LFLAGS=
LIBRARIES=-lpthread

all: verifica_assegnamenti_contemporanei_long_double.exe


verifica_assegnamenti_contemporanei_long_double.exe: verifica_assegnamenti_contemporanei_long_double.o
	gcc ${LFLAGS} -o verifica_assegnamenti_contemporanei_long_double.exe verifica_assegnamenti_contemporanei_long_double.o ${LIBRARIES}

verifica_assegnamenti_contemporanei_long_double.o: verifica_assegnamenti_contemporanei_long_double.c
	gcc -c ${CFLAGS} verifica_assegnamenti_contemporanei_long_double.c

.PHONY:	clean

clean:
	rm -f verifica_assegnamenti_contemporanei_long_double.exe verifica_assegnamenti_contemporanei_long_double.o 
