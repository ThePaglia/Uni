	/* funzioni2.c */

#include <stdlib.h>
#include <unistd.h> 
#include <stdio.h>

#include <math.h>

/* non deve essere visibile e usata fuori da questo modulo */
static int A=2;

double CosenoPerA2( double x )
{
	double risultato;

	risultato = A*cos( x ); 
	printf( "CosenoPerA2 calcola %f \n", risultato );

	return(risultato);
}


