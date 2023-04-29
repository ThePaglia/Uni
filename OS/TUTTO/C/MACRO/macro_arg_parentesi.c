/* macro_arg_parentesi.c  */

#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>

/* NB: nella implementazione di una macro
   e' sempre meglio circondare gli argomenti
   con delle parentesi tonde, 
   nel caso gli argomenti passati siano composti.
*/

#define STAMPAPRODOTTO( X, Y )	printf("%i\n",  X * Y )

#define STAMPAPRODOTTO_USA_PARENTESI( X, Y )	printf("%i\n",  (X) * (Y) )

int main(void) {

	STAMPAPRODOTTO( 3, 2 );			 /* stampa 6, va bene */

	STAMPAPRODOTTO( 3+5, 2 );		 /* stampa 13,  ERRATO */
	STAMPAPRODOTTO_USA_PARENTESI( 3+5, 2 );  /* stampa 16, corretto */

	return(0);
}

