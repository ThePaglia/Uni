/* verifica_assegnamenti_contemporanei_double.c  */

/* messi nella riga di comando del compilatore 
*/
#ifndef _THREAD_SAFE
#define _THREAD_SAFE
#endif

#ifndef _REENTRANT
#define _REENTRANT
#endif

#ifndef _DEFAULT_SOURCE
#define _DEFAULT_SOURCE  /* per random */
#endif


#include <unistd.h>
#include <stdlib.h>  /*  per random */
#include <stdio.h>
#include <errno.h>
#include <stdint.h>	/* uintptr_t */
#include <inttypes.h>	/* PRIiPTR */
#include <pthread.h>

#define NUM_THREADS 100
#define NUMVALUE NUM_THREADS

long double value[NUMVALUE];
long double G=0.0;

void *PrintHello(void *arg)
{
	long double assign;
	assign=value[((intptr_t)arg)%NUMVALUE];
	printf("\n %"  PRIiPTR ": Hello World! assign %Lf \n", (intptr_t)arg, assign );

	while(1)
		G=assign;

	pthread_exit (NULL);
}

int main()
{
	pthread_t threads[NUM_THREADS];
	long double val;
	unsigned long counter=0;
	int rc;
	intptr_t t;

	srandom( time(NULL) );
	for(t=0;t < NUMVALUE;t++){
		value[t]= ((random() % 1000000000)/5439564313.339);
	}
	G=value[0];

	for(t=0;t < NUM_THREADS;t++){

		printf("Creating thread %" PRIiPTR "\n", t);
		rc = pthread_create (&threads[t], NULL, PrintHello, (void*)t );
		if (rc){
			printf("ERROR; return code from pthread_create() is %d \n",rc );
			exit(1);
		}
	}

	/* controllo che il valore di G sia sempre uno di quelli assegnati */
	while(1) {
		int i, numdiversi;

		counter++;

		val=G;

		numdiversi=0;
		for(i=0;i<NUMVALUE;i++) 
			if( val!=value[i] ) numdiversi++;

		if( numdiversi==NUMVALUE ) {
			printf("errore, confronto %lu valore is %Lf \n", counter, val );
			exit(1);
		}
	}

	pthread_exit (NULL);
	return(0);
}

