/* macro_chiamata_in_if.c 
	CAPIRE PERCHE' PROVOCA ERRORE IN COMPILAZIONE
*/

#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>

/* NB: il corpo della macro finisce alla fine della riga */

#define SALUTA(X,Y)      printf(X); printf(Y)

int main(void) {

	if(1)
		SALUTA( "ciao " , "vaffa " );
	else
		printf( "non saluta\n" );

	printf( "\nfine\n" );
	return(0);
}

