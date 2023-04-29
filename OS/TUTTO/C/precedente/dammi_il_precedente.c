/* dammi_il_precedente.c  */

#ifndef _POSIX_SOURCE
	#define _POSIX_SOURCE
#endif

#include <unistd.h>
#include <stdlib.h>		/* serve per la funzione rand */
#include <stdio.h>
#include <time.h>		/* serve per la funzione time */

unsigned int dammi_il_precedente( unsigned int attuale) {
	static unsigned int precedente=0;
	unsigned int temp;
	printf("precedente %u attuale %u\n", precedente, attuale );
	temp=precedente;
	precedente=attuale;
	return(temp);
}

int main(void) {
	srand( time(NULL) );	
	while(1) {
		if( ( dammi_il_precedente( rand() ) %10) == 3 ) 
			break;
	}
	return(0);
}


