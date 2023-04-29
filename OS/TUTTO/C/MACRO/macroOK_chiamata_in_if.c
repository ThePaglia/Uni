/* macroOK_chiamata_in_if.c */

#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>

/* NB: il corpo della macro finirebbe alla fine della riga,
   per farla continuare alla riga successiva devo mettere
   un backslash  \ come ultimo carattere della riga.
   L'ultima riga percio' non necessita del backslash in fondo.
*/

#define SALUTA(X,Y)	do {			\
				printf(X);	\
				printf(Y);	\
			} while(0)

int main(void) {

	if(1)
		SALUTA( "ciao " , "vaffa " );
	else
		printf( "non saluta\n" );

	printf( "\nfine\n" );
	return(0);
}

