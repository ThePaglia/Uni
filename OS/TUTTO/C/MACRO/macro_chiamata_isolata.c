/* macro_chiamata_isolata.c  */

#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>

/* NB: il corpo della macro finisce alla fine della riga */

#define SALUTA(X,Y)    printf(X); printf(Y)

int main(void) {

	SALUTA( "ciao " , "vaffa " );

	printf( "\nfine\n" );
	return(0);
}

