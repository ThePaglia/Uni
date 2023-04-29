#include <stdlib.h>
#include <unistd.h> 
#include <stdio.h>

#include <math.h>

/* deve essere visibile e usata anche fuori da questo modulo */
int A=0;

double CosenoPerA( double x )
{
	double risultato;

	risultato = A*cos( x ); 
	printf( "CosenoPerA calcola %f \n", risultato );

	return(risultato);
}


