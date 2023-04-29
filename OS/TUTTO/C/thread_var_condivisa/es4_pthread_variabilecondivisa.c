/* es4_pthread_variabilecondivisa.c */

/* queste define forse sono gia' nel makefile
   quindi le metto dentro a degli ifndef
*/

#ifndef _THREAD_SAFE
	#define _THREAD_SAFE
#endif
#ifndef _REENTRANT
	#define _REENTRANT
#endif
#ifndef _POSIX_SOURCE
	#define _POSIX_SOURCE 1
#else
	#if _POSIX_SOURCE != 1
		#undef _POSIX_SOURCE
		#define _POSIX_SOURCE 1
	#endif
#endif

#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <stdint.h>	/* per uintptr_t e intptr_t */
#include <pthread.h>

#define NUMTHRDS 10

pthread_t vthread[NUMTHRDS];
pthread_mutex_t mutex;   
/* this variable can be initialized statically with default attributes, using
   pthread_mutex_t mutexdata = PTHREAD_MUTEX_INITIALIZER;  
   same as pthread_mutex_init(...,NULL)
   but with NO ERROR CHECKING 
*/

uint32_t G=0;

void *incrementa(void *arg)
{
	int rc;
	uint32_t contatore=0;
	intptr_t indice=0;

	indice=((intptr_t)arg); /* trucco osceno */

	while(1) {	
		rc = pthread_mutex_lock (&mutex);
		if( rc ) { printf("pthread_mutex_lock failed\n"); exit(1); }

		if( G == indice ) {
			sleep(1);
			G=(G+1)%NUMTHRDS;
			contatore++;
			printf("indice %i contatore %i\n",(int)indice,contatore);
			fflush(stdout);
		}
		
		rc = pthread_mutex_unlock (&mutex);
		if( rc ) { printf("pthread_mutex_unlock failed\n"); exit(1); }

	}
	pthread_exit((void*) NULL);
}

int main (int argc, char *argv[])
{
	uintptr_t i;
	int rc; 

	rc = pthread_mutex_init (&mutex, NULL);
	if( rc != 0 ) { printf("pthread_mutex_init failed\n"); exit(1); }

	for(i=0;i < NUMTHRDS;i++) 	{
		/* per default i thread consentono il join, */
		/* NB: COME FARE CAST PER ASSEGNARE L'INTERO i AL PUNTATORE void*.
		- il cast (void*)i da' warning se l'intero i ha size diversa dal puntatore.
		- il doppio cast (void*)((int64_t)i) funziona solo su sist a 64bit
		  dove la dimensione del puntatore e' di 64 bit, 
		  ma su sist a 32 bit da' warning perche' dice che nel passaggio
		  da int64_t a void* ho due dimensioni diverse e perdo dei bytes.
		- il doppio cast (void*)((int32_t)i) funziona solo su sist a 32bit
		  dove la dimensione del puntatore e' di 32 bit, 
		  ma su sist a 64 bit da' warning perche' dice che nel passaggio
		  da int32_t a void* ho due dimensioni diverse.
		- il modo corretto e' definire l'intero i utilizzando un particolare
		  tipo di dato intero, definito in stdint.h e denominato
		  intptr_t (oppure uintptr_t) che e' un intero con (o senza) segno
		  che ha le stesse dimensioni di un puntatore.
		  Ovviamente la dimensione di questo tipo di dato dipende dal fatto
		  che il sistema e' a 32 o 64 bit.
		*/
		rc = pthread_create ( &vthread[i], NULL, incrementa, (void *)i );
		if( rc != 0 ) { printf("pthread_create failed\n"); exit(1); }
	}

	pthread_exit((void*) NULL);
	return(0);
}

