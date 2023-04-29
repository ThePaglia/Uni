/* file:  sincroCircolareConTurno.c DA COMPLETARE
   Realizza sincronizzazione circolare tra tre pthread.
   Implementata con una sola condition variable ed un turno.
*/ 


#include <unistd.h> 
#include <stdlib.h> 
#include <stdio.h> 

#include <stdint.h>    /* definizioni intptr_t */
#include <inttypes.h>  /* definizioni PRIiPTR */

#include <pthread.h> 
#include "printerror.h"
#include "DBGpthread.h"

#define NUMTHREADS 3

/* dati da proteggere */
uint64_t valGlobale=0;

/* variabili per la sincronizzazione */
pthread_mutex_t  mutex; 
pthread_cond_t   cond;
intptr_t turno=0;

void *Thread (void *arg) 
{ 
	uintptr_t mioindice;
	char Label[512];

	mioindice=(intptr_t)arg;
	snprintf( Label, 512, "T%" PRIiPTR "", mioindice );

	while(1) {

		/* AGGIUNGI CODICE PER ENTRARE IN SEZIONE CRITICA
		   ED ESEGUIRLA QUANDO SEI "di turno"
		*/
		DBGpthread_mutex_lock(&mutex, Label);
		while (mioindice != turno){
			DBGpthread_cond_wait(&cond, &mutex, Label);
		}

		/* FINE AGGIUNTA CODICE */

		/* SEZIONE CRITICA: incrementa var globale */
		printf(" Sono %s in sezione critica\n", Label);
		fflush(stdout);

		valGlobale=valGlobale+5;

                if(turno == NUMTHREADS-1) turno=0;
		else turno++;

		/* rimango un secondo in sezione critica */
		sleep(15);

		/* AGGIUNGI CODICE PER USCIRE DALLA SEZIONE CRITICA
		   E PER STABILIRE CHE E' IL PROSSIMO THREAD "di turno"
		   E PER FARLO ENTRARE IN SEZIONE CRITICA
		*/

		DBGpthread_cond_broadcast(&cond, Label);
		DBGpthread_mutex_unlock(&mutex, Label);
		/* FINE AGGIUNTA CODICE */
	}
	pthread_exit(NULL); 
} 

int main () 
{ 
	pthread_t    th[NUMTHREADS]; 
	int  rc;
	intptr_t i;
	void *ptr; 

	rc = pthread_cond_init(&cond, NULL);
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");
	rc = pthread_mutex_init(&mutex, NULL); 
	if( rc ) PrintERROR_andExit(rc,"pthread_mutex_init failed");

	/* all'inizio 0 deve eseguire la sua sezione critica */
	turno=0;

	for( i=0; i<NUMTHREADS; i++ ) {
		rc = pthread_create(&(th[i]), NULL, Thread, (void*)i); 
		if (rc) PrintERROR_andExit(rc,"pthread_create failed");
	}

	for(i=0;i<NUMTHREADS;i++) {
		rc = pthread_join(th[i], &ptr ); 
		if (rc) PrintERROR_andExit(rc,"pthread_join failed");
	}

	rc = pthread_mutex_destroy(&mutex); 
	if(rc) PrintERROR_andExit(rc,"pthread_mutex_destroy failed");
	rc = pthread_cond_destroy(&cond); 
	if(rc) PrintERROR_andExit(rc,"pthread_cond_destroy failed");

	return(0); 
} 
  
  
