# NOTA SULLE OPZIONI DI COMPILAZIONE
# 	Ultima modifica 2019-11-01
#
# L'opzione di compilazione -fPIC significa 
# Position-Independent Code e ordina al compilatore
# di generare codice macchina che possa essere caricato
# in un punto qualsiasi dello spazio degli indirizzi.
# Il codice position-independent non potra' contenere
# dei jump ad indirizzi assoluti, ma sfruttera' solo
# jump con indirizzi relativi, inoltre, non potra'
# sfruttare alcuni trucchi osceni per ottimizzazione.
# Scopo del codice position-independent e' quello di
# poter essere collocato, a run-time, a partire da
# un indirizzo qualunque della memoria, indirizzo
# che non e' noto al momento del linking.
# Questo e' utile per il codice che implementa le
# librerie caricate dinamicamente, poiche' queste
# librerie vengono caricate a run-time.
#
#
# Occorre capire cosa e' il SONAME
#
# Ogni shared library ha:

# - un SONAME, ossia una stringa che soddisfa il pattern 
#	"lib[NAME][API-VERSION].so.[major-version]" 
#	(dove API-VERSION è solitamente vuoto);
#	Quando cambio molto le API della libreria incremento lo API-VERSION.
#	Nel nostro esempio i SONAME sono libfunzioni1shared.so e 
#	libfunzioni2shared.so perche' non voglio usare versioni. 
# - un REALNAME ossia una stringa che soddisfa il pattern 
#	"SONAME.[MINOR-NUMBER].[RELEASE-NUMBER]" ed è solitamente il nome 
#	del file che concretamente è presente nel file system.
#	Estendendo SONAME, il pattern di REALNAME è 
#	"lib[NAME][API-VERSION].so.[MAJOR-VERSION].[MINOR-VERSION].[RELEASE-NUMBER]";
#	Nel nostro esempio i REALNAME sono libfunzioni1shared.so e 
#	libfunzioni2shared.so perche' non voglio usare versioni e nemmeno
#	MAJOR e MINOR VERSION. 
# - un LINKER NAME, ossia una stringa che da usare sul linker che identifica
#	la libreria stessa.
#	Nel nostro esempio i LINKERNAME sono funzioni1shared e 
#	funzioni2shared.
# NOTARE CHE IL REALNAME E' UNA ESTENSIONE DEL SONAME.



# directory in cui installare le librerie libfunction1
INSTALLDIR_1=/home/studente/lib/lib_1
# directory in cui installare le librerie libfunction2
INSTALLDIR_2=/home/studente/lib/lib_2


# opzioni di compilazione comuni a tutti i sorgenti C
CFLAGS=-ansi -Wpedantic -Wall

TARGETS=${INSTALLDIR_2}/libfunzioni2shared.so ${INSTALLDIR_1}/libfunzioni1shared.so progr1_sharedlib.exe

# All -rpath arguments are concatenated and passed to the runtime linker,
# which uses them to locate shared objects at runtime.

# il primo target specifica le dipendenze principali
# comunemente il primo target viene indicato con la parola all
all:	${TARGETS}


${INSTALLDIR_2}/libfunzioni2shared.so:	libfunzioni2shared.so
	cp libfunzioni2shared.so ${INSTALLDIR_2}/

# ATTENZIONE, i flag -Wl vanno messi dopo i -o e dopo i moduli
libfunzioni2shared.so:	funzioni2shared.o
	gcc -shared ${CFLAGS} -fPIC \
	-o libfunzioni2shared.so funzioni2shared.o \
	-Wl,-soname,libfunzioni2shared.so 


funzioni2shared.o:	funzioni2.c
	gcc -c -fPIC ${CFLAGS} -o funzioni2shared.o  funzioni2.c 


${INSTALLDIR_1}/libfunzioni1shared.so:	libfunzioni1shared.so
	cp libfunzioni1shared.so ${INSTALLDIR_1}/


# ATTENZIONE, i flag -Wl -l e -L vanno messi dopo -o e dopo i moduli
# VERIFICARE col comando   readelf -d libfunzioni1shared.so
# che libreria 1 "(NEEDED) Shared library: [libfunzioni2shared.so] 
libfunzioni1shared.so:	${INSTALLDIR_2}/libfunzioni2shared.so funzioni1shared.o
	gcc -shared ${CFLAGS} -fPIC    \
		-o libfunzioni1shared.so funzioni1shared.o \
		-Wl,-soname,libfunzioni1shared.so \
		-lfunzioni2shared -L${INSTALLDIR_2} \
		-Wl,-rpath,${INSTALLDIR_2} 



funzioni1shared.o:	funzioni1.c funzioni2.h
	gcc -c -fPIC ${CFLAGS} -o funzioni1shared.o  funzioni1.c  


progr1.o:	progr1.c funzioni1.h
	gcc -c ${CFLAGS} progr1.c

progr1_sharedlib.exe:	progr1.o ${INSTALLDIR_1}/libfunzioni1shared.so
	gcc ${CFLAGS} \
		-o progr1_sharedlib.exe progr1.o      \
		-lfunzioni1shared \
		-L${INSTALLDIR_1} \
		-Wl,-rpath,${INSTALLDIR_1}


.PHONY:         clean distclean createDir1 createDir2 createDirs

clean:	
		-rm -f *.exe *.o *.a *.so *~ 

distclean:
		-rm -f ${INSTALLDIR_1}/libfunzioni1shared.so \
			${INSTALLDIR_2}/libfunzioni2shared.so

createDirs:	createDir1 createDir2


createDir1:
	if [ ! -e ${INSTALLDIR_1} ]; then mkdir -p ${INSTALLDIR_1}; fi

createDir2:
	if [ ! -e ${INSTALLDIR_2} ]; then mkdir -p ${INSTALLDIR_2}; fi

