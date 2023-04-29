/* file:  es23_staffetta.c 
*/ 

#include "printerror.h"

#include <unistd.h> 
#include <stdlib.h> 
#include <stdio.h> 
#include <stdint.h>
#include <inttypes.h>
#include <pthread.h> 
#include <math.h>	/* per sqrt */

#define SYNC_MAX 5 
#define NTHREADS  20

pthread_mutex_t  mutex; 
pthread_cond_t   condAttendiFuori; 
pthread_cond_t   condAttendiDentro; 
int  sync_count = 0, usciti=0; 

float fglob=1111.0;

void SyncPointAGruppi( intptr_t indice ) 
{ 	int rc;
	/* blocca l'accesso al counter */ 
	rc = pthread_mutex_lock(&mutex); 
	if( rc ) PrintERROR_andExit(rc,"pthread_mutex_lock failed"); 

        /* assegna valore */
        fglob=sqrt(fglob);

	while ( sync_count >= SYNC_MAX ) {
		rc = pthread_cond_wait(&condAttendiFuori, &mutex); 
		if( rc ) PrintERROR_andExit(rc, "pthread_cond_wait failed"); 
	}
	/* incrementa il counter di quelli arrivati*/ 
	sync_count++; 
	/* controlla se deve aspettare o no */ 
	if (sync_count < SYNC_MAX) {
		/* aspetta gli altri */ 
		/*
		printf ("thread %" PRIiPTR " aspetta di essere in 5 \n", indice);
		fflush(stdout);
		*/
		rc = pthread_cond_wait(&condAttendiDentro, &mutex); 
		if( rc ) PrintERROR_andExit(rc,"pthread_cond_wait failed"); 
	}
	else    {
		/* in 5 hanno raggiunto il punto di barriera */ 
		printf ("thread %" PRIiPTR " e' il quinto\n", indice);
		fflush(stdout);
		/*
		sleep(1);
		*/
	
		rc = pthread_cond_broadcast (&condAttendiDentro); 
		if( rc ) PrintERROR_andExit(rc,"pthread_cond_signal failed");
	}
	usciti++;
	if( usciti==SYNC_MAX) {
		usciti=0;
		sync_count=0;
		rc = pthread_cond_broadcast (&condAttendiFuori); 
		if( rc ) PrintERROR_andExit(rc,"pthread_cond_signal failed");
	}

	printf ("thread %" PRIiPTR " esce \n", indice);
	fflush(stdout);

	/* sblocca il mutex */ 
	rc = pthread_mutex_unlock (&mutex);
	if( rc ) PrintERROR_andExit(rc,"pthread_mutex_unlock failed");

	return; 
} 

void *staffetta ( void *arg) 
{ 
	intptr_t indice=(intptr_t)arg;

	SyncPointAGruppi( indice ); 
	pthread_exit(NULL); 
} 

int main () 
{ 
	pthread_t    th[NTHREADS]; 
	int  rc;
	intptr_t i;
	void *ptr; 

	rc = pthread_cond_init(&condAttendiFuori, NULL);
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed"); 
	rc = pthread_cond_init(&condAttendiDentro, NULL);
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed"); 
	rc = pthread_mutex_init(&mutex, NULL); 
	if( rc ) PrintERROR_andExit(rc,"pthread_mutex_init failed"); 

	while(1) {
		for(i=0;i<NTHREADS;i++) {
			rc = pthread_create(&(th[i]), NULL, staffetta, (void*)i ); 
			if (rc) PrintERROR_andExit(rc,"pthread_create failed");
		}
		for(i=0;i<NTHREADS;i++) {
			rc = pthread_join(th[i], &ptr ); 
			if (rc) PrintERROR_andExit(rc,"pthread_join failed");
		}
	}

	rc = pthread_mutex_destroy(&mutex); 
	if( rc ) PrintERROR_andExit(rc,"pthread_mutex_destroy failed");
	rc = pthread_cond_destroy(&condAttendiFuori); 
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_destroy failed");
	rc = pthread_cond_destroy(&condAttendiDentro); 
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_destroy failed");
	pthread_exit (NULL); 
} 
  
  
  
