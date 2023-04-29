#include <stdlib.h>
#include <unistd.h>   /* per sleep */
#include <stdio.h>

extern double CosenoPerA( double x );
extern int A;

extern double CosenoPerA2( double x );


int main(void)
{
	double ris;


	/* configuro la variabile del modulo funzioni.c */
	A=30;

	ris = CosenoPerA( 0.1 ); 
	printf( "ris = %f \n", ris );

	ris = CosenoPerA2( 0.2 ); 
	printf( "ris2 = %f \n", ris );

	printf( "A = %i \n", A );

	return(0);
}


