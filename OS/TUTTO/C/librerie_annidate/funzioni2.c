
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>


int funzione_di_libreria2(void)
{
	printf("sono funzione_di_libreria2() e faccio un bel niente\n");
	fflush(stdout);
	return(1);
}

