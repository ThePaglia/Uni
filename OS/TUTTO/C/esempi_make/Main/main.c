/* Main.c */

#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>

#include "A.h"
#include "B.h"

int main (int argc, char* argv[])
{
	int num; double x;
	
	if( argc!=2 ) { printf("serve un parametro\n"); exit(1); }
	
	num=atoi(argv[1]);
	x=(double)num;
	printf("%f\n", calcolaA( calcolaB( x ) ) );

	return(0);
}

