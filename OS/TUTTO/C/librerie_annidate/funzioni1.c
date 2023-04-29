
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>

#include "funzioni2.h"

static int num=0;


int incrementa(void)
{
	num++;
	funzione_di_libreria2(); /* solo per usare l'altra libreria */
	return(num);
}



