/* macro_in_piu_righe.c  */

#include <unistd.h>
#include <stdlib.h>		/* serve per la funzione rand */
#include <stdio.h>

/* NB: il corpo della macro finirebbe alla fine della riga,
   per farla continuare alla riga successiva devo mettere
   un backslash  \ come ultimo carattere della riga.
   L'ultima riga percio' non necessita del backslash in fondo.
*/

#define SALUTAaCASO()	srandom( time(NULL) );				\
			if(  random()%300 < 150 )			\
				printf( "ciao\n" );			\
			else						\
				/* notare che sotto non c'e' il ; */	\
				/* e non c'e' il \ in fondo */		\
				printf( "la vacca ti ha fatto \n" )

int main(void) {

	printf( "inizio\n" );

	SALUTAaCASO( );

	printf( "fine\n" );
	return(0);
}


